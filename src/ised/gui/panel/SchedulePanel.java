/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SchedulePanel.java
 *
 * Created on 11 1, 11, 10:17:31 PM
 */
package ised.gui.panel;

import ised.gui.Principal;
import ised.model.Schedule;
import ised.model.Subject;
import ised.model.Class;
import ised.model.ClassSchedule;
import ised.model.SchoolYear;
import ised.service.implementation.ClassScheduleServiceImpl;
import ised.service.implementation.ClassServiceImpl;
import ised.service.implementation.SubjectServiceImpl;
import ised.service.interfaces.ClassScheduleService;
import ised.service.interfaces.ClassService;
import ised.service.interfaces.SubjectService;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import java.sql.Time;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ABDUL
 */
public class SchedulePanel extends javax.swing.JPanel {

    List<ClassSchedule> classScheduleList;
    List<Schedule> scheduleList;
    List<Class> sectionList;
    List<Subject> subjectList;
    SchoolYear currentSchoolYear;
    Subject selectedSubject;
    Class selectedSection;
    String selectedDay;
    DefaultComboBoxModel sectionComboBoxModel;
    DefaultTableModel subjectTableModel;
    DefaultTableModel classScheduleTableModel;
    ClassScheduleService classScheduleService;
    ClassService classService;
    SubjectService subjectService;
    Principal parentFrame;
    boolean isAdd;
    ClassSchedule selectedClassSchedule;

    /** Creates new form SchedulePanel */
    public SchedulePanel(Principal parentFrame, SchoolYear schoolYear) throws ExceptionHandler {
        initComponents();
        this.parentFrame = parentFrame;
        classScheduleService = new ClassScheduleServiceImpl();
        classService = new ClassServiceImpl();
        subjectService = new SubjectServiceImpl();
        scheduleList = classScheduleService.getScheduleList();
        currentSchoolYear = schoolYear;
        sectionComboBoxModel = (DefaultComboBoxModel) sectionComboBox.getModel();
        subjectTableModel = (DefaultTableModel) subjectsTable.getModel();
        classScheduleTableModel = (DefaultTableModel) classScheduleTable.getModel();
        setSectionComboBox();
        displayClassSchedule();

        ComponentFormatter.stripTable(subjectsTable);
        ComponentFormatter.stripTable(classScheduleTable);
    }

    private void setSectionComboBox() throws ExceptionHandler {
        sectionList = classService.getClassList(currentSchoolYear.getSchoolYearID(), "Active");
        sectionComboBoxModel.removeAllElements();
        sectionComboBoxModel.addElement(null);
        for (Class section : sectionList) {
            sectionComboBoxModel.addElement(section);
        }
    }

    private void displaySubjectList() throws ExceptionHandler {
        subjectList = subjectService.getSubjectList(selectedSection.getSection().getCurriculum().getCurriculumID(), selectedSection.getYearLevel());
        ComponentFormatter.clearTable(subjectTableModel);
        for (Subject subject : subjectList) {
            subjectTableModel.addRow(new Object[]{subject.getSubjectCode(), subject.getDescription(),
                        subject.getUnits(), getSubjectHours(subject.getUnits()) + " hours", isSet(subject)});
        }
    }

    private void displayClassSchedule() throws ExceptionHandler {
        selectedSection = (Class) sectionComboBox.getSelectedItem();
        ComponentFormatter.clearTable(classScheduleTableModel);
        if (selectedSection == null) {
            addScheduleButton.setEnabled(false);
            dayComboBox.setEnabled(false);
            return;
        } else {
            addScheduleButton.setEnabled(true);
            dayComboBox.setEnabled(true);
            
            if (dayComboBox.getSelectedIndex() == 0) {
                classScheduleList = classScheduleService.getClassScheduleList(selectedSection.getClassID());
            } else {
                classScheduleList = classScheduleService.getClassScheduleList(selectedSection.getClassID(), getDayChar());
            }

            for (ClassSchedule classSchedule : classScheduleList) {
                if (classSchedule.getSubject() != null) {
                    classScheduleTableModel.addRow(new Object[]{classSchedule.getSchedule().getDays() + " " + ComponentFormatter.formatTimeToString(classSchedule.getSchedule().getStartTime())
                                + " - " + ComponentFormatter.formatTimeToString(classSchedule.getSchedule().getEndTime()),
                                classSchedule.getSubject().getSubjectCode(), classSchedule.getSubject().getDescription(),
                                classSchedule.getTeacher() == null ? "" : classSchedule.getTeacher()});
                } else {
                    classScheduleTableModel.addRow(new Object[]{classSchedule.getSchedule().getDays() + " " + ComponentFormatter.formatTimeToString(classSchedule.getSchedule().getStartTime())
                                + " - " + ComponentFormatter.formatTimeToString(classSchedule.getSchedule().getEndTime()),
                                classSchedule.getNonSubject(), null});
                }

            }
        }
    }

    private double getSubjectHours(double subjectUnits) {
        if (subjectUnits < 1) {
            return 1;
        }
        return subjectUnits * 3;
    }

    private double subtractTime(Time t1, Time t2) {
        double startTime = t1.getHours() + (double) t1.getMinutes() / 60;
        double endTime = t2.getHours() + (double) t2.getMinutes() / 60;
        return endTime - startTime;
    }

//    private double getSubjectScheduleHours(Subject subject) throws ExceptionHandler {
//        double subjectHours = 0;
//        if (subject != null) {
//            classScheduleList = classScheduleService.getClassScheduleList(selectedSection.getClassID());
//            for (ClassSchedule classSchedule : classScheduleList) {
//                if (classSchedule.getSubject() != null) {
//                    if (classSchedule.getSubject().getSubjectID() == subject.getSubjectID()) {
//                        int noOfDays = classSchedule.getSchedule().getDays().length();
//                        subjectHours += (subtractTime(classSchedule.getSchedule().getStartTime(), classSchedule.getSchedule().getEndTime()) * noOfDays);
//                    }
//                }
//            }
//        }
//        return subjectHours;
//    }
    private String isSet(Subject subject) throws ExceptionHandler {
        classScheduleList = classScheduleService.getClassScheduleList(selectedSection.getClassID());
        for (ClassSchedule classSchedule : classScheduleList) {
            if (classSchedule.getSubject() != null) {
                if (classSchedule.getSubject().getSubjectID() == subject.getSubjectID()) {
                    return "Set";
                }
            }
        }
        return "";
    }

    private double getScheduleHours(Schedule schedule) throws ExceptionHandler {
        double subjectHours = 0;
        int noOfDays = schedule.getDays().length();
        subjectHours = (subtractTime(schedule.getStartTime(), schedule.getEndTime()) * noOfDays);
        return subjectHours;
    }

    private char getDayChar() {
        selectedDay = dayComboBox.getSelectedItem().toString();
        if (selectedDay.equalsIgnoreCase("Monday")) {
            return 'M';
        } else if (selectedDay.equalsIgnoreCase("Tuesday")) {
            return 'T';
        } else if (selectedDay.equalsIgnoreCase("Wednesday")) {
            return 'W';
        } else if (selectedDay.equalsIgnoreCase("Thursday")) {
            return 'H';
        } else if (selectedDay.equalsIgnoreCase("Friday")) {
            return 'F';
        }
        return ' ';
    }

    private String getScheduleDays() {
        String daysChar = "";
        daysChar += (mondayCheckBox.isSelected()) ? "M" : "";
        daysChar += (tuesdayCheckBox.isSelected()) ? "T" : "";
        daysChar += (wednesdayCheckBox.isSelected()) ? "W" : "";
        daysChar += (thursdayCheckBox.isSelected()) ? "H" : "";
        daysChar += (fridayCheckBox.isSelected()) ? "F" : "";

        return daysChar;
    }

    private boolean checkSchedule(ClassSchedule sched) {
        char[] schedDays = sched.getSchedule().getDays().toCharArray();
        for (ClassSchedule sc : classScheduleList) {
            if (sc.getClassScheduleID() == sched.getClassScheduleID()) {
                continue;
            }
            char[] scDays = sc.getSchedule().getDays().toCharArray();
            for (int i = 0; i < scDays.length; i++) {
                for (int j = 0; j < schedDays.length; j++) {
                    if (schedDays[j] == scDays[i]) {
                        if (isTimeConflict(sched.getSchedule().getStartTime(), sched.getSchedule().getEndTime(),
                                sc.getSchedule().getStartTime(), sc.getSchedule().getEndTime())) {
                            String scheduleName;
                            if (sc.getSubject() != null) {
                                scheduleName = sc.getSubject().getSubjectCode();
                            } else {
                                scheduleName = sc.getNonSubject();
                            }
                            JOptionPane.showMessageDialog(parentFrame, "Schedule is conflict with "
                                    + scheduleName + ".", "WARNING", 2);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isTimeConflict(Time fTime1, Time tTime1, Time fTime2, Time tTime2) {
        return (fTime1.equals(fTime2) || (tTime1.equals(tTime2) || (fTime1.before(tTime2) && tTime1.after(fTime2))));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scheduleButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        classScheduleTable = new javax.swing.JTable();
        sectionComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        editScheduleButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        dayComboBox = new javax.swing.JComboBox();
        removeScheduleButton = new javax.swing.JButton();
        addScheduleButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        submitButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        endTimeLabel = new javax.swing.JLabel();
        startTimeLabel = new javax.swing.JLabel();
        startTimeModComboBox = new javax.swing.JComboBox();
        endTimeModComboBox = new javax.swing.JComboBox();
        endTimeMinComboBox = new javax.swing.JComboBox();
        startTimeMinComboBox = new javax.swing.JComboBox();
        startTimeHourComboBox = new javax.swing.JComboBox();
        endTimeHourComboBox = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        subjectsTable = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        wednesdayCheckBox = new javax.swing.JCheckBox();
        thursdayCheckBox = new javax.swing.JCheckBox();
        tuesdayCheckBox = new javax.swing.JCheckBox();
        fridayCheckBox = new javax.swing.JCheckBox();
        mondayCheckBox = new javax.swing.JCheckBox();
        allDaysCheckBox = new javax.swing.JCheckBox();
        mwfCheckBox = new javax.swing.JCheckBox();
        tthCheckBox = new javax.swing.JCheckBox();
        nonSubjectCheckBox = new javax.swing.JCheckBox();
        subjectCheckBox = new javax.swing.JCheckBox();
        nonSubjectTextField = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 12));

        jPanel44.setBackground(new java.awt.Color(0, 204, 51));
        jPanel44.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel44.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        classScheduleTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        classScheduleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Day/Time", "Subject Code", "Description", "Teacher"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        classScheduleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                classScheduleTableMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(classScheduleTable);
        classScheduleTable.getColumnModel().getColumn(0).setPreferredWidth(100);

        jPanel44.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 570, 350));

        sectionComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        sectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionComboBoxActionPerformed(evt);
            }
        });
        jPanel44.add(sectionComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel3.setText("Section");
        jPanel44.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 20));

        editScheduleButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        editScheduleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/pencil.png"))); // NOI18N
        editScheduleButton.setText("Edit Schedule");
        editScheduleButton.setEnabled(false);
        editScheduleButton.setOpaque(false);
        editScheduleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editScheduleButtonActionPerformed(evt);
            }
        });
        jPanel44.add(editScheduleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 440, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel1.setText("Day:");
        jPanel44.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 20));

        dayComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        dayComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" }));
        dayComboBox.setEnabled(false);
        dayComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayComboBoxActionPerformed(evt);
            }
        });
        jPanel44.add(dayComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 110, -1));

        removeScheduleButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        removeScheduleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/delete.png"))); // NOI18N
        removeScheduleButton.setText("Remove Schedule");
        removeScheduleButton.setEnabled(false);
        removeScheduleButton.setOpaque(false);
        removeScheduleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeScheduleButtonActionPerformed(evt);
            }
        });
        jPanel44.add(removeScheduleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, -1, -1));

        addScheduleButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        addScheduleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/add.png"))); // NOI18N
        addScheduleButton.setText("Add Schedule");
        addScheduleButton.setEnabled(false);
        addScheduleButton.setOpaque(false);
        addScheduleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addScheduleButtonActionPerformed(evt);
            }
        });
        jPanel44.add(addScheduleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 440, -1, -1));

        jButton1.setText("...");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel44.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 40, -1));

        jPanel26.setBackground(new java.awt.Color(0, 204, 51));
        jPanel26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel26.setEnabled(false);
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        submitButton.setBackground(new java.awt.Color(0, 204, 51));
        submitButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        submitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/accept.png"))); // NOI18N
        submitButton.setText("Submit");
        submitButton.setEnabled(false);
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });
        jPanel26.add(submitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 440, -1, -1));

        cancelButton.setBackground(new java.awt.Color(0, 204, 51));
        cancelButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/cancel.png"))); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.setEnabled(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel26.add(cancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 440, 100, -1));

        jPanel30.setBackground(new java.awt.Color(0, 204, 51));
        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Time", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel30.setEnabled(false);
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        endTimeLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        endTimeLabel.setText("End Time:");
        endTimeLabel.setEnabled(false);
        jPanel30.add(endTimeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        startTimeLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        startTimeLabel.setText("Start Time:");
        startTimeLabel.setEnabled(false);
        jPanel30.add(startTimeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 70, 20));

        startTimeModComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        startTimeModComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AM", "PM" }));
        startTimeModComboBox.setEnabled(false);
        jPanel30.add(startTimeModComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 50, -1));

        endTimeModComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        endTimeModComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AM", "PM" }));
        endTimeModComboBox.setEnabled(false);
        jPanel30.add(endTimeModComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 50, -1));

        endTimeMinComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        endTimeMinComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "15", "30", "45" }));
        endTimeMinComboBox.setEnabled(false);
        jPanel30.add(endTimeMinComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 50, -1));

        startTimeMinComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        startTimeMinComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "15", "30", "45" }));
        startTimeMinComboBox.setEnabled(false);
        jPanel30.add(startTimeMinComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 50, -1));

        startTimeHourComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        startTimeHourComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "6", "7", "8", "9", "10", "11", "12", "1", "2", "3", "4", "5" }));
        startTimeHourComboBox.setEnabled(false);
        jPanel30.add(startTimeHourComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 50, -1));

        endTimeHourComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        endTimeHourComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "6", "7", "8", "9", "10", "11", "12", "1", "2", "3", "4", "5", " " }));
        endTimeHourComboBox.setEnabled(false);
        jPanel30.add(endTimeHourComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 50, -1));

        jPanel26.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 290, 110));

        subjectsTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        subjectsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject Code", "Description", "Units", "Hours Per Week", "Set"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        subjectsTable.setEnabled(false);
        jScrollPane1.setViewportView(subjectsTable);
        subjectsTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        subjectsTable.getColumnModel().getColumn(2).setMinWidth(50);
        subjectsTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        subjectsTable.getColumnModel().getColumn(2).setMaxWidth(50);
        subjectsTable.getColumnModel().getColumn(4).setPreferredWidth(50);

        jPanel26.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 460, 180));

        jPanel29.setBackground(new java.awt.Color(0, 204, 51));
        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Days", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel29.setEnabled(false);
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        wednesdayCheckBox.setBackground(new java.awt.Color(0, 204, 51));
        wednesdayCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        wednesdayCheckBox.setText("Wednesday");
        wednesdayCheckBox.setEnabled(false);
        jPanel29.add(wednesdayCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        thursdayCheckBox.setBackground(new java.awt.Color(0, 204, 51));
        thursdayCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        thursdayCheckBox.setText("Thursday");
        thursdayCheckBox.setEnabled(false);
        jPanel29.add(thursdayCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

        tuesdayCheckBox.setBackground(new java.awt.Color(0, 204, 51));
        tuesdayCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        tuesdayCheckBox.setText("Tuesday");
        tuesdayCheckBox.setEnabled(false);
        tuesdayCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tuesdayCheckBoxActionPerformed(evt);
            }
        });
        jPanel29.add(tuesdayCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        fridayCheckBox.setBackground(new java.awt.Color(0, 204, 51));
        fridayCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        fridayCheckBox.setText("Friday");
        fridayCheckBox.setEnabled(false);
        jPanel29.add(fridayCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, 20));

        mondayCheckBox.setBackground(new java.awt.Color(0, 204, 51));
        mondayCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        mondayCheckBox.setText("Monday");
        mondayCheckBox.setEnabled(false);
        jPanel29.add(mondayCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        allDaysCheckBox.setBackground(new java.awt.Color(0, 204, 51));
        allDaysCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        allDaysCheckBox.setText("All Days");
        allDaysCheckBox.setEnabled(false);
        allDaysCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allDaysCheckBoxActionPerformed(evt);
            }
        });
        jPanel29.add(allDaysCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        mwfCheckBox.setBackground(new java.awt.Color(0, 204, 51));
        mwfCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        mwfCheckBox.setText("Mon/Wed/Fri");
        mwfCheckBox.setEnabled(false);
        mwfCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mwfCheckBoxActionPerformed(evt);
            }
        });
        jPanel29.add(mwfCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 20));

        tthCheckBox.setBackground(new java.awt.Color(0, 204, 51));
        tthCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        tthCheckBox.setText("Tue/Thur");
        tthCheckBox.setEnabled(false);
        tthCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tthCheckBoxActionPerformed(evt);
            }
        });
        jPanel29.add(tthCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 100, 20));

        jPanel26.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 130, 190));

        scheduleButtonGroup.add(nonSubjectCheckBox);
        nonSubjectCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        nonSubjectCheckBox.setText("Non-subject");
        nonSubjectCheckBox.setEnabled(false);
        nonSubjectCheckBox.setOpaque(false);
        nonSubjectCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nonSubjectCheckBoxActionPerformed(evt);
            }
        });
        jPanel26.add(nonSubjectCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, 20));

        scheduleButtonGroup.add(subjectCheckBox);
        subjectCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        subjectCheckBox.setText("Subject");
        subjectCheckBox.setEnabled(false);
        subjectCheckBox.setOpaque(false);
        subjectCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectCheckBoxActionPerformed(evt);
            }
        });
        jPanel26.add(subjectCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        nonSubjectTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
        nonSubjectTextField.setEnabled(false);
        jPanel26.add(nonSubjectTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 270, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        try {
            Time startTime = new Time(0, 0, 0);
            Time endTime = new Time(0, 0, 0);
            String days = getScheduleDays();

            if (!subjectCheckBox.isSelected() && !nonSubjectCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(parentFrame, "Pls. check a schedule", "WARNING", 2);
                return;
            }

            if (subjectCheckBox.isSelected() && subjectsTable.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(parentFrame, "Pls. select a subject", "WARNING", 2);
                return;
            }

            if (nonSubjectCheckBox.isSelected() && nonSubjectTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid schedule name", "WARNING", 2);
                return;
            }

            if (days.equals("")) {
                JOptionPane.showMessageDialog(parentFrame, "Pls. select day(s) for schedule", "WARNING", 2);
                return;
            }

            String startTimeHour = startTimeHourComboBox.getSelectedItem().toString();
            String startTimeMin = startTimeMinComboBox.getSelectedItem().toString();
            String startTimeMod = startTimeModComboBox.getSelectedItem().toString();
            String endTimeHour = endTimeHourComboBox.getSelectedItem().toString();
            String endTimeMin = endTimeMinComboBox.getSelectedItem().toString();
            String endTimeMod = endTimeModComboBox.getSelectedItem().toString();

            startTime = get24HourFormat(Integer.parseInt(startTimeHour), Integer.parseInt(startTimeMin), startTimeMod);
            endTime = get24HourFormat(Integer.parseInt(endTimeHour), Integer.parseInt(endTimeMin), endTimeMod);

            if (endTime.getHours() < startTime.getHours()
                    || (endTime.getHours() == startTime.getHours()
                    && endTime.getMinutes() <= startTime.getMinutes())) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid time", "WARNING", 2);
                return;
            }

            Schedule schedule = getSchedule(days, startTime, endTime);

            if (schedule == null) {
                Schedule newSchedule = new Schedule(days, startTime, endTime);
                classScheduleService.addSchedule(newSchedule);
                scheduleList = classScheduleService.getScheduleList();
                schedule = getSchedule(days, startTime, endTime);
            }


            ClassSchedule classSchedule;

            if (isAdd) {
                classSchedule = null;
            } else {
                classSchedule = selectedClassSchedule;
            }

            Subject subject = null;
            if (subjectCheckBox.isSelected()) {
                subject = subjectList.get(subjectsTable.getSelectedRow());
                if (isSet(subject).equalsIgnoreCase("Set")) {
                    JOptionPane.showMessageDialog(parentFrame, "Subject is already set", "Schedule", 2);
                    return;
                }
                if (isAdd) {
                    classSchedule = new ClassSchedule(selectedSection, schedule, subject);
                } else {
                    classSchedule.setSchedule(schedule);
                    classSchedule.setSubject(subject);
                    classSchedule.setNonSubject(null);
                }

                if (getSubjectHours(subject.getUnits()) < getScheduleHours(schedule)) {
                    JOptionPane.showMessageDialog(parentFrame, "Time exceed on subject hours", "Schedule", 2);
                    return;
                } else if (getSubjectHours(subject.getUnits()) > getScheduleHours(schedule)) {
                    JOptionPane.showMessageDialog(parentFrame, "Time is less than the subject hours", "Schedule", 2);
                    return;
                }



            } else if (nonSubjectCheckBox.isSelected()) {
                if (isAdd) {
                    classSchedule = new ClassSchedule(selectedSection, schedule, nonSubjectTextField.getText());
                } else {
                    classSchedule.setSchedule(schedule);
                    classSchedule.setSubject(null);
                    classSchedule.setNonSubject(nonSubjectTextField.getText());
                }
            }

            if (checkSchedule(classSchedule)) {
                if (isAdd) {
                    classScheduleService.addClassSchedule(classSchedule);
                    JOptionPane.showMessageDialog(parentFrame, "Class schedule successfully added", "Add Schedule", 1);
                } else {
                    classScheduleService.editClassSchedule(classSchedule);
                    JOptionPane.showMessageDialog(parentFrame, "Class schedule successfully edited", "Edit Schedule", 1);
                }
                displayClassSchedule();
                clearSetSchedulePanel();
            }

        } catch (ExceptionHandler ex) {
        }
}//GEN-LAST:event_submitButtonActionPerformed

    private void tuesdayCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tuesdayCheckBoxActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_tuesdayCheckBoxActionPerformed

    private void addScheduleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addScheduleButtonActionPerformed
        // TODO add your handling code here:
        clearSetSchedulePanel();
        subjectCheckBox.setEnabled(true);
        nonSubjectCheckBox.setEnabled(true);
        isAdd = true;
    }//GEN-LAST:event_addScheduleButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        clearSetSchedulePanel();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void clearSetSchedulePanel() {
        scheduleButtonGroup.clearSelection();
        subjectCheckBox.setEnabled(false);
        nonSubjectCheckBox.setEnabled(false);
        ComponentFormatter.clearTable(subjectTableModel);
        nonSubjectTextField.setText("");
        updateSetScheduleComponents(false);
    }
    private void editScheduleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editScheduleButtonActionPerformed
        // TODO add your handling code here:
        int index = classScheduleTable.getSelectedRow();
        if (index >= 0) {
            updateSetScheduleComponents(true);
            setDaysCheckBoxSelected(false);
            nonSubjectCheckBox.setEnabled(true);
            subjectCheckBox.setEnabled(true);
            ClassSchedule classSchedule = classScheduleList.get(index);
            selectedClassSchedule = classSchedule;

            if (classSchedule.getSubject() == null) {
                nonSubjectCheckBox.setSelected(true);
                nonSubjectCheckBox.setEnabled(true);
                nonSubjectTextField.setEnabled(true);
                nonSubjectTextField.setText(classSchedule.getNonSubject());
            } else {
                subjectsTable.setEnabled(true);
                subjectCheckBox.setSelected(true);
                try {
                    displaySubjectList();
                } catch (ExceptionHandler ex) {
                    Logger.getLogger(SchedulePanel.class.getName()).log(Level.SEVERE, null, ex);
                }

                int subjectIndex = -1;

                for (Subject subject : subjectList) {
                    if (classSchedule.getSubject().getSubjectID() == subject.getSubjectID()) {
                        subjectIndex = subjectList.indexOf(subject);
                    }
                }

                if (subjectIndex >= 0) {
                    subjectsTable.setRowSelectionInterval(subjectIndex, subjectIndex);
                }

                if (classSchedule.getSchedule().getDays().equalsIgnoreCase("MTWHF")) {
                    allDaysCheckBox.setSelected(true);
                    setDaysCheckBoxSelected(true);
                } else if (classSchedule.getSchedule().getDays().equalsIgnoreCase("MWF")) {
                    mwfCheckBox.setSelected(true);
                    mondayCheckBox.setSelected(true);
                    wednesdayCheckBox.setSelected(true);
                    fridayCheckBox.setSelected(true);
                } else if (classSchedule.getSchedule().getDays().equalsIgnoreCase("TH")) {
                    tthCheckBox.setSelected(true);
                    tuesdayCheckBox.setSelected(true);
                    thursdayCheckBox.setSelected(true);
                } else {
                    char[] daysChar = classSchedule.getSchedule().getDays().toCharArray();
                    for (int i = 0; i < daysChar.length; i++) {
                        if (daysChar[i] == 'M') {
                            mondayCheckBox.setSelected(true);
                        } else if (daysChar[i] == 'T') {
                            tuesdayCheckBox.setSelected(true);
                        } else if (daysChar[i] == 'W') {
                            wednesdayCheckBox.setSelected(true);
                        } else if (daysChar[i] == 'H') {
                            thursdayCheckBox.setSelected(true);
                        } else if (daysChar[i] == 'F') {
                            fridayCheckBox.setSelected(true);
                        }
                    }
                }

                Time startTime = classSchedule.getSchedule().getStartTime();
                Time endTime = classSchedule.getSchedule().getEndTime();

                startTimeHourComboBox.setSelectedItem(Integer.toString(getHour(startTime)));
                startTimeMinComboBox.setSelectedItem(Integer.toString(startTime.getMinutes()));
                startTimeModComboBox.setSelectedItem(startTime.getHours() > 12 ? "PM" : "AM");
                endTimeHourComboBox.setSelectedItem(Integer.toString(getHour(endTime)));
                endTimeMinComboBox.setSelectedItem(Integer.toString(endTime.getMinutes()));
                endTimeModComboBox.setSelectedItem(endTime.getHours() > 12 ? "PM" : "AM");
            }
            isAdd = false;
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Please select a class schedule", "Edit Class Schedule", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_editScheduleButtonActionPerformed

    private void sectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionComboBoxActionPerformed
        try {
            // TODO add your handling code here:
            displayClassSchedule();
            clearSetSchedulePanel();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(SchedulePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sectionComboBoxActionPerformed

    private void allDaysCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allDaysCheckBoxActionPerformed
        // TODO add your handling code here:
        if (allDaysCheckBox.isSelected()) {
            setDaysCheckBoxSelected(true);
            mwfCheckBox.setSelected(false);
            tthCheckBox.setSelected(false);
        } else {
            setDaysCheckBoxSelected(false);
        }
    }//GEN-LAST:event_allDaysCheckBoxActionPerformed

    private void subjectCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectCheckBoxActionPerformed
        // TODO add your handling code here:
        try {
            if (subjectCheckBox.isSelected()) {
                subjectsTable.setEnabled(true);
                displaySubjectList();
                updateSetScheduleComponents(true);
                nonSubjectTextField.setEnabled(false);
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(SchedulePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_subjectCheckBoxActionPerformed

    private void nonSubjectCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nonSubjectCheckBoxActionPerformed
        // TODO add your handling code here:
        if (nonSubjectCheckBox.isSelected()) {
            nonSubjectTextField.setEnabled(true);
            updateSetScheduleComponents(true);
            ComponentFormatter.clearTable(subjectTableModel);
            subjectsTable.setEnabled(false);
        } else {
            nonSubjectTextField.setEnabled(false);
        }
    }//GEN-LAST:event_nonSubjectCheckBoxActionPerformed

    private void classScheduleTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classScheduleTableMouseClicked
        // TODO add your handling code here:
        int index = classScheduleTable.getSelectedRow();
        if (index >= 0) {
            ClassSchedule classSchedule = classScheduleList.get(index);
            if (classSchedule.getTeacher() == null) {
                editScheduleButton.setEnabled(true);
                removeScheduleButton.setEnabled(true);
            } else {
                editScheduleButton.setEnabled(false);
                removeScheduleButton.setEnabled(false);
            }
        }
    }//GEN-LAST:event_classScheduleTableMouseClicked

    private void dayComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayComboBoxActionPerformed
        try {
            // TODO add your handling code here:
            displayClassSchedule();
            //clearSetSchedulePanel();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(SchedulePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_dayComboBoxActionPerformed

    private void tthCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tthCheckBoxActionPerformed
        // TODO add your handling code here:
        if (tthCheckBox.isSelected()) {
            allDaysCheckBox.setSelected(false);
            mwfCheckBox.setSelected(false);
            mondayCheckBox.setSelected(false);
            wednesdayCheckBox.setSelected(false);
            fridayCheckBox.setSelected(false);
            tuesdayCheckBox.setSelected(true);
            thursdayCheckBox.setSelected(true);
        } else {
            setDaysCheckBoxSelected(false);
        }
    }//GEN-LAST:event_tthCheckBoxActionPerformed

    private void removeScheduleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeScheduleButtonActionPerformed
        // TODO add your handling code here:
        int index = classScheduleTable.getSelectedRow();
        if (index >= 0) {
            ClassSchedule classSchedule = classScheduleList.get(index);
            int option = JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to delete this schedule?",
                    "Delete Student", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    if (classSchedule.getTeacher() == null) {
                        classScheduleService.deleteClassSchedule(classSchedule.getClassScheduleID());
                        displayClassSchedule();
                        clearSetSchedulePanel();
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Schedule can't be deleted because there are already teacher assigned", "Delete Student", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (ExceptionHandler ex) {
                    Logger.getLogger(StudentRecordsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Please select a class schedule", "Delete Class Schedule", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_removeScheduleButtonActionPerformed

    private void mwfCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mwfCheckBoxActionPerformed
        // TODO add your handling code here:
        if (mwfCheckBox.isSelected()) {
            mondayCheckBox.setSelected(true);
            wednesdayCheckBox.setSelected(true);
            fridayCheckBox.setSelected(true);
            allDaysCheckBox.setSelected(false);
            tuesdayCheckBox.setSelected(false);
            thursdayCheckBox.setSelected(false);
        }
    }//GEN-LAST:event_mwfCheckBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            setSectionComboBox();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(SchedulePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void setDaysCheckBoxSelected(boolean flag) {
        mondayCheckBox.setSelected(flag);
        tuesdayCheckBox.setSelected(flag);
        wednesdayCheckBox.setSelected(flag);
        thursdayCheckBox.setSelected(flag);
        fridayCheckBox.setSelected(flag);
    }

    private void updateSetScheduleComponents(boolean flag) {
        allDaysCheckBox.setEnabled(flag);
        mwfCheckBox.setEnabled(flag);
        tthCheckBox.setEnabled(flag);
        mondayCheckBox.setEnabled(flag);
        tuesdayCheckBox.setEnabled(flag);
        wednesdayCheckBox.setEnabled(flag);
        thursdayCheckBox.setEnabled(flag);
        fridayCheckBox.setEnabled(flag);
        startTimeLabel.setEnabled(flag);
        endTimeLabel.setEnabled(flag);
        startTimeHourComboBox.setEnabled(flag);
        endTimeHourComboBox.setEnabled(flag);
        startTimeMinComboBox.setEnabled(flag);
        endTimeMinComboBox.setEnabled(flag);
        startTimeModComboBox.setEnabled(flag);
        endTimeModComboBox.setEnabled(flag);
        submitButton.setEnabled(flag);
        cancelButton.setEnabled(flag);
        if (flag == false) {
            setDaysCheckBoxSelected(false);
            allDaysCheckBox.setSelected(false);
            mwfCheckBox.setSelected(false);
            tthCheckBox.setSelected(false);
            startTimeHourComboBox.setSelectedIndex(0);
            startTimeMinComboBox.setSelectedIndex(0);
            startTimeModComboBox.setSelectedIndex(0);
            endTimeHourComboBox.setSelectedIndex(0);
            endTimeMinComboBox.setSelectedIndex(0);
            endTimeModComboBox.setSelectedIndex(0);
        }
    }

    private Time get24HourFormat(int hour, int min, String modifier) {
        if (modifier.equalsIgnoreCase("PM") && hour < 12) {
            hour += 12;
        } else if (hour == 12 && modifier.equalsIgnoreCase("AM")) {
            hour = 0;
        }
        return new Time(hour, min, 0);
    }

    private int getHour(Time time) {
        if (time.getHours() > 12) {
            return time.getHours() - 12;
        }
        return time.getHours();
    }

    private Schedule getSchedule(String days, Time timeFrom, Time timeTo) {
        for (Schedule schedule : scheduleList) {
            if (schedule.getDays().equalsIgnoreCase(days) && schedule.getStartTime().equals(timeFrom) && schedule.getEndTime().equals(timeTo)) {
                return schedule;
            }
        }
        return null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addScheduleButton;
    private javax.swing.JCheckBox allDaysCheckBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTable classScheduleTable;
    private javax.swing.JComboBox dayComboBox;
    private javax.swing.JButton editScheduleButton;
    private javax.swing.JComboBox endTimeHourComboBox;
    private javax.swing.JLabel endTimeLabel;
    private javax.swing.JComboBox endTimeMinComboBox;
    private javax.swing.JComboBox endTimeModComboBox;
    private javax.swing.JCheckBox fridayCheckBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JCheckBox mondayCheckBox;
    private javax.swing.JCheckBox mwfCheckBox;
    private javax.swing.JCheckBox nonSubjectCheckBox;
    private javax.swing.JTextField nonSubjectTextField;
    private javax.swing.JButton removeScheduleButton;
    private javax.swing.ButtonGroup scheduleButtonGroup;
    private javax.swing.JComboBox sectionComboBox;
    private javax.swing.JComboBox startTimeHourComboBox;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JComboBox startTimeMinComboBox;
    private javax.swing.JComboBox startTimeModComboBox;
    private javax.swing.JCheckBox subjectCheckBox;
    private javax.swing.JTable subjectsTable;
    private javax.swing.JButton submitButton;
    private javax.swing.JCheckBox thursdayCheckBox;
    private javax.swing.JCheckBox tthCheckBox;
    private javax.swing.JCheckBox tuesdayCheckBox;
    private javax.swing.JCheckBox wednesdayCheckBox;
    // End of variables declaration//GEN-END:variables
}

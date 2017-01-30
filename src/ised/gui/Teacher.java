/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Registrar.java
 *
 * Created on Oct 15, 2011, 10:33:48 AM
 */
package ised.gui;

import com.mysql.jdbc.Blob;
import ised.gui.dialog.UpdateAccountDialog;
import ised.model.Adviser;
import ised.model.Employee;
import ised.model.SchoolYear;
import ised.model.UserAccount;
import ised.model.ClassSchedule;
import ised.model.Student;
import ised.model.SubjectGrade;
import ised.service.implementation.AdviserServiceImpl;
import ised.service.implementation.ClassScheduleServiceImpl;
import ised.service.implementation.EnrollmentServiceImpl;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.implementation.StudentSubjectServiceImpl;
import ised.service.interfaces.AdviserService;
import ised.service.interfaces.ClassScheduleService;
import ised.service.interfaces.EnrollmentService;
import ised.service.interfaces.SchoolYearService;
import ised.service.interfaces.StudentSubjectService;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import ised.tools.TimeRunnableObject;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ABDUL
 */
public class Teacher extends javax.swing.JFrame {

    private LogIn parentFrame;
    private UserAccount user;
    private SchoolYearService schoolYearService;
    private ClassScheduleService classScheduleService;
    private StudentSubjectService studentSubjectService;
    private AdviserService adviserService;
    private EnrollmentService enrollmentService;
    private SchoolYear currentSchoolYear;
    private DefaultComboBoxModel subjectLoadingComboBoxModel;
    private DefaultTableModel studentTableModel;
    private DefaultTableModel adviserTableModel;
    private DefaultTableModel subjectLoadingTableModel;
    private DefaultTableModel classStudentTableModel;
    private List<ClassSchedule> loadingList;
    private List<ClassSchedule> subjectLoadingList;
    private List<SubjectGrade> studentGradeList;
    private List<Student> studentList;
    private ClassSchedule selectedSubjectLoading;
    private SubjectGrade selectedStudent;
    private Adviser adviser;
    private int selectedGrading;
    private Lock lockObject = new ReentrantLock(true);
    private ExecutorService runner;
    private TimeRunnableObject timeObject;
    private Calendar currentDate;

    /** Creates new form Registrar */
    public Teacher(LogIn parentFrame, UserAccount user) throws ExceptionHandler {
        initComponents();
        //Theme.setDefaultLookAndFeel();
        this.parentFrame = parentFrame;
        this.user = user;
        schoolYearService = new SchoolYearServiceImpl();
        classScheduleService = new ClassScheduleServiceImpl();
        studentSubjectService = new StudentSubjectServiceImpl();
        enrollmentService = new EnrollmentServiceImpl();
        adviserService = new AdviserServiceImpl();
        studentGradeList = new ArrayList<SubjectGrade>();
        loadingList = new ArrayList<ClassSchedule>();
        subjectLoadingList = new ArrayList<ClassSchedule>();
        currentSchoolYear = schoolYearService.getCurrentSchoolYear();
        studentTableModel = (DefaultTableModel) studentTable.getModel();
        adviserTableModel = (DefaultTableModel) adviserTable.getModel();
        subjectLoadingTableModel = (DefaultTableModel) subjectLoadingTable.getModel();
        classStudentTableModel = (DefaultTableModel) classStudentTable.getModel();
        subjectLoadingComboBoxModel = (DefaultComboBoxModel) subjectLoadingComboBox.getModel();
        currentDate = null;
        displayUserInfo();
        displayEmployeeProfile();
        displaySubjectLoadingList();
        displayAdvisee();
        setSubjectLoadingComboBox();

        ComponentFormatter.stripTable(studentTable);

    }

    public void displayUserInfo() throws ExceptionHandler {
        currentDate = schoolYearService.getServerCurrentDate();
        schoolYearTextField.setText(currentSchoolYear.toString());
        dateTextField.setText(String.format("%1$tB %1$td, %1$tY", currentDate));
        userNameTextField.setText(user.getUserType());
        jLabel3.setText(user.getEmployee().getFullName());
        runner = Executors.newFixedThreadPool(1);
        timeObject = new TimeRunnableObject(lockObject, dateTextField, currentDate);
        runner.execute(timeObject);
        runner.shutdown();
    }

    private void displayStudentGradesList() throws ExceptionHandler {
        ComponentFormatter.clearTable(studentTableModel);
        selectedGrading = getGrading(gradingComboBox.getSelectedItem().toString());
        studentGradeList = studentSubjectService.getStudentGradeList(selectedSubjectLoading.getSection().getClassID(),
                selectedSubjectLoading.getSubject().getSubjectID(), selectedGrading);
        for (SubjectGrade subjectGrade : studentGradeList) {
            Student student = subjectGrade.getStudentSubject().getEnrollment().getAdmission().getStudent();
            studentTableModel.addRow(new Object[]{student.getStudentID(), student.getFullName(),
                        subjectGrade.getGradingGrade() > 0 ? subjectGrade.getGradingGrade() : ""});
        }
//        if (isGradesLocked()) {
//            studentTable.setEnabled(false);
//            lockButton.setEnabled(false);
//        } else {
//            studentTable.setEnabled(true);
//            lockButton.setEnabled(true);
//        }
    }
//
//    private boolean isGradesLocked() {
//        for (SubjectGrade subjectGrade : studentGradeList) {
//            if (subjectGrade.getIsLocked() == 1) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
//    }

    private int getGrading(String grading) {
        if (grading.equalsIgnoreCase("1st grading")) {
            return 1;
        } else if (grading.equalsIgnoreCase("2nd grading")) {
            return 2;
        } else if (grading.equalsIgnoreCase("3rd grading")) {
            return 3;
        } else if (grading.equalsIgnoreCase("4th grading")) {
            return 4;
        }
        return 0;
    }

    private void setSubjectLoadingComboBox() throws ExceptionHandler {
        loadingList = classScheduleService.getTeacherLoadingsList(user.getEmployee().getEmployeeID(), currentSchoolYear.getSchoolYearID());
        subjectLoadingComboBoxModel.removeAllElements();
        subjectLoadingComboBoxModel.addElement(null);
        for (ClassSchedule section : loadingList) {
            subjectLoadingComboBoxModel.addElement(section);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar2 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        logOutButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        sectionLabel = new javax.swing.JLabel();
        section = new javax.swing.JLabel();
        subjectLabel = new javax.swing.JLabel();
        scheduleLabel = new javax.swing.JLabel();
        subject = new javax.swing.JLabel();
        schedule = new javax.swing.JLabel();
        unitsLabel = new javax.swing.JLabel();
        units = new javax.swing.JLabel();
        gradingComboBox = new javax.swing.JComboBox();
        gradingLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        name = new javax.swing.JLabel();
        selectGradeLabel = new javax.swing.JLabel();
        gradeComboBox = new javax.swing.JComboBox();
        cancelButton = new javax.swing.JButton();
        nameLabel = new javax.swing.JLabel();
        subjectLoadingComboBox = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        pictureLabel = new javax.swing.JLabel();
        employeeProfileTable = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        subjectLoadingTable = new javax.swing.JTable();
        jLabel95 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        adviserTable = new javax.swing.JTable();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel97 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        classStudentTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        yearLevelSection = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        totalSubjectLoading = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        totalStudents = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        totalLoadingUnits = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        dateTextField = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        userNameTextField = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        schoolYearTextField = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setOpaque(false);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/update24.png"))); // NOI18N
        jButton2.setText("UPDATE ACCOUNT");
        jButton2.setFocusable(false);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setOpaque(false);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        logOutButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        logOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/lock24.png"))); // NOI18N
        logOutButton.setText("LOG OUT");
        logOutButton.setFocusable(false);
        logOutButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        logOutButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        logOutButton.setInheritsPopupMenu(true);
        logOutButton.setOpaque(false);
        logOutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(logOutButton);

        getContentPane().add(jToolBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 0, 250, 30));

        jTabbedPane1.setBackground(new java.awt.Color(0, 204, 51));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 12));

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setEnabled(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel29.setBackground(new java.awt.Color(0, 204, 51));
        jPanel29.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        studentTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID No.", "Name", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentTableMouseClicked(evt);
            }
        });
        studentTable.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                studentTableComponentAdded(evt);
            }
        });
        jScrollPane2.setViewportView(studentTable);
        studentTable.getColumnModel().getColumn(0).setMinWidth(100);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(0).setMaxWidth(100);
        studentTable.getColumnModel().getColumn(2).setMinWidth(100);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(2).setMaxWidth(100);

        jPanel5.setBackground(new java.awt.Color(0, 204, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setEnabled(false);

        sectionLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        sectionLabel.setText("Section:");
        sectionLabel.setEnabled(false);

        section.setFont(new java.awt.Font("Tahoma", 1, 12));
        section.setText("---");
        section.setEnabled(false);

        subjectLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        subjectLabel.setText("Subject:");
        subjectLabel.setEnabled(false);

        scheduleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        scheduleLabel.setText("Schedule:");
        scheduleLabel.setEnabled(false);

        subject.setFont(new java.awt.Font("Tahoma", 1, 12));
        subject.setText("---");
        subject.setEnabled(false);

        schedule.setFont(new java.awt.Font("Tahoma", 1, 12));
        schedule.setText("---");
        schedule.setEnabled(false);

        unitsLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        unitsLabel.setText("Units:");
        unitsLabel.setEnabled(false);

        units.setFont(new java.awt.Font("Tahoma", 1, 12));
        units.setText("---");
        units.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(subjectLabel)
                        .addGap(9, 9, 9)
                        .addComponent(subject, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unitsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(units, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(scheduleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(schedule, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(sectionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(section, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sectionLabel)
                    .addComponent(section))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subjectLabel)
                    .addComponent(subject)
                    .addComponent(unitsLabel)
                    .addComponent(units)
                    .addComponent(scheduleLabel)
                    .addComponent(schedule))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gradingComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        gradingComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1st Grading", "2nd Grading", "3rd Grading", "4th Grading" }));
        gradingComboBox.setEnabled(false);
        gradingComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                gradingComboBoxItemStateChanged(evt);
            }
        });
        gradingComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradingComboBoxActionPerformed(evt);
            }
        });

        gradingLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        gradingLabel.setText("Select Grading:");
        gradingLabel.setEnabled(false);

        jPanel6.setBackground(new java.awt.Color(0, 204, 51));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Enter Grade:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel6.setEnabled(false);
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        saveButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/saveIcon1.png"))); // NOI18N
        saveButton.setText("Save");
        saveButton.setEnabled(false);
        saveButton.setOpaque(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jPanel6.add(saveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 91, -1, 30));

        name.setFont(new java.awt.Font("Tahoma", 0, 12));
        name.setText("---");
        name.setEnabled(false);
        jPanel6.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 170, -1));

        selectGradeLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        selectGradeLabel.setText("Select Grade:");
        selectGradeLabel.setEnabled(false);
        jPanel6.add(selectGradeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 20));

        gradeComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        gradeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1.0", "1.25", "1.5", "1.75", "2.0", "2.25", "2.5", "2.75", "3.0", "3.25", "3.5", "3.75", "4.0", "5.0", "Clear" }));
        gradeComboBox.setEnabled(false);
        jPanel6.add(gradeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 90, -1));

        cancelButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/cancel24.png"))); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.setEnabled(false);
        cancelButton.setOpaque(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel6.add(cancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, -1, 30));

        nameLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        nameLabel.setText("Name:");
        nameLabel.setEnabled(false);
        jPanel6.add(nameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        subjectLoadingComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        subjectLoadingComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                subjectLoadingComboBoxItemStateChanged(evt);
            }
        });
        subjectLoadingComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectLoadingComboBoxActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel16.setText("Select Subject:");

        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("NOTE: You can no longer edit the grade of the student");

        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("after 2 hours once grade is entered.");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(subjectLoadingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(jLabel8))
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(gradingLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gradingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subjectLoadingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gradingComboBox)
                    .addComponent(gradingLabel))
                .addGap(10, 10, 10)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 890, 460));

        jTabbedPane1.addTab("INPUT GRADES", new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/grades.png")), jPanel1); // NOI18N

        jPanel2.setBackground(new java.awt.Color(0, 153, 51));
        jPanel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 204, 51));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pictureLabel.setBackground(new java.awt.Color(255, 255, 255));
        pictureLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        pictureLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pictureLabel.setText("PICTURE");
        pictureLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pictureLabel.setOpaque(true);

        employeeProfileTable.setFont(new java.awt.Font("Tahoma", 1, 12));
        employeeProfileTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "      PERSONAL INFORMATION"},
                {" ID NUMBER", null},
                {" NAME", null},
                {" BRITH DATE", null},
                {" GENDER", null},
                {" CIVIL STATUS", null},
                {" RELIGION", null},
                {" ADDRESS", null},
                {" CONTACT NO", " "},
                {" POSITION", null},
                {" YEAR ADMITTED", ""},
                {" STATUS", null},
                {"", "      EDUCATIONAL INFORMATION"},
                {" FINISHED DEGREE", null},
                {" SCHOOL GRADUATED", null},
                {" YEAR GRADUATED", null}
            },
            new String [] {
                "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        employeeProfileTable.setRowHeight(22);
        employeeProfileTable.setSelectionBackground(new java.awt.Color(255, 255, 255));
        employeeProfileTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        employeeProfileTable.setShowVerticalLines(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(employeeProfileTable, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(employeeProfileTable, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 800, 460));

        jTabbedPane1.addTab("PROFILE", new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/faculty.png")), jPanel2); // NOI18N

        jPanel7.setBackground(new java.awt.Color(0, 153, 51));
        jPanel7.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(0, 204, 51));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        subjectLoadingTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        subjectLoadingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject", "Schedule", "Year Level and Section"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        subjectLoadingTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subjectLoadingTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(subjectLoadingTable);
        subjectLoadingTable.getColumnModel().getColumn(0).setMinWidth(100);
        subjectLoadingTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        subjectLoadingTable.getColumnModel().getColumn(0).setMaxWidth(100);

        jPanel8.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 147, 440, 262));

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel95.setText("Advisee");
        jPanel8.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        adviserTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        adviserTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Year Level", "Section"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        adviserTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adviserTableMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(adviserTable);
        adviserTable.getColumnModel().getColumn(0).setMinWidth(100);
        adviserTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        adviserTable.getColumnModel().getColumn(0).setMaxWidth(100);

        jPanel8.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 40, 440, 60));

        jSeparator15.setBackground(new java.awt.Color(0, 204, 51));
        jPanel8.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 110, 440, 10));

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel97.setText("Subject Loadings");
        jPanel8.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 126, -1, -1));

        classStudentTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        classStudentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(classStudentTable);
        classStudentTable.getColumnModel().getColumn(1).setMinWidth(100);
        classStudentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        classStudentTable.getColumnModel().getColumn(1).setMaxWidth(100);

        jPanel8.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 320, 370));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setText("Year Level and Section:");
        jPanel8.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 150, -1));

        yearLevelSection.setFont(new java.awt.Font("Tahoma", 1, 12));
        yearLevelSection.setText("---");
        jPanel8.add(yearLevelSection, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, -1, -1));

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel98.setText("Total no. of Subject Loadings:");
        jPanel8.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 190, -1));

        totalSubjectLoading.setFont(new java.awt.Font("Tahoma", 1, 12));
        totalSubjectLoading.setText("---");
        jPanel8.add(totalSubjectLoading, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, 40, -1));

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel96.setText("Total Units:");
        jPanel8.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 440, -1, -1));

        jLabel20.setText("---");
        jPanel8.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 465, -1, -1));

        totalStudents.setFont(new java.awt.Font("Tahoma", 1, 12));
        totalStudents.setText("---");
        jPanel8.add(totalStudents, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 430, 50, -1));

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel99.setText("Total no. of Students:");
        jPanel8.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 430, -1, -1));

        totalLoadingUnits.setFont(new java.awt.Font("Tahoma", 1, 12));
        totalLoadingUnits.setText("---");
        jPanel8.add(totalLoadingUnits, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 440, 60, -1));

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 830, 470));

        jTabbedPane1.addTab("LOADINGS", new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/facultyLoad.png")), jPanel7); // NOI18N

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1250, 550));
        jTabbedPane1.getAccessibleContext().setAccessibleName("INPUT GRADES");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/date25.png"))); // NOI18N
        jLabel1.setText("Today is");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 110, -1, 30));

        dateTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        dateTextField.setText("---");
        getContentPane().add(dateTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 110, 230, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/user25.png"))); // NOI18N
        jLabel4.setText("User Type:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 80, -1, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 60, -1, 30));

        userNameTextField.setFont(new java.awt.Font("Tahoma", 1, 11));
        userNameTextField.setText("----");
        getContentPane().add(userNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 80, 140, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/user25.png"))); // NOI18N
        jLabel6.setText("Name:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 60, -1, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/trimester-label.png"))); // NOI18N
        jLabel5.setText("School Year:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 40, -1, 20));

        schoolYearTextField.setFont(new java.awt.Font("Tahoma", 1, 11));
        schoolYearTextField.setText("----");
        getContentPane().add(schoolYearTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 40, 130, 20));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/banner.jpg"))); // NOI18N
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 140));

        jMenu1.setText("File");

        jMenuItem1.setText("Update Account");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Log Out");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        // TODO add your handling code here:
        int answer = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to log out ?", "LOG OUT", javax.swing.JOptionPane.YES_NO_OPTION);
        if (answer == javax.swing.JOptionPane.YES_OPTION) {
            this.setVisible(false);
            parentFrame.setVisible(true);
            this.dispose();
        }
}//GEN-LAST:event_logOutButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {
            // TODO add your handling code here:
            Calendar currentDate = schoolYearService.getServerCurrentDate();
            if (selectedStudent != null) {
                if (selectedStudent.getDateSubmitted() != null) {
                    Calendar dateSubmitted = selectedStudent.getDateSubmitted();
                    dateSubmitted.add(Calendar.HOUR, 2);
                    if (dateSubmitted.before(currentDate)) {
                        JOptionPane.showMessageDialog(this, "You can no longer edit the grade of the student.", "Edit Grades", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                double grade;
                if (gradeComboBox.getSelectedItem().toString().equalsIgnoreCase("Clear")) {
                    grade = 0;
                } else {
                    grade = Double.parseDouble(gradeComboBox.getSelectedItem().toString());
                }

                selectedStudent.setGradingGrade(grade);
                selectedStudent.setDateSubmitted(currentDate);
                studentSubjectService.enterStudentGrade(selectedStudent);
                updateEnterGradePanel(false);
                displayStudentGradesList();
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }

}//GEN-LAST:event_saveButtonActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        updateAccount();
}//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        parentFrame.setVisible(true);
        this.dispose();
}//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
}//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        updateAccount();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void subjectLoadingComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectLoadingComboBoxActionPerformed
    }//GEN-LAST:event_subjectLoadingComboBoxActionPerformed

    private void gradingComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradingComboBoxActionPerformed
    }//GEN-LAST:event_gradingComboBoxActionPerformed

    private void studentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMouseClicked
        // TODO add your handling code here:
        int index = studentTable.getSelectedRow();
        if (index >= 0) {
            SubjectGrade subjectGrade = studentGradeList.get(index);
            selectedStudent = subjectGrade;
            updateEnterGradePanel(true);
            name.setText(selectedStudent.getStudentSubject().getEnrollment().getAdmission().getStudent().getFullName());
            gradingComboBox.setSelectedItem(Double.toString(selectedStudent.getGradingGrade()));
        }
    }//GEN-LAST:event_studentTableMouseClicked

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        updateEnterGradePanel(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to exit?", "Exit Window", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }//GEN-LAST:event_formWindowClosing

    private void studentTableComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_studentTableComponentAdded
    }//GEN-LAST:event_studentTableComponentAdded

    private void subjectLoadingTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subjectLoadingTableMouseClicked
        // TODO add your handling code here:
        try {
            int index = subjectLoadingTable.getSelectedRow();
            if (index >= 0) {
                ClassSchedule classSchedule = subjectLoadingList.get(index);
                yearLevelSection.setText(getYearLevel(classSchedule.getSection().getYearLevel()) + " - " + classSchedule.getSection().getSection().getSectionName());
                displayStudentList(classSchedule.getSection().getClassID());
            }
        } catch (ExceptionHandler ex) {
        }
    }//GEN-LAST:event_subjectLoadingTableMouseClicked

    private void adviserTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adviserTableMouseClicked
        // TODO add your handling code here:
        try {
            int index = adviserTable.getSelectedRow();
            if (index >= 0) {
                displayStudentList(adviser.getSection().getClassID());
            }
        } catch (ExceptionHandler ex) {
        }
    }//GEN-LAST:event_adviserTableMouseClicked

    private void gradingComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_gradingComboBoxItemStateChanged
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            displayStudentGradesList();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_gradingComboBoxItemStateChanged

    private void subjectLoadingComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_subjectLoadingComboBoxItemStateChanged
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            selectedSubjectLoading = (ClassSchedule) subjectLoadingComboBox.getSelectedItem();
            if (selectedSubjectLoading == null) {
                updateClassScheduleInfoPanel(false);
                ComponentFormatter.clearTable(studentTableModel);
            } else {
                updateClassScheduleInfoPanel(true);
                displayClassScheduleInfo();
                displayStudentGradesList();
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_subjectLoadingComboBoxItemStateChanged

    private void displayStudentList(int classID) throws ExceptionHandler {
        studentList = enrollmentService.getStudentsListBySection(currentSchoolYear.getSchoolYearID(), classID);
        ComponentFormatter.clearTable(classStudentTableModel);
        for (Student student : studentList) {
            classStudentTableModel.addRow(new Object[]{student.getFullName(), student.getGender()});
        }
        totalStudents.setText(studentList.size() + "");
    }

    public String getYearLevel(int yearLevel) {
        if (yearLevel == 1) {
            return "1st year";
        } else if (yearLevel == 2) {
            return "2nd year";
        } else if (yearLevel == 3) {
            return "3rd year";
        } else if (yearLevel == 4) {
            return "4th year";
        }

        return "";
    }

//    private boolean isOkToLock() {
//        for (SubjectGrade subjectGrade : studentGradeList) {
//            if (subjectGrade.getGradingGrade() <= 0) {
//                JOptionPane.showMessageDialog(this, "Grades can't be locked because there are empty grades on student list", "Lock Grades", JOptionPane.WARNING_MESSAGE);
//                return false;
//            }
//        }
//        return true;
//    }
    private void updateEnterGradePanel(boolean flag) {
        nameLabel.setEnabled(flag);
        name.setEnabled(flag);
        selectGradeLabel.setEnabled(flag);
        gradeComboBox.setEnabled(flag);
        saveButton.setEnabled(flag);
        cancelButton.setEnabled(flag);
        if (flag == false) {
            name.setText("---");
            //gradeComboBox.setSelectedIndex(0);
        }
    }

    private void updateClassScheduleInfoPanel(boolean flag) {
        sectionLabel.setEnabled(flag);
        section.setEnabled(flag);
        subjectLabel.setEnabled(flag);
        subject.setEnabled(flag);
        unitsLabel.setEnabled(flag);
        units.setEnabled(flag);
        schedule.setEnabled(flag);
        scheduleLabel.setEnabled(flag);
        gradingLabel.setEnabled(flag);
        gradingComboBox.setEnabled(flag);
        studentTable.setEnabled(flag);
        if (flag == false) {
            section.setText("---");
            subject.setText("---");
            units.setText("---");
            schedule.setText("---");
            gradingComboBox.setSelectedIndex(0);
            ComponentFormatter.clearTable(studentTableModel);
            updateEnterGradePanel(false);
        }
    }

    private void displayClassScheduleInfo() {
        section.setText(selectedSubjectLoading.getSection().toString());
        subject.setText(selectedSubjectLoading.getSubject().getSubjectCode());
        units.setText(Double.toString(selectedSubjectLoading.getSubject().getUnits()));
        schedule.setText(ComponentFormatter.formatTimeToString(selectedSubjectLoading.getSchedule().getStartTime())
                + " - " + ComponentFormatter.formatTimeToString(selectedSubjectLoading.getSchedule().getEndTime()));
    }

    private void updateAccount() {
        UpdateAccountDialog updateAccount = new UpdateAccountDialog(this, true, user);
        updateAccount.setVisible(true);
        user = updateAccount.getUserAccount();
        updateAccount.dispose();
        userNameTextField.setText(user.getUserName());
    }

    public void displayEmployeeProfile() {
        Employee employee = user.getEmployee();
        if (employee != null) {
            employeeProfileTable.setValueAt(Integer.toString(employee.getEmployeeID()), 1, 1);
            employeeProfileTable.setValueAt(employee.getFullName(), 2, 1);
            employeeProfileTable.setValueAt(ComponentFormatter.formatMonthDateYear(employee.getDOB()).toString(), 3, 1);
            employeeProfileTable.setValueAt(employee.getGender(), 4, 1);
            employeeProfileTable.setValueAt(employee.getCivilStatus(), 5, 1);
            employeeProfileTable.setValueAt(employee.getReligion(), 6, 1);
            employeeProfileTable.setValueAt(employee.getAddress(), 7, 1);
            employeeProfileTable.setValueAt(employee.getContactNo(), 8, 1);
            employeeProfileTable.setValueAt(employee.getPosition(), 9, 1);
            employeeProfileTable.setValueAt(employee.getYearAdmitted(), 10, 1);
            employeeProfileTable.setValueAt(employee.getStatus(), 11, 1);
            employeeProfileTable.setValueAt(employee.getFinishedDegree(), 13, 1);
            employeeProfileTable.setValueAt(employee.getSchoolGraduated(), 14, 1);
            employeeProfileTable.setValueAt(employee.getYearGraduated(), 15, 1);

            if (employee.getPicture() != null) {
                Object obj = employee.getPicture();
                pictureLabel.setText("");
                if (obj instanceof com.mysql.jdbc.Blob) {
                    pictureLabel.setIcon(new javax.swing.ImageIcon(ComponentFormatter.convertToActualSizeImage((Blob) obj)));
                } else {
                    Image image = Toolkit.getDefaultToolkit().getImage(obj.toString()).getScaledInstance(ComponentFormatter.IMAGE_WIDTH, ComponentFormatter.IMAGE_HEIGHT, 129);
                    obj = new javax.swing.ImageIcon(image);
                    pictureLabel.setIcon((Icon) obj);
                }
            } else {
                pictureLabel.setText("PICTURE");
                pictureLabel.setIcon(null);
            }
        }
    }

    public void displaySubjectLoadingList() throws ExceptionHandler {
        double totalUnits = 0;
        subjectLoadingList = classScheduleService.getTeacherLoadingsList(user.getEmployee().getEmployeeID(), currentSchoolYear.getSchoolYearID());
        ComponentFormatter.clearTable(subjectLoadingTableModel);
        if (subjectLoadingList != null) {
            for (ClassSchedule classSchedule : subjectLoadingList) {
                subjectLoadingTableModel.addRow(new Object[]{classSchedule.getSubject().getSubjectCode(), classSchedule.getSchedule().getDays() + " "
                            + ComponentFormatter.formatTimeToString(classSchedule.getSchedule().getStartTime()) + " - "
                            + ComponentFormatter.formatTimeToString(classSchedule.getSchedule().getEndTime()),
                            getYearLevelText(classSchedule.getSection().getYearLevel()) + " - " + classSchedule.getSection().getSection().getSectionName()});
                totalUnits += classSchedule.getSubject().getUnits();
            }
        }

        totalLoadingUnits.setText(totalUnits + "");
        totalSubjectLoading.setText(subjectLoadingList.size() + "");
    }

    private String getYearLevelText(int yearLevel) {
        if (yearLevel == 1) {
            return "I";
        } else if (yearLevel == 2) {
            return "II";
        } else if (yearLevel == 3) {
            return "III";
        } else if (yearLevel == 4) {
            return "IV";
        }
        return null;
    }

    private void displayAdvisee() throws ExceptionHandler {
        ComponentFormatter.clearTable(adviserTableModel);
        adviser = adviserService.getAdvisse(user.getEmployee().getEmployeeID(), currentSchoolYear.getSchoolYearID());
        if (adviser != null) {
            adviserTableModel.addRow(new Object[]{getYearLevelText(adviser.getSection().getYearLevel()), adviser.getSection().getSection().getSectionName()});
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable adviserTable;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTable classStudentTable;
    private javax.swing.JLabel dateTextField;
    private javax.swing.JTable employeeProfileTable;
    private javax.swing.JComboBox gradeComboBox;
    private javax.swing.JComboBox gradingComboBox;
    private javax.swing.JLabel gradingLabel;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton logOutButton;
    private javax.swing.JLabel name;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel pictureLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel schedule;
    private javax.swing.JLabel scheduleLabel;
    private javax.swing.JLabel schoolYearTextField;
    private javax.swing.JLabel section;
    private javax.swing.JLabel sectionLabel;
    private javax.swing.JLabel selectGradeLabel;
    private javax.swing.JTable studentTable;
    private javax.swing.JLabel subject;
    private javax.swing.JLabel subjectLabel;
    private javax.swing.JComboBox subjectLoadingComboBox;
    private javax.swing.JTable subjectLoadingTable;
    private javax.swing.JLabel totalLoadingUnits;
    private javax.swing.JLabel totalStudents;
    private javax.swing.JLabel totalSubjectLoading;
    private javax.swing.JLabel units;
    private javax.swing.JLabel unitsLabel;
    private javax.swing.JLabel userNameTextField;
    private javax.swing.JLabel yearLevelSection;
    // End of variables declaration//GEN-END:variables
}

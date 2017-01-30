/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FacultyLoadingPanel.java
 *
 * Created on Nov 5, 2011, 6:35:52 PM
 */
package ised.gui.panel;

import ised.gui.Principal;
import ised.gui.dialog.AdviserDialog;
import ised.model.Adviser;
import ised.model.Class;
import ised.model.ClassSchedule;
import ised.model.Employee;
import ised.model.SchoolYear;
import ised.model.Section;
import ised.service.implementation.AdviserServiceImpl;
import ised.service.implementation.ClassScheduleServiceImpl;
import ised.service.implementation.ClassServiceImpl;
import ised.service.implementation.EmployeeServiceImpl;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.implementation.SectionServiceImpl;
import ised.service.implementation.SubjectServiceImpl;
import ised.service.interfaces.AdviserService;
import ised.service.interfaces.ClassScheduleService;
import ised.service.interfaces.ClassService;
import ised.service.interfaces.EmployeeService;
import ised.service.interfaces.SchoolYearService;
import ised.service.interfaces.SectionService;
import ised.service.interfaces.SubjectService;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Blob;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rodel
 */
public class FacultyLoadingPanel extends javax.swing.JPanel {

    private Principal parentFrame;
    private SchoolYear currentSchoolYear;
    private EmployeeService employeeService;
    private SubjectService subjectservice;
    private Employee selectedFaculty;
    private ClassService classService;
    private SchoolYearService schoolYearService;
    private ClassScheduleService classScheduleService;
    private AdviserService adviserService;
    private SectionService sectionService;
    private Section section;
    private ArrayList<ClassSchedule> csList = new ArrayList<ClassSchedule>();
    private ClassSchedule classSchedule;
    private DefaultTableModel facultyTableModel;
    private DefaultTableModel adviseeTableModel;
    private DefaultTableModel subjectLoadingTableModel;
    private DefaultTableModel subjectTableModel;
    private List<Employee> facultyList;
    private List<Class> sectionList;
    private List<ClassSchedule> loadingList;
    private List<ClassSchedule> subjectList;
    private List<SchoolYear> schoolYearList;
    private Adviser advisee;
    private SchoolYear selectedSchoolYear;

    /** Creates new form FacultyLoadingPanel */
    public FacultyLoadingPanel(Principal parentFrame, SchoolYear schoolYear) throws ExceptionHandler {
        initComponents();
        this.parentFrame = parentFrame;
        employeeService = new EmployeeServiceImpl();
        subjectservice = new SubjectServiceImpl();
        sectionService = new SectionServiceImpl();
        classScheduleService = new ClassScheduleServiceImpl();
        schoolYearService = new SchoolYearServiceImpl();
        adviserService = new AdviserServiceImpl();
        classService = new ClassServiceImpl();
        advisee = null;
        facultyList = new ArrayList<Employee>();
        sectionList = new ArrayList<Class>();
        loadingList = new ArrayList<ClassSchedule>();
        subjectList = new ArrayList<ClassSchedule>();
        schoolYearList = new ArrayList<SchoolYear>();
        selectedFaculty = null;
        facultyTableModel = (DefaultTableModel) facultyTable.getModel();
        adviseeTableModel = (DefaultTableModel) adviseeTable.getModel();
        subjectLoadingTableModel = (DefaultTableModel) subjectLoadingTable.getModel();
        subjectTableModel = (DefaultTableModel) subjectTable.getModel();
        currentSchoolYear = schoolYear;
        selectedSchoolYear = schoolYear;
        displayEmployeeList();
        displaySchoolYearList();

        ComponentFormatter.stripTable(facultyTable);
        ComponentFormatter.stripTable(subjectLoadingTable);
        ComponentFormatter.stripTable(subjectTable);
    }

    private void displayEmployeeList() throws ExceptionHandler {
        facultyList = employeeService.getFacultyList();
        ComponentFormatter.clearTable(facultyTableModel);
        for (Employee employee : facultyList) {
            facultyTableModel.addRow(new Object[]{employee.getEmployeeID(), employee.getFullName()});
        }
    }

    private void displaySchoolYearList() throws ExceptionHandler {
        DefaultComboBoxModel schoolYearComboBoxModel = (DefaultComboBoxModel) schoolYearComboBox.getModel();
        schoolYearList = schoolYearService.getSchoolYearList();
        schoolYearComboBoxModel.removeAllElements();
        for (SchoolYear schoolYear : schoolYearList) {
            schoolYearComboBoxModel.addElement(schoolYear);
        }
        schoolYearComboBoxModel.setSelectedItem(currentSchoolYear);
    }

    private void displaySubjectLoadingList() throws ExceptionHandler {
        double totalUnits = 0;
        if (selectedFaculty != null) {
            loadingList = classScheduleService.getTeacherLoadingsList(selectedFaculty.getEmployeeID(), selectedSchoolYear.getSchoolYearID());
            ComponentFormatter.clearTable(subjectLoadingTableModel);
            for (ClassSchedule classSchedule : loadingList) {
                subjectLoadingTableModel.addRow(new Object[]{classSchedule.getSubject().getSubjectCode(), classSchedule.getSchedule().getDays() + " "
                            + ComponentFormatter.formatTimeToString(classSchedule.getSchedule().getStartTime()) + " - "
                            + ComponentFormatter.formatTimeToString(classSchedule.getSchedule().getEndTime()),
                            classSchedule.getSection().getSection().getSectionName()});
                totalUnits += classSchedule.getSubject().getUnits();
            }
            totalSubjectLoadings.setText(Integer.toString(subjectLoadingTableModel.getRowCount()));
            totalLoadingUnits.setText(Double.toString(totalUnits));
        }
    }

    private void displayAdvisee() throws ExceptionHandler {
        ComponentFormatter.clearTable(adviseeTableModel);
        if (selectedFaculty != null) {
            advisee = adviserService.getAdvisse(selectedFaculty.getEmployeeID(), selectedSchoolYear.getSchoolYearID());
            if (advisee != null) {
                adviseeTableModel.addRow(new Object[]{advisee.getSection().getYearLevel(), advisee.getSection().getSection().getSectionName()});
            }
            updateAdviseeButtons();
        }
    }

    private void updateAdviseeButtons() {
        if (selectedSchoolYear.getSchoolYearID() == currentSchoolYear.getSchoolYearID()) {
            if (advisee != null) {
                addAdviseeButton.setEnabled(false);
                editAdviseeButton.setEnabled(true);
                deleteAdviseeButton.setEnabled(true);
            } else {
                addAdviseeButton.setEnabled(true);
                editAdviseeButton.setEnabled(false);
                deleteAdviseeButton.setEnabled(false);
            }
        }
    }

    public void setSectionComboBox() {
        try {
            int yearLevel = 0;
            String yearLevelText = String.valueOf(yearLevelComboBox.getSelectedItem());
            if (yearLevelText.equals("1st year")) {
                yearLevel = 1;
            } else if (yearLevelText.equals("2nd year")) {
                yearLevel = 2;
            } else if (yearLevelText.equals("3rd year")) {
                yearLevel = 3;
            } else if (yearLevelText.equals("4th year")) {
                yearLevel = 4;
            }
            if (yearLevel > 0) {
                sectionList = classService.getClassList(currentSchoolYear.getSchoolYearID(), yearLevel);
                sectionComboBox.removeAllItems();
                sectionComboBox.addItem("<Choose Section>");
                for (Class section : sectionList) {
                    sectionComboBox.addItem(section);
                }
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(FacultyLoadingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isTimeConflict(Time fTime1, Time tTime1, Time fTime2, Time tTime2) {
        return (fTime1.equals(fTime2) || (tTime1.equals(tTime2) || (fTime1.before(tTime2) && tTime1.after(fTime2))));
    }

    private String getRemarks(ClassSchedule classSchedule) {
        if (classSchedule.getTeacher() != null) {
            return "Assigned";
        } else {
            if (isConflict(classSchedule)) {
                return "Conflict";
            }
        }
        return "";
    }

    private boolean isConflict(ClassSchedule sched) {
        char[] schedDays = sched.getSchedule().getDays().toCharArray();
        for (ClassSchedule sc : loadingList) {
            char[] scDays = sc.getSchedule().getDays().toCharArray();
            for (int i = 0; i < scDays.length; i++) {
                for (int j = 0; j < schedDays.length; j++) {
                    if (schedDays[j] == scDays[i]) {
                        if (isTimeConflict(sched.getSchedule().getStartTime(), sched.getSchedule().getEndTime(),
                                sc.getSchedule().getStartTime(), sc.getSchedule().getEndTime())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void displayClassScheduleList() throws ExceptionHandler {
        Class selectedSection = (Class) sectionComboBox.getSelectedItem();
        subjectList = classScheduleService.getSubjectList(selectedSection.getClassID());
        ComponentFormatter.clearTable(subjectTableModel);
        for (ClassSchedule classSchedule : subjectList) {
            subjectTableModel.addRow(new Object[]{classSchedule.getSubject().getSubjectCode(), classSchedule.getSchedule().getDays() + " "
                        + ComponentFormatter.formatTimeToString(classSchedule.getSchedule().getStartTime()) + " - "
                        + ComponentFormatter.formatTimeToString(classSchedule.getSchedule().getEndTime()), isConflict(classSchedule) ? "Conflict" : "",
                        classSchedule.getTeacher() == null ? "Not Assigned" : classSchedule.getTeacher()});
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

        jPanel16 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        facultyTable = new javax.swing.JTable();
        jPanel38 = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        searchButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        pictureLabel = new javax.swing.JLabel();
        employeeProfileTable = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        adviseeTable = new javax.swing.JTable();
        jLabel102 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        addAdviseeButton = new javax.swing.JButton();
        deleteAdviseeButton = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        subjectLoadingTable = new javax.swing.JTable();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        totalSubjectLoadings = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        totalLoadingUnits = new javax.swing.JLabel();
        removeLoadingButton = new javax.swing.JButton();
        editAdviseeButton = new javax.swing.JButton();
        schoolYearComboBox = new javax.swing.JComboBox();
        jLabel106 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jSeparator17 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        yearLevelComboBox = new javax.swing.JComboBox();
        sectionComboBox = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        addSubjectLoadingButton = new javax.swing.JButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        subjectTable = new javax.swing.JTable();

        jPanel16.setBackground(new java.awt.Color(0, 153, 51));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel22.setBackground(new java.awt.Color(0, 204, 51));
        jPanel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel22.setFont(new java.awt.Font("Tahoma", 1, 12));

        facultyTable.setAutoCreateRowSorter(true);
        facultyTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        facultyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID No.", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        facultyTable.setDoubleBuffered(true);
        facultyTable.setDragEnabled(true);
        facultyTable.getTableHeader().setResizingAllowed(false);
        facultyTable.getTableHeader().setReorderingAllowed(false);
        facultyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                facultyTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                facultyTableMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                facultyTableMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(facultyTable);
        facultyTable.getColumnModel().getColumn(0).setMinWidth(60);
        facultyTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        facultyTable.getColumnModel().getColumn(0).setMaxWidth(60);

        jPanel38.setBackground(new java.awt.Color(0, 204, 51));
        jPanel38.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
        searchTextField.setText("ID No. or Name");
        searchTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTextFieldMouseClicked(evt);
            }
        });
        jPanel38.add(searchTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 140, 20));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel63.setText("Search:");
        jPanel38.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        searchButton1.setBackground(new java.awt.Color(0, 204, 51));
        searchButton1.setFont(new java.awt.Font("Tahoma", 1, 12));
        searchButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/find.png"))); // NOI18N
        searchButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButton1ActionPerformed(evt);
            }
        });
        jPanel38.add(searchButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 40, 20));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addComponent(jPanel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );

        jPanel16.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 270, 490));

        jPanel4.setBackground(new java.awt.Color(0, 204, 51));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pictureLabel.setBackground(new java.awt.Color(255, 255, 255));
        pictureLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        pictureLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pictureLabel.setText("PICTURE");
        pictureLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pictureLabel.setOpaque(true);
        jPanel4.add(pictureLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 150, 150));

        employeeProfileTable.setFont(new java.awt.Font("Tahoma", 1, 12));
        employeeProfileTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {" ID NO.", "     "},
                {" NAME", null}
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
        jPanel4.add(employeeProfileTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 260, -1));
        employeeProfileTable.getColumnModel().getColumn(0).setMinWidth(75);
        employeeProfileTable.getColumnModel().getColumn(0).setPreferredWidth(75);
        employeeProfileTable.getColumnModel().getColumn(0).setMaxWidth(75);

        adviseeTable.setFont(new java.awt.Font("Tahoma", 0, 13));
        adviseeTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane14.setViewportView(adviseeTable);
        adviseeTable.getColumnModel().getColumn(0).setMinWidth(100);
        adviseeTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        adviseeTable.getColumnModel().getColumn(0).setMaxWidth(100);

        jPanel4.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 260, 50));

        jLabel102.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel102.setText("Advisee");
        jPanel4.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, -1, -1));
        jPanel4.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 186, -1, 10));

        addAdviseeButton.setBackground(new java.awt.Color(204, 204, 204));
        addAdviseeButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        addAdviseeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/add.png"))); // NOI18N
        addAdviseeButton.setText("Add");
        addAdviseeButton.setEnabled(false);
        addAdviseeButton.setOpaque(false);
        addAdviseeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAdviseeButtonActionPerformed(evt);
            }
        });
        jPanel4.add(addAdviseeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 80, 25));

        deleteAdviseeButton.setBackground(new java.awt.Color(204, 204, 204));
        deleteAdviseeButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        deleteAdviseeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/delete.png"))); // NOI18N
        deleteAdviseeButton.setText("Delete");
        deleteAdviseeButton.setEnabled(false);
        deleteAdviseeButton.setOpaque(false);
        deleteAdviseeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAdviseeButtonActionPerformed(evt);
            }
        });
        jPanel4.add(deleteAdviseeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, 90, -1));

        subjectLoadingTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        subjectLoadingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject", "Schedule", "Section"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane15.setViewportView(subjectLoadingTable);
        subjectLoadingTable.getColumnModel().getColumn(0).setMinWidth(100);
        subjectLoadingTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        subjectLoadingTable.getColumnModel().getColumn(0).setMaxWidth(100);
        subjectLoadingTable.getColumnModel().getColumn(1).setMinWidth(230);
        subjectLoadingTable.getColumnModel().getColumn(1).setPreferredWidth(230);
        subjectLoadingTable.getColumnModel().getColumn(1).setMaxWidth(230);
        subjectLoadingTable.getColumnModel().getColumn(2).setMinWidth(100);
        subjectLoadingTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        subjectLoadingTable.getColumnModel().getColumn(2).setMaxWidth(100);

        jPanel4.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 420, 190));

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel103.setText("School Year:");
        jPanel4.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, 20));

        jLabel104.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel104.setText("Total no. of Subject Loadings:");
        jPanel4.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        totalSubjectLoadings.setFont(new java.awt.Font("Tahoma", 1, 12));
        totalSubjectLoadings.setText("---");
        jPanel4.add(totalSubjectLoadings, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 430, -1, -1));

        jLabel105.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel105.setText("Total Units:");
        jPanel4.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 430, -1, -1));

        totalLoadingUnits.setFont(new java.awt.Font("Tahoma", 1, 12));
        totalLoadingUnits.setText("---");
        jPanel4.add(totalLoadingUnits, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 430, -1, -1));

        removeLoadingButton.setBackground(new java.awt.Color(204, 204, 204));
        removeLoadingButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        removeLoadingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/delete18.png"))); // NOI18N
        removeLoadingButton.setText("Remove Subject Loading");
        removeLoadingButton.setOpaque(false);
        removeLoadingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeLoadingButtonActionPerformed(evt);
            }
        });
        jPanel4.add(removeLoadingButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 190, -1));

        editAdviseeButton.setBackground(new java.awt.Color(204, 204, 204));
        editAdviseeButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        editAdviseeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/pencil.png"))); // NOI18N
        editAdviseeButton.setText("Edit");
        editAdviseeButton.setEnabled(false);
        editAdviseeButton.setOpaque(false);
        editAdviseeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editAdviseeButtonActionPerformed(evt);
            }
        });
        jPanel4.add(editAdviseeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 80, 25));

        schoolYearComboBox.setFont(new java.awt.Font("Tahoma", 1, 12));
        schoolYearComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        schoolYearComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                schoolYearComboBoxItemStateChanged(evt);
            }
        });
        jPanel4.add(schoolYearComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 130, 20));

        jLabel106.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel106.setText("Subject Assignment");
        jPanel4.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        jPanel16.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 460, 490));

        jPanel5.setBackground(new java.awt.Color(0, 204, 51));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel6.setBackground(new java.awt.Color(0, 204, 51));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "View Subject by"));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        yearLevelComboBox.setFont(new java.awt.Font("Tahoma", 1, 12));
        yearLevelComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Choose Year Level>", "1st year", "2nd year", "3rd year", "4th year" }));
        yearLevelComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                yearLevelComboBoxItemStateChanged(evt);
            }
        });
        yearLevelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearLevelComboBoxActionPerformed(evt);
            }
        });
        jPanel6.add(yearLevelComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 180, 30));

        sectionComboBox.setFont(new java.awt.Font("Tahoma", 1, 12));
        sectionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Choose Section>" }));
        sectionComboBox.setEnabled(false);
        sectionComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sectionComboBoxItemStateChanged(evt);
            }
        });
        sectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionComboBoxActionPerformed(evt);
            }
        });
        jPanel6.add(sectionComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 170, 30));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jButton1.setText("Go");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 20, -1, 30));

        addSubjectLoadingButton.setFont(new java.awt.Font("Tahoma", 1, 14));
        addSubjectLoadingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/load.png"))); // NOI18N
        addSubjectLoadingButton.setText("Load");
        addSubjectLoadingButton.setOpaque(false);
        addSubjectLoadingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSubjectLoadingButtonActionPerformed(evt);
            }
        });

        subjectTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        subjectTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject Code", "Schedule", "Conflict", "Teacher"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane16.setViewportView(subjectTable);
        subjectTable.getColumnModel().getColumn(0).setMinWidth(80);
        subjectTable.getColumnModel().getColumn(0).setMaxWidth(80);
        subjectTable.getColumnModel().getColumn(2).setMinWidth(50);
        subjectTable.getColumnModel().getColumn(2).setMaxWidth(50);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(addSubjectLoadingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addSubjectLoadingButton, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 20, 450, 490));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 1267, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void facultyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_facultyTableMouseClicked
        // TODO add your handling code here:
        try {
            int index = facultyTable.getSelectedRow();
            if (index >= 0) {
                selectedFaculty = facultyList.get(index);
                employeeProfileTable.setValueAt(selectedFaculty.getEmployeeID(), 0, 1);
                employeeProfileTable.setValueAt(selectedFaculty.getFullName(), 1, 1);
                if (selectedFaculty.getPicture() != null) {
                    Object obj = selectedFaculty.getPicture();
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
                schoolYearComboBox.setEnabled(true);
                displayAdvisee();
                displaySubjectLoadingList();
            }
        } catch (ExceptionHandler ex) {
            //Logger.getLogger(FacultyLoadingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_facultyTableMouseClicked

    private void facultyTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_facultyTableMouseEntered
        // TODO add your handling code here:
}//GEN-LAST:event_facultyTableMouseEntered

    private void facultyTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_facultyTableMousePressed
}//GEN-LAST:event_facultyTableMousePressed

    private void searchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseClicked
        // TODO add your handling code here:
        searchTextField.setText("");
}//GEN-LAST:event_searchTextFieldMouseClicked

    private void searchButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButton1ActionPerformed

    private void deleteAdviseeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAdviseeButtonActionPerformed
        try {
            // TODO add your handling code here:
            int option = JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to delete this advisee?",
                    "Delete Advisee", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                adviserService.deleteAdvisee(advisee.getAdviserID());
                displayAdvisee();
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(FacultyLoadingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_deleteAdviseeButtonActionPerformed

    private void removeLoadingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeLoadingButtonActionPerformed
        // TODO add your handling code here:
        try {
            int index = subjectLoadingTable.getSelectedRow();
            if (index >= 0) {
                int option = JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to delete this schedule?",
                        "Delete Subject Loading", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    ClassSchedule classSchedule = loadingList.get(index);
                    classScheduleService.removeTeacher(classSchedule.getClassScheduleID());
                    JOptionPane.showMessageDialog(parentFrame, "Schedule is successfully deleted", "Delete Student", JOptionPane.INFORMATION_MESSAGE);
                    displaySubjectLoadingList();
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Please select a loading", "Delete Subject Loading", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ExceptionHandler ex) {
            //Logger.getLogger(FacultyLoadingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_removeLoadingButtonActionPerformed

    private void yearLevelComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_yearLevelComboBoxItemStateChanged
        int index = yearLevelComboBox.getSelectedIndex();
        if (index != 0) {
            sectionComboBox.setEnabled(true);
            setSectionComboBox();
        } else if (index == 0) {
            sectionComboBox.setSelectedIndex(0);
            sectionComboBox.setEnabled(false);
        }
}//GEN-LAST:event_yearLevelComboBoxItemStateChanged

    private void addSubjectLoadingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSubjectLoadingButtonActionPerformed
        // TODO add your handling code here:
        int index = subjectTable.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(parentFrame, "Please select subject", "Add Subject Loading", JOptionPane.WARNING_MESSAGE);
            return;
        }


        ClassSchedule classSchedule = subjectList.get(index);
        String remarks = getRemarks(classSchedule);
        if (remarks.equalsIgnoreCase("Assigned")) {
            JOptionPane.showMessageDialog(parentFrame, "Class Schedule is already assigned", "Add Subject Loading", JOptionPane.WARNING_MESSAGE);
        } else if (remarks.equalsIgnoreCase("Conflict")) {
            JOptionPane.showMessageDialog(parentFrame, "Class Schedule is conflict to selected teacher's schedule", "Add Subject Loading", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                classScheduleService.setTeacher(classSchedule.getClassScheduleID(), selectedFaculty.getEmployeeID());
                displaySubjectLoadingList();
                displayClassScheduleList();
                //JOptionPane.showMessageDialog(parentFrame, "Class Schedule is successfully added to the teacher's schedule", "Add Subject Loading", JOptionPane.INFORMATION_MESSAGE);
            } catch (ExceptionHandler ex) {
                Logger.getLogger(FacultyLoadingPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

}//GEN-LAST:event_addSubjectLoadingButtonActionPerformed

    private void sectionComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sectionComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sectionComboBoxItemStateChanged

    private void yearLevelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearLevelComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearLevelComboBoxActionPerformed

    private void sectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sectionComboBoxActionPerformed

    private void addAdviseeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAdviseeButtonActionPerformed
        // TODO add your handling code here:
        try {
            AdviserDialog addAdvisee = new AdviserDialog(parentFrame, true, selectedFaculty, null, currentSchoolYear);
            addAdvisee.setVisible(true);
            if (addAdvisee.success) {
                displayAdvisee();
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(FacultyLoadingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_addAdviseeButtonActionPerformed

    private void editAdviseeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editAdviseeButtonActionPerformed
        // TODO add your handling code here:
        try {
            AdviserDialog editAdvisee = new AdviserDialog(parentFrame, true, null, advisee, currentSchoolYear);
            editAdvisee.setVisible(true);
            if (editAdvisee.success) {
                displayAdvisee();
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(FacultyLoadingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editAdviseeButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (sectionComboBox.getSelectedIndex() > 0) {
            try {
                if (selectedFaculty != null) {
                    displayClassScheduleList();
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Pls. select a faculty", "Add Subject Loading", JOptionPane.WARNING_MESSAGE);
                }
            } catch (ExceptionHandler ex) {
                Logger.getLogger(FacultyLoadingPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void schoolYearComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_schoolYearComboBoxItemStateChanged
        try {
            // TODO add your handling code here:
            if (selectedFaculty != null) {
                selectedSchoolYear = (SchoolYear) schoolYearComboBox.getSelectedItem();
                if (selectedSchoolYear.getSchoolYearID() != currentSchoolYear.getSchoolYearID()) {
                    removeLoadingButton.setEnabled(false);
                    addAdviseeButton.setEnabled(false);
                    editAdviseeButton.setEnabled(false);
                    deleteAdviseeButton.setEnabled(false);
                    addSubjectLoadingButton.setEnabled(false);
                } else {
                    addSubjectLoadingButton.setEnabled(true);
                    removeLoadingButton.setEnabled(true);
                }
                displayAdvisee();
                displaySubjectLoadingList();
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(FacultyLoadingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_schoolYearComboBoxItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAdviseeButton;
    private javax.swing.JButton addSubjectLoadingButton;
    private javax.swing.JTable adviseeTable;
    private javax.swing.JButton deleteAdviseeButton;
    private javax.swing.JButton editAdviseeButton;
    private javax.swing.JTable employeeProfileTable;
    private javax.swing.JTable facultyTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JLabel pictureLabel;
    private javax.swing.JButton removeLoadingButton;
    private javax.swing.JComboBox schoolYearComboBox;
    private javax.swing.JButton searchButton1;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JComboBox sectionComboBox;
    private javax.swing.JTable subjectLoadingTable;
    private javax.swing.JTable subjectTable;
    private javax.swing.JLabel totalLoadingUnits;
    private javax.swing.JLabel totalSubjectLoadings;
    private javax.swing.JComboBox yearLevelComboBox;
    // End of variables declaration//GEN-END:variables
}

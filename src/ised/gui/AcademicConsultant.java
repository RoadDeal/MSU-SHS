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

import ised.gui.dialog.CurriculumDialog;
import ised.gui.dialog.EditSubjectDialog;
import ised.gui.dialog.UpdateAccountDialog;
import ised.model.Curriculum;
import ised.model.UserAccount;
import ised.model.SchoolYear;
import ised.model.Subject;
import ised.service.implementation.CurriculumServiceImpl;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.implementation.SubjectServiceImpl;
import ised.service.interfaces.CurriculumService;
import ised.service.interfaces.SchoolYearService;
import ised.service.interfaces.SubjectService;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import ised.tools.TimeRunnableObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ABDUL
 */
public final class AcademicConsultant extends javax.swing.JFrame {

    LogIn parentFrame;
    UserAccount user;
    private List<Subject> subjectList;
    private List<Subject> subjectFromPrevCurrList;
    private List<Curriculum> curriculumList;
    private SchoolYear currentSchoolYear;
    private DefaultTableModel subjectTableModel;
    private DefaultTableModel addSubjectTableModel;
    private DefaultTableModel curriculumTableModel;
    private SchoolYearService schoolYearService;
    private CurriculumService curriculumService;
    private SubjectService subjectService;
    private Curriculum selectedCurriculum;
    private Curriculum currentCurriculum;
    private Lock lockObject = new ReentrantLock( true );
    private ExecutorService runner;
    private TimeRunnableObject timeObject;

    /** Creates new form Registrar */
    public AcademicConsultant(LogIn parentFrame, UserAccount user) throws ExceptionHandler {
        initComponents();
        //Theme.setDefaultLookAndFeel();
        this.parentFrame = parentFrame;
        this.user = user;
        subjectTableModel = (DefaultTableModel) subjectTable.getModel();
        addSubjectTableModel = (DefaultTableModel) addSubjectTable.getModel();
        curriculumTableModel = (DefaultTableModel) curriculumTable.getModel();
        schoolYearService = new SchoolYearServiceImpl();
        subjectService = new SubjectServiceImpl();
        curriculumService = new CurriculumServiceImpl();
        curriculumList = new ArrayList<Curriculum>();
        subjectList = new ArrayList<Subject>();
        subjectFromPrevCurrList = new ArrayList<Subject>();
        currentCurriculum = curriculumService.getCurrentCurriculum();
        selectedCurriculum = currentCurriculum;
        currentSchoolYear = schoolYearService.getCurrentSchoolYear();
        displayUserInfo();
        displayCurriculumList();
        displaySubjectList();

        ComponentFormatter.stripTable(curriculumTable);
        ComponentFormatter.stripTable(subjectTable);
        ComponentFormatter.stripTable(addSubjectTable);
    }

    public void displayUserInfo() throws ExceptionHandler {
        Calendar currentDate = schoolYearService.getServerCurrentDate();
        schoolYearTextField.setText(currentSchoolYear.toString());
        dateTextField.setText(String.format("%1$tB %1$td, %1$tY", currentDate));
        userNameTextField.setText(user.getUserType());
        jLabel3.setText(user.getEmployee().getFullName());
        runner = Executors.newFixedThreadPool(1);
        timeObject = new TimeRunnableObject(lockObject, dateTextField, currentDate);
        runner.execute(timeObject);
        runner.shutdown();
    }

    private void displayCurriculumList() throws ExceptionHandler {
        ComponentFormatter.clearTable(curriculumTableModel);
        curriculumList = curriculumService.getCurriculumList();
        for (Curriculum curriculum : curriculumList) {
            curriculumTableModel.addRow(new Object[]{curriculum.getCurriculumYear(), curriculum.getStatus()});
        }
    }

    private void displaySubjectList() throws ExceptionHandler {
        int selectedYearLevel = yearLevelComboBox.getSelectedIndex() + 1;
        subjectList = subjectService.getSubjectList(selectedCurriculum.getCurriculumID(), selectedYearLevel);
        ComponentFormatter.clearTable(subjectTableModel);
        double totalSubjectUnits = 0;

        for (Subject subject : subjectList) {
            subjectTableModel.addRow(new Object[]{subject.getSubjectCode(), subject.getDescription(), subject.getUnits()});
            totalSubjectUnits += subject.getUnits();
        }
        noOfSubjects.setText(subjectList.size() + "");
        totalUnits.setText(totalSubjectUnits + "");
        curriculumYear.setText(selectedCurriculum.getCurriculumYear() + "");
        curriculumUnits.setText(getCurriculumUnits() + "");
        status.setText(selectedCurriculum.getStatus());
    }

    private void displaySubjectFromPrevCurrList() throws ExceptionHandler {
        int selectedYearLevel = yearLevelOldSubComboBox.getSelectedIndex() + 1;
        subjectFromPrevCurrList = subjectService.getSubjectFromPrevCurrList(selectedCurriculum.getCurriculumID(), selectedYearLevel);
        ComponentFormatter.clearTable(addSubjectTableModel);
        for (Subject subject : subjectFromPrevCurrList) {
            addSubjectTableModel.addRow(new Object[]{subject.getSubjectCode(), subject.getDescription(), subject.getUnits(), getRemarks(subject)});
        }
    }

    private String getRemarks(Subject subject) throws ExceptionHandler {
        if (checkSubject(subject)) {
            return "Added";
        } else {
            return "";
        }
    }

    private boolean checkSubject(Subject subject) throws ExceptionHandler {
        List<Subject> subjectList = subjectService.getSubjectList(selectedCurriculum.getCurriculumID());
        for (Subject subject2 : subjectList) {
            if (subject.getSubjectID() == subject2.getSubjectID()) {
                return true;
            }
        }
        return false;
    }

    private boolean isSafeToDelete(int curriculumID) throws ExceptionHandler {
        return !curriculumService.hasSubjects(curriculumID) && curriculumService.isSafeToDelete(curriculumID);
    }

    private double getCurriculumUnits() throws ExceptionHandler {
        double totalUnits = 0;
        List<Subject> subjectList = subjectService.getSubjectList(selectedCurriculum.getCurriculumID());
        for (Subject subject : subjectList) {
            totalUnits += subject.getUnits();
        }
        return totalUnits;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        subjectOption = new javax.swing.ButtonGroup();
        jToolBar2 = new javax.swing.JToolBar();
        updateButton = new javax.swing.JButton();
        logOutButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        yearLevelComboBox = new javax.swing.JComboBox();
        curriculumYear = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        noOfSubjects = new javax.swing.JLabel();
        addSubjectButton = new javax.swing.JButton();
        deleteSubjectButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        subjectTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        curriculumUnits = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        totalUnits = new javax.swing.JLabel();
        editSubjectButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        newSubjectRadioButton = new javax.swing.JRadioButton();
        descriptionLabel = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        subjectCodeLabel = new javax.swing.JLabel();
        subjectCodeTextField = new javax.swing.JTextField();
        unitsLabel = new javax.swing.JLabel();
        submitButton = new javax.swing.JButton();
        yearLevelOldSubComboBox = new javax.swing.JComboBox();
        yearLevelLabel = new javax.swing.JLabel();
        yearLevelNewSubComboBox = new javax.swing.JComboBox();
        oldSubjectRadioButton = new javax.swing.JRadioButton();
        AddSubject = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        jScrollPane19 = new javax.swing.JScrollPane();
        addSubjectTable = new javax.swing.JTable();
        yearLevelAddSubLabel = new javax.swing.JLabel();
        unitsComboBox = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        deleteCurriculumButton = new javax.swing.JButton();
        addCurriculumButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        curriculumTable = new javax.swing.JTable();
        editCurriculumButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        dateTextField = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        userNameTextField = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        schoolYearTextField = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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

        updateButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/update24.png"))); // NOI18N
        updateButton.setText("UPDATE ACCOUNT");
        updateButton.setFocusable(false);
        updateButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        updateButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        updateButton.setOpaque(false);
        updateButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(updateButton);

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

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 153, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CURRICULUM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jPanel14.setBackground(new java.awt.Color(0, 204, 51));
        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        yearLevelComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevelComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1st year", "2nd year", "3rd year", "4th year" }));
        yearLevelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearLevelComboBoxActionPerformed(evt);
            }
        });
        jPanel14.add(yearLevelComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 100, 20));

        curriculumYear.setFont(new java.awt.Font("Tahoma", 1, 12));
        curriculumYear.setText("----");
        jPanel14.add(curriculumYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel10.setText("Curriculum:");
        jPanel14.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel11.setText("View Subjects by Year Level:");
        jPanel14.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 20));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel14.setText("No. of subjects:");
        jPanel14.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, 30));

        noOfSubjects.setFont(new java.awt.Font("Tahoma", 1, 12));
        noOfSubjects.setText("---");
        jPanel14.add(noOfSubjects, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 40, -1));

        addSubjectButton.setBackground(new java.awt.Color(0, 204, 51));
        addSubjectButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        addSubjectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/add.png"))); // NOI18N
        addSubjectButton.setText("Add Subject");
        addSubjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSubjectButtonActionPerformed(evt);
            }
        });
        jPanel14.add(addSubjectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 130, 30));

        deleteSubjectButton.setBackground(new java.awt.Color(0, 204, 51));
        deleteSubjectButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        deleteSubjectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/delete.png"))); // NOI18N
        deleteSubjectButton.setText("Delete Subject");
        deleteSubjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSubjectButtonActionPerformed(evt);
            }
        });
        jPanel14.add(deleteSubjectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 420, 140, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel4.setText("Status:");
        jPanel14.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, 20));

        status.setFont(new java.awt.Font("Tahoma", 1, 12));
        status.setText("---");
        jPanel14.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 80, 20));

        subjectTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        subjectTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject Code", "Description", "Units"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        subjectTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subjectTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(subjectTable);
        subjectTable.getColumnModel().getColumn(0).setMinWidth(100);
        subjectTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        subjectTable.getColumnModel().getColumn(0).setMaxWidth(100);
        subjectTable.getColumnModel().getColumn(2).setMinWidth(60);
        subjectTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        subjectTable.getColumnModel().getColumn(2).setMaxWidth(60);

        jPanel14.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 390, 290));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel6.setText("Total Units:");
        jPanel14.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        curriculumUnits.setFont(new java.awt.Font("Tahoma", 1, 12));
        curriculumUnits.setText("---");
        jPanel14.add(curriculumUnits, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 80, 20));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel15.setText("Total units:");
        jPanel14.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, -1, -1));

        totalUnits.setFont(new java.awt.Font("Tahoma", 1, 12));
        totalUnits.setText("---");
        jPanel14.add(totalUnits, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, 40, -1));

        editSubjectButton.setBackground(new java.awt.Color(0, 204, 51));
        editSubjectButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        editSubjectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/pencil.png"))); // NOI18N
        editSubjectButton.setText("Edit Subject");
        editSubjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSubjectButtonActionPerformed(evt);
            }
        });
        jPanel14.add(editSubjectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, 130, 30));

        jPanel3.setBackground(new java.awt.Color(0, 204, 51));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setEnabled(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        newSubjectRadioButton.setBackground(new java.awt.Color(0, 204, 51));
        subjectOption.add(newSubjectRadioButton);
        newSubjectRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        newSubjectRadioButton.setText("New subject");
        newSubjectRadioButton.setEnabled(false);
        newSubjectRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newSubjectRadioButtonMouseClicked(evt);
            }
        });
        newSubjectRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newSubjectRadioButtonActionPerformed(evt);
            }
        });
        jPanel3.add(newSubjectRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        descriptionLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        descriptionLabel.setText("Description:");
        descriptionLabel.setEnabled(false);
        jPanel3.add(descriptionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, 20));

        descriptionTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
        descriptionTextField.setEnabled(false);
        jPanel3.add(descriptionTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 167, -1));

        subjectCodeLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        subjectCodeLabel.setText("Subject Code:");
        subjectCodeLabel.setEnabled(false);
        jPanel3.add(subjectCodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, 20));

        subjectCodeTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
        subjectCodeTextField.setEnabled(false);
        jPanel3.add(subjectCodeTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 105, -1));

        unitsLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        unitsLabel.setText("Units:");
        unitsLabel.setEnabled(false);
        jPanel3.add(unitsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, -1, -1));

        submitButton.setBackground(new java.awt.Color(0, 204, 51));
        submitButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        submitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/disk.png"))); // NOI18N
        submitButton.setText("Submit");
        submitButton.setEnabled(false);
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });
        jPanel3.add(submitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 430, -1, -1));

        yearLevelOldSubComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevelOldSubComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1st year", "2nd year", "3rd year", "4th year" }));
        yearLevelOldSubComboBox.setEnabled(false);
        yearLevelOldSubComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                yearLevelOldSubComboBoxItemStateChanged(evt);
            }
        });
        jPanel3.add(yearLevelOldSubComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 80, -1));

        yearLevelLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevelLabel.setText("Year Level:");
        yearLevelLabel.setEnabled(false);
        jPanel3.add(yearLevelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));

        yearLevelNewSubComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevelNewSubComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1st year", "2nd year", "3rd year", "4th year" }));
        yearLevelNewSubComboBox.setEnabled(false);
        jPanel3.add(yearLevelNewSubComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 100, -1));

        oldSubjectRadioButton.setBackground(new java.awt.Color(0, 204, 51));
        subjectOption.add(oldSubjectRadioButton);
        oldSubjectRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        oldSubjectRadioButton.setText("Subject from previous curriculum");
        oldSubjectRadioButton.setEnabled(false);
        oldSubjectRadioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                oldSubjectRadioButtonMouseClicked(evt);
            }
        });
        oldSubjectRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oldSubjectRadioButtonActionPerformed(evt);
            }
        });
        jPanel3.add(oldSubjectRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        AddSubject.setFont(new java.awt.Font("Tahoma", 1, 14));
        AddSubject.setText("Add Subject");
        jPanel3.add(AddSubject, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

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
        jPanel3.add(cancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 430, -1, -1));

        addSubjectTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        addSubjectTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject", "Description", "Units", "Remarks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane19.setViewportView(addSubjectTable);
        addSubjectTable.getColumnModel().getColumn(0).setMinWidth(70);
        addSubjectTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        addSubjectTable.getColumnModel().getColumn(0).setMaxWidth(70);
        addSubjectTable.getColumnModel().getColumn(2).setMinWidth(50);
        addSubjectTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        addSubjectTable.getColumnModel().getColumn(2).setMaxWidth(50);
        addSubjectTable.getColumnModel().getColumn(3).setMinWidth(60);
        addSubjectTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        addSubjectTable.getColumnModel().getColumn(3).setMaxWidth(60);

        jPanel3.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 360, 170));

        yearLevelAddSubLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevelAddSubLabel.setText("Year Level:");
        yearLevelAddSubLabel.setEnabled(false);
        jPanel3.add(yearLevelAddSubLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, 20));

        unitsComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        unitsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2.0", "1.0", "0.75", "0.5", "0.25" }));
        unitsComboBox.setEnabled(false);
        jPanel3.add(unitsComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, -1, -1));

        jPanel4.setBackground(new java.awt.Color(0, 204, 51));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deleteCurriculumButton.setBackground(new java.awt.Color(0, 204, 51));
        deleteCurriculumButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        deleteCurriculumButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/delete.png"))); // NOI18N
        deleteCurriculumButton.setText("Delete");
        deleteCurriculumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCurriculumButtonActionPerformed(evt);
            }
        });
        jPanel4.add(deleteCurriculumButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 420, 90, 30));

        addCurriculumButton.setBackground(new java.awt.Color(0, 204, 51));
        addCurriculumButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        addCurriculumButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/add.png"))); // NOI18N
        addCurriculumButton.setText("Add");
        addCurriculumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCurriculumButtonActionPerformed(evt);
            }
        });
        jPanel4.add(addCurriculumButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 80, 30));

        curriculumTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        curriculumTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Curriculum Year", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        curriculumTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                curriculumTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(curriculumTable);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 250, 390));

        editCurriculumButton.setBackground(new java.awt.Color(0, 204, 51));
        editCurriculumButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        editCurriculumButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/pencil.png"))); // NOI18N
        editCurriculumButton.setText("Edit");
        editCurriculumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCurriculumButtonActionPerformed(evt);
            }
        });
        jPanel4.add(editCurriculumButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 80, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, 520));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 1250, 540));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/date25.png"))); // NOI18N
        jLabel1.setText("Today is");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 110, -1, 30));

        dateTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        dateTextField.setText("---");
        getContentPane().add(dateTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 110, 230, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/user25.png"))); // NOI18N
        jLabel5.setText("User Type:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 80, -1, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 60, -1, 30));

        userNameTextField.setFont(new java.awt.Font("Tahoma", 1, 11));
        userNameTextField.setText("----");
        getContentPane().add(userNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 80, 140, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/user25.png"))); // NOI18N
        jLabel7.setText("Name:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 60, -1, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/trimester-label.png"))); // NOI18N
        jLabel8.setText("School Year:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 40, -1, 20));

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

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        updateAccount();
    }//GEN-LAST:event_updateButtonActionPerformed

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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to exit?", "Exit Window", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }//GEN-LAST:event_formWindowClosing

    private void addCurriculumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCurriculumButtonActionPerformed
        // TODO add your handling code here:
        try {
            CurriculumDialog addCurriculum = new CurriculumDialog(parentFrame, true, null);
            addCurriculum.setVisible(true);
            if (addCurriculum.getCurriculum() != null) {
                displayCurriculumList();
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
        }

}//GEN-LAST:event_addCurriculumButtonActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // TODO add your handling code here:
        newSubjectRadioButton.setEnabled(true);
        oldSubjectRadioButton.setEnabled(true);
        submitButton.setEnabled(true);
        cancelButton.setEnabled(true);
        yearLevelOldSubComboBox.setEnabled(true);
}//GEN-LAST:event_jButton43ActionPerformed

    private void newSubjectRadioButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newSubjectRadioButtonMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_newSubjectRadioButtonMouseClicked

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        // TODO add your handling code here:
        try {
            if (newSubjectRadioButton.isSelected()) {
                if (subjectCodeTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "Subject code is empty", "Add Subject", JOptionPane.WARNING_MESSAGE);
                } else if (descriptionTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "Description is empty", "Add Subject", JOptionPane.WARNING_MESSAGE);
                } else {
                    String subjectCode = subjectCodeTextField.getText();
                    String description = descriptionTextField.getText();
                    double units = Double.parseDouble(unitsComboBox.getSelectedItem().toString());
                    int yearLevel = yearLevelNewSubComboBox.getSelectedIndex() + 1;
                    if (subjectService.checkSubject(subjectCode)) {
                        Subject subject = new Subject(subjectCode, description, units, yearLevel);
                        subjectService.addSubject(subject);
                        Subject addSubject = subjectService.getSubject(subjectCode);
                        curriculumService.addSubject(addSubject.getSubjectID(), selectedCurriculum.getCurriculumID());
                        //JOptionPane.showMessageDialog(parentFrame, "Subject has successfully added", "Add Subject", JOptionPane.INFORMATION_MESSAGE);
                        displaySubjectList();
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Subject code already exist", "Add Subject", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                int index = addSubjectTable.getSelectedRow();
                if (index >= 0) {
                    Subject subject = subjectFromPrevCurrList.get(index);
                    if (!checkSubject(subject)) {
                        curriculumService.addSubject(subject.getSubjectID(), selectedCurriculum.getCurriculumID());
                        //JOptionPane.showMessageDialog(parentFrame, "Subject has successfully added", "Add Subject", JOptionPane.INFORMATION_MESSAGE);
                        displaySubjectList();
                        displaySubjectFromPrevCurrList();
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Subject already exist in the curriculum", "Add Subject", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Please choose a subject", "Add Subject", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_submitButtonActionPerformed

    private void yearLevelOldSubComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_yearLevelOldSubComboBoxItemStateChanged
        try {
            displaySubjectFromPrevCurrList();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_yearLevelOldSubComboBoxItemStateChanged

    private void oldSubjectRadioButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_oldSubjectRadioButtonMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_oldSubjectRadioButtonMouseClicked

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        disableAddSubjectPanel();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void deleteCurriculumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCurriculumButtonActionPerformed
        // TODO add your handling code here:
        int index = curriculumTable.getSelectedRow();
        if (index >= 0) {
            int option = JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to delete this curriculum?",
                    "Delete Curriculum", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    selectedCurriculum = curriculumList.get(index);
                    if (isSafeToDelete(selectedCurriculum.getCurriculumID())) {
                        curriculumService.deleteCurriculum(selectedCurriculum.getCurriculumID());
                        displayCurriculumList();
                        clearSubjectPanel();
                        disableAddSubjectPanel();
                        JOptionPane.showMessageDialog(parentFrame, "Curriculum is successfully deleted", "Delete Curriculum", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "This curriculum cannot be deleted", "Delete Curriculum", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (ExceptionHandler ex) {
                    Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "No curriculum selected", "Delete Curriculum", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_deleteCurriculumButtonActionPerformed

    private void addSubjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSubjectButtonActionPerformed
        // TODO add your handling code here:
        newSubjectRadioButton.setEnabled(true);
        oldSubjectRadioButton.setEnabled(true);
        submitButton.setEnabled(true);
        cancelButton.setEnabled(true);
    }//GEN-LAST:event_addSubjectButtonActionPerformed

    private void deleteSubjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSubjectButtonActionPerformed
        // TODO add your handling code here:
        int index = subjectTable.getSelectedRow();
        if (index >= 0) {
            int option = JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to delete this subject?",
                    "Delete Subject", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                Subject subject = subjectList.get(index);
                try {
                    curriculumService.deleteSubject(subject.getSubjectID(), selectedCurriculum.getCurriculumID());
                    if (subjectService.isSafeToDelete(subject.getSubjectID(), selectedCurriculum.getCurriculumID())) {
                        subjectService.deleteSubject(subject.getSubjectID());
                    }
                    displaySubjectList();
                    disableAddSubjectPanel();
                    JOptionPane.showMessageDialog(parentFrame, "Subject is successfully deleted", "Delete Subject", JOptionPane.INFORMATION_MESSAGE);
                } catch (ExceptionHandler ex) {
                    Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Please select a subject", "Delete subject", JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_deleteSubjectButtonActionPerformed

    private void yearLevelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearLevelComboBoxActionPerformed
        // TODO add your handling code here:
        try {
            displaySubjectList();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_yearLevelComboBoxActionPerformed

    private void oldSubjectRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oldSubjectRadioButtonActionPerformed
        // TODO add your handling code here:
        if (oldSubjectRadioButton.isSelected()) {
            updatAddFromOldSubjectComponents(true);
            updateAddNewSubjectComponents(false);
            try {
                displaySubjectFromPrevCurrList();
            } catch (ExceptionHandler ex) {
                Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_oldSubjectRadioButtonActionPerformed

    private void newSubjectRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newSubjectRadioButtonActionPerformed
        // TODO add your handling code here:
        if (newSubjectRadioButton.isSelected()) {
            updatAddFromOldSubjectComponents(false);
            updateAddNewSubjectComponents(true);
            yearLevelNewSubComboBox.setSelectedIndex(yearLevelComboBox.getSelectedIndex());
        }
    }//GEN-LAST:event_newSubjectRadioButtonActionPerformed

    private void curriculumTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_curriculumTableMouseClicked
        // TODO add your handling code here:
        try {
            int index = curriculumTable.getSelectedRow();
            if (index >= 0) {
                selectedCurriculum = curriculumList.get(index);

                if (selectedCurriculum.getStatus().equalsIgnoreCase("Active")) {
                    addSubjectButton.setEnabled(true);
                    editSubjectButton.setEnabled(true);
                    deleteSubjectButton.setEnabled(true);
                } else {
                    addSubjectButton.setEnabled(false);
                    editSubjectButton.setEnabled(false);
                    deleteSubjectButton.setEnabled(false);
                    disableAddSubjectPanel();
                }
                displaySubjectList();

            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_curriculumTableMouseClicked

    private void editSubjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSubjectButtonActionPerformed
        // TODO add your handling code here:
        int index = subjectTable.getSelectedRow();
        if (index >= 0) {
            try {
                Subject subject = subjectList.get(index);

                EditSubjectDialog editSubject = new EditSubjectDialog(this, true, subject);
                editSubject.setVisible(true);
                if (editSubject.success) {
                    displaySubjectList();
                }
            } catch (ExceptionHandler ex) {
                Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Please select a subject", "Edit subject", JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_editSubjectButtonActionPerformed

    private void subjectTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subjectTableMouseClicked
        // TODO add your handling code here:
        int index = subjectTable.getSelectedRow();
        if (index >= 0) {
            try {
                Subject subject = subjectList.get(index);
                if (curriculumService.isSubjectUsed(subject.getSubjectID(), selectedCurriculum.getCurriculumID())) {
                    editSubjectButton.setEnabled(true);
                    deleteSubjectButton.setEnabled(true);
                } else {
                    editSubjectButton.setEnabled(false);
                    deleteSubjectButton.setEnabled(false);
                }
            } catch (ExceptionHandler ex) {
                Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_subjectTableMouseClicked

    private void editCurriculumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCurriculumButtonActionPerformed
        // TODO add your handling code here:
        try {
            int index = curriculumTable.getSelectedRow();
            if (index >= 0) {
                selectedCurriculum = curriculumList.get(index);
                CurriculumDialog editCurriculum = new CurriculumDialog(parentFrame, true, selectedCurriculum);
                editCurriculum.setVisible(true);
                if (editCurriculum.getCurriculum() != null) {
                    displayCurriculumList();
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "No curriculum selected", "Edit Curriculum", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AcademicConsultant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editCurriculumButtonActionPerformed

    private void clearSubjectPanel() {
        curriculumYear.setText("---");
        status.setText("---");
        yearLevelComboBox.setSelectedIndex(0);
        curriculumUnits.setText("---");
        ComponentFormatter.clearTable(subjectTableModel);
        noOfSubjects.setText("---");
        totalUnits.setText("---");
        addSubjectButton.setEnabled(false);
        editSubjectButton.setEnabled(false);
        deleteSubjectButton.setEnabled(false);
    }

    private void disableAddSubjectPanel() {
        ComponentFormatter.clearTable(addSubjectTableModel);
        updatAddFromOldSubjectComponents(false);
        updateAddNewSubjectComponents(false);
        submitButton.setEnabled(false);
        cancelButton.setEnabled(false);
        newSubjectRadioButton.setEnabled(false);
        oldSubjectRadioButton.setEnabled(false);
        subjectOption.clearSelection();
    }

    private void updateAddNewSubjectComponents(boolean flag) {
        subjectCodeLabel.setEnabled(flag);
        descriptionLabel.setEnabled(flag);
        unitsLabel.setEnabled(flag);
        yearLevelLabel.setEnabled(flag);
        subjectCodeTextField.setEnabled(flag);
        descriptionTextField.setEnabled(flag);
        unitsComboBox.setEnabled(flag);
        yearLevelNewSubComboBox.setEnabled(flag);
        unitsComboBox.setSelectedIndex(0);
        subjectCodeTextField.setText("");
        descriptionTextField.setText("");
        yearLevelNewSubComboBox.setSelectedIndex(0);
    }

    private void updatAddFromOldSubjectComponents(boolean flag) {
        yearLevelAddSubLabel.setEnabled(flag);
        yearLevelOldSubComboBox.setEnabled(flag);
        addSubjectTable.setEnabled(flag);
        yearLevelOldSubComboBox.setSelectedIndex(0);
        ComponentFormatter.clearTable(addSubjectTableModel);
    }

    private void updateAccount() {
        UpdateAccountDialog updateAccount = new UpdateAccountDialog(this, true, user);
        updateAccount.setVisible(true);
        user = updateAccount.getUserAccount();
        updateAccount.dispose();
        userNameTextField.setText(user.getUserName());
    }
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AddSubject;
    private javax.swing.JButton addCurriculumButton;
    private javax.swing.JButton addSubjectButton;
    private javax.swing.JTable addSubjectTable;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTable curriculumTable;
    private javax.swing.JLabel curriculumUnits;
    private javax.swing.JLabel curriculumYear;
    private javax.swing.JLabel dateTextField;
    private javax.swing.JButton deleteCurriculumButton;
    private javax.swing.JButton deleteSubjectButton;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JButton editCurriculumButton;
    private javax.swing.JButton editSubjectButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton logOutButton;
    private javax.swing.JRadioButton newSubjectRadioButton;
    private javax.swing.JLabel noOfSubjects;
    private javax.swing.JRadioButton oldSubjectRadioButton;
    private javax.swing.JLabel schoolYearTextField;
    private javax.swing.JLabel status;
    private javax.swing.JLabel subjectCodeLabel;
    private javax.swing.JTextField subjectCodeTextField;
    private javax.swing.ButtonGroup subjectOption;
    private javax.swing.JTable subjectTable;
    private javax.swing.JButton submitButton;
    private javax.swing.JLabel totalUnits;
    private javax.swing.JComboBox unitsComboBox;
    private javax.swing.JLabel unitsLabel;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel userNameTextField;
    private javax.swing.JLabel yearLevelAddSubLabel;
    private javax.swing.JComboBox yearLevelComboBox;
    private javax.swing.JLabel yearLevelLabel;
    private javax.swing.JComboBox yearLevelNewSubComboBox;
    private javax.swing.JComboBox yearLevelOldSubComboBox;
    // End of variables declaration//GEN-END:variables
}

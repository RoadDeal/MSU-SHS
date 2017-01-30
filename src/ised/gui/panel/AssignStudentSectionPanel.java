/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AssignStudentSectionPanel.java
 *
 * Created on 10 25, 11, 2:31:35 PM
 */
package ised.gui.panel;

import ised.gui.Principal;
import ised.model.Enrollment;
import ised.model.SchoolYear;
import ised.model.Student;
import ised.model.Class;
import ised.model.Subject;
import ised.service.implementation.ClassServiceImpl;
import ised.service.implementation.EnrollmentServiceImpl;
import ised.service.implementation.StudentSubjectServiceImpl;
import ised.service.implementation.SubjectServiceImpl;
import ised.service.interfaces.ClassService;
import ised.service.interfaces.EnrollmentService;
import ised.service.interfaces.StudentSubjectService;
import ised.service.interfaces.SubjectService;
import ised.tools.ComponentFormatter;
import ised.tools.Theme;
import ised.tools.ExceptionHandler;
import java.util.ArrayList;
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
public class AssignStudentSectionPanel extends javax.swing.JPanel {

    Principal parentFrame;
    private List<Student> studentList;
    private List<Student> studentSectionList;
    private List<Class> sectionList;
    private SchoolYear currentSchoolYear;
    private DefaultTableModel studentTableModel;
    private DefaultTableModel studentSectionTableModel;
    private int selectedYearLevel;
    private Class selectedSection;
    private EnrollmentService enrollmentService;
    private SubjectService subjectService;
    private StudentSubjectService studentSubjectService;
    private ClassService classService;

    /** Creates new form AssignStudentSectionPanel */
    public AssignStudentSectionPanel(Principal parentFrame, SchoolYear schoolYear) throws ExceptionHandler {
        Theme.setDefaultLookAndFeel();
        initComponents();
        this.parentFrame = parentFrame;
        studentTableModel = (DefaultTableModel) studentTable.getModel();
        studentSectionTableModel = (DefaultTableModel) studentSectionTable.getModel();
        studentList = new ArrayList<Student>();
        studentSectionList = new ArrayList<Student>();
        sectionList = new ArrayList<Class>();
        classService = new ClassServiceImpl();
        enrollmentService = new EnrollmentServiceImpl();
        subjectService = new SubjectServiceImpl();
        studentSubjectService = new StudentSubjectServiceImpl();
        currentSchoolYear = schoolYear;
        displayStudentList();

        ComponentFormatter.stripTable(studentTable);
        ComponentFormatter.stripTable(studentSectionTable);
    }

    public void displayStudentList() throws ExceptionHandler {
        int maleCount = 0, femaleCount = 0;
        ComponentFormatter.clearTable(studentTableModel);

        if (selectedYearLevel == 0) {
            updateComponents(false);
            return;
        } else {
            studentList = enrollmentService.getStudentsNotAssignedToSection(currentSchoolYear.getSchoolYearID(), selectedYearLevel);
            updateComponents(true);
        }

        for (Student student : studentList) {
            studentTableModel.addRow(new Object[]{student.getFullName(), student.getGender()});
            if (student.getGender().equalsIgnoreCase("Male")) {
                maleCount++;
            } else if (student.getGender().equalsIgnoreCase("Female")) {
                femaleCount++;
            }
        }

        totalStudentsYearLevel.setText(Integer.toString(studentList.size()));
        maleYearLevel.setText(Integer.toString(maleCount));
        femaleYearLevel.setText(Integer.toString(femaleCount));

    }

    public void displayStudentSectionList() throws ExceptionHandler {
        selectedSection = (Class) sectionComboBox.getSelectedItem();
        ComponentFormatter.clearTable(studentSectionTableModel);
        if (selectedSection == null) {
            return;
        } else {
            studentSectionList = enrollmentService.getStudentsListBySection(currentSchoolYear.getSchoolYearID(), selectedSection.getClassID());
            int maleCount = 0, femaleCount = 0;
            for (Student student : studentSectionList) {
                studentSectionTableModel.addRow(new Object[]{student.getFullName(), student.getGender()});
                if (student.getGender().equalsIgnoreCase("Male")) {
                    maleCount++;
                } else if (student.getGender().equalsIgnoreCase("Female")) {
                    femaleCount++;
                }
            }
            totalStudentsSection.setText(Integer.toString(studentSectionList.size()));
            maleSection.setText(Integer.toString(maleCount));
            femaleSection.setText(Integer.toString(femaleCount));
        }
    }

    public void setSectionComboBox() throws ExceptionHandler {
        DefaultComboBoxModel model = (DefaultComboBoxModel) sectionComboBox.getModel();
        model.removeAllElements();
        if (selectedYearLevel == 0) {
            return;
        }
        sectionList = classService.getClassList(currentSchoolYear.getSchoolYearID(), selectedYearLevel);
        for (Class section : sectionList) {
            model.addElement(section);
        }
    }

    public void displayYearLevel() {
        if (selectedYearLevel == 0) {
            yearLevel.setText("---");
        } else if (selectedYearLevel == 1) {
            yearLevel.setText("1st year");
        } else if (selectedYearLevel == 2) {
            yearLevel.setText("2nd year");
        } else if (selectedYearLevel == 3) {
            yearLevel.setText("3rd year");
        } else if (selectedYearLevel == 4) {
            yearLevel.setText("4th year");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        studentSectionTable = new javax.swing.JTable();
        leftArrowButton = new javax.swing.JButton();
        rightArrowButton = new javax.swing.JButton();
        jScrollPane17 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        totalSectionStudentsLabel = new javax.swing.JLabel();
        totalStudentsSection = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        maleSection = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        totalStudentsYearLevel = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        femaleSection = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        yearLevelComboBox = new javax.swing.JComboBox();
        jLabel64 = new javax.swing.JLabel();
        maleYearLevel = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        femaleYearLevel = new javax.swing.JLabel();
        yearLevelLabel = new javax.swing.JLabel();
        yearLevel = new javax.swing.JLabel();
        sectionLabel = new javax.swing.JLabel();
        sectionComboBox = new javax.swing.JComboBox();

        jPanel6.setBackground(new java.awt.Color(0, 153, 51));
        jPanel6.setFont(new java.awt.Font("Tahoma", 1, 11));

        jPanel15.setBackground(new java.awt.Color(0, 204, 51));
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        studentSectionTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        studentSectionTable.setModel(new javax.swing.table.DefaultTableModel(
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
        studentSectionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentSectionTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                studentSectionTableMouseEntered(evt);
            }
        });
        jScrollPane16.setViewportView(studentSectionTable);
        studentSectionTable.getColumnModel().getColumn(1).setMinWidth(100);
        studentSectionTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        studentSectionTable.getColumnModel().getColumn(1).setMaxWidth(100);

        jPanel15.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 320, 360));

        leftArrowButton.setBackground(new java.awt.Color(0, 204, 51));
        leftArrowButton.setFont(new java.awt.Font("Tahoma", 0, 14));
        leftArrowButton.setText("<");
        leftArrowButton.setEnabled(false);
        leftArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftArrowButtonActionPerformed(evt);
            }
        });
        jPanel15.add(leftArrowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, 50, 30));

        rightArrowButton.setBackground(new java.awt.Color(0, 204, 51));
        rightArrowButton.setFont(new java.awt.Font("Tahoma", 0, 14));
        rightArrowButton.setText(">");
        rightArrowButton.setEnabled(false);
        rightArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightArrowButtonActionPerformed(evt);
            }
        });
        jPanel15.add(rightArrowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 50, 30));

        studentTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        studentTable.setModel(new javax.swing.table.DefaultTableModel(
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
        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                studentTableMouseEntered(evt);
            }
        });
        jScrollPane17.setViewportView(studentTable);
        studentTable.getColumnModel().getColumn(1).setMinWidth(100);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(1).setMaxWidth(100);

        jPanel15.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 60, 310, 360));

        totalSectionStudentsLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        totalSectionStudentsLabel.setText("Total no. of Students:");
        jPanel15.add(totalSectionStudentsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 430, -1, -1));

        totalStudentsSection.setFont(new java.awt.Font("Tahoma", 0, 12));
        totalStudentsSection.setText("---");
        jPanel15.add(totalStudentsSection, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 60, 40));

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel61.setText("Male:");
        jPanel15.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 450, -1, -1));

        maleSection.setFont(new java.awt.Font("Tahoma", 0, 12));
        maleSection.setText("---");
        jPanel15.add(maleSection, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 450, 30, -1));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel63.setText("Total no. of Students:");
        jPanel15.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, 20));

        totalStudentsYearLevel.setFont(new java.awt.Font("Tahoma", 0, 12));
        totalStudentsYearLevel.setText("---");
        jPanel15.add(totalStudentsYearLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 50, 20));

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel65.setText("Female:");
        jPanel15.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, -1, -1));

        femaleSection.setFont(new java.awt.Font("Tahoma", 0, 12));
        femaleSection.setText("---");
        jPanel15.add(femaleSection, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 450, 70, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel6.setText("Select year level:");
        jPanel15.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 20));

        yearLevelComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevelComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1st year", "2nd year", "3rd year", "4th year" }));
        yearLevelComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        yearLevelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearLevelComboBoxActionPerformed(evt);
            }
        });
        jPanel15.add(yearLevelComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 100, -1));

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel64.setText("Male:");
        jPanel15.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 30, -1));

        maleYearLevel.setFont(new java.awt.Font("Tahoma", 0, 12));
        maleYearLevel.setText("---");
        jPanel15.add(maleYearLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 450, 40, -1));

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel69.setText("Female:");
        jPanel15.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, -1, -1));

        femaleYearLevel.setFont(new java.awt.Font("Tahoma", 0, 12));
        femaleYearLevel.setText("---");
        jPanel15.add(femaleYearLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 60, -1));

        yearLevelLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevelLabel.setText("Year Level:");
        yearLevelLabel.setEnabled(false);
        jPanel15.add(yearLevelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, -1, 21));

        yearLevel.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevel.setText("---");
        yearLevel.setEnabled(false);
        jPanel15.add(yearLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 63, 20));

        sectionLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        sectionLabel.setText("Section: ");
        sectionLabel.setEnabled(false);
        jPanel15.add(sectionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, -1, 20));

        sectionComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        sectionComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        sectionComboBox.setEnabled(false);
        sectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionComboBoxActionPerformed(evt);
            }
        });
        jPanel15.add(sectionComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, 91, -1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(440, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void yearLevelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearLevelComboBoxActionPerformed
        // TODO add your handling code here:
        try {
            selectedYearLevel = yearLevelComboBox.getSelectedIndex();
            displayStudentList();
            displayYearLevel();
            setSectionComboBox();
            displayStudentSectionList();
        } catch (ExceptionHandler ex) {
        }
}//GEN-LAST:event_yearLevelComboBoxActionPerformed

    private void updateComponents(boolean flag) {
        yearLevelLabel.setEnabled(flag);
        yearLevel.setEnabled(flag);
        sectionLabel.setEnabled(flag);
        sectionComboBox.setEnabled(flag);
        if (flag == false) {
            totalStudentsYearLevel.setText("---");
            maleYearLevel.setText("---");
            femaleYearLevel.setText("---");
            totalStudentsSection.setText("---");
            maleSection.setText("---");
            femaleSection.setText("---");
        }
    }

    private void studentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMouseClicked
        // TODO add your handling code here:
        try {
            if (studentTable.getSelectedRow() >= 0) {
                rightArrowButton.setEnabled(true);
                leftArrowButton.setEnabled(false);
                studentSectionTable.removeRowSelectionInterval(0, studentSectionTable.getRowCount());
            }
        } catch (IllegalArgumentException ex) {
        }
    }//GEN-LAST:event_studentTableMouseClicked

    private void rightArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightArrowButtonActionPerformed
        // TODO add your handling code here:
        try {
            if (studentTable.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog(parentFrame, "Please select a student", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int index[] = studentTable.getSelectedRows();
            selectedSection = (Class) sectionComboBox.getSelectedItem();
            for (int i = 0; i < index.length; i++) {
                Student student = studentList.get(index[i]);
                Enrollment enrollment = enrollmentService.getEnrollment(student.getStudentID(), currentSchoolYear.getSchoolYearID());
                enrollmentService.assignToSection(enrollment.getEnrollmentID(), selectedSection.getClassID());
                enrollSubjects(enrollment.getEnrollmentID(), selectedSection.getSection().getCurriculum().getCurriculumID(), selectedSection.getYearLevel());
            }
            displayStudentSectionList();
            displayStudentList();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AssignStudentSectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rightArrowButtonActionPerformed

    private void enrollSubjects(int enrollmentID, int curriculumID, int yearLevel) throws ExceptionHandler{
        List<Subject> subjectList = subjectService.getSubjectList(curriculumID, yearLevel);
        for (Subject subject : subjectList){
            studentSubjectService.addStudentSubject(enrollmentID, subject.getSubjectID());
        }
    }

    private void sectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionComboBoxActionPerformed
        try {
            displayStudentSectionList();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AssignStudentSectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sectionComboBoxActionPerformed

    private void studentSectionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentSectionTableMouseClicked
        // TODO add your handling code here:
        try {
            if (studentSectionTable.getSelectedRow() >= 0) {
                rightArrowButton.setEnabled(false);
                leftArrowButton.setEnabled(true);
                studentTable.removeRowSelectionInterval(0, studentTable.getRowCount() - 1);
            }
        } catch (IllegalArgumentException ex) {
        }
    }//GEN-LAST:event_studentSectionTableMouseClicked

    private void leftArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftArrowButtonActionPerformed
        // TODO add your handling code here:
        try {
            if (studentSectionTable.getSelectedRows().length == 0) {
                JOptionPane.showMessageDialog(parentFrame, "Please select a student", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int answer = javax.swing.JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to remove the selected student(s) in the selected section"
                    + "?", "Remove Student From Section", javax.swing.JOptionPane.YES_NO_OPTION);
            if (answer == javax.swing.JOptionPane.YES_OPTION) {
                int index[] = studentSectionTable.getSelectedRows();
                selectedSection = (Class) sectionComboBox.getSelectedItem();
                for (int i = 0; i < index.length; i++) {
                    Student student = studentSectionList.get(index[i]);
                    Enrollment enrollment = enrollmentService.getEnrollment(student.getStudentID(), currentSchoolYear.getSchoolYearID());
                    enrollmentService.removeAssign(enrollment.getEnrollmentID());
                    studentSubjectService.deleteStudentSubjects(enrollment.getEnrollmentID());
                }
                displayStudentSectionList();
                displayStudentList();
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AssignStudentSectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_leftArrowButtonActionPerformed

    private void studentSectionTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentSectionTableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_studentSectionTableMouseEntered

    private void studentTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_studentTableMouseEntered

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel femaleSection;
    private javax.swing.JLabel femaleYearLevel;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JButton leftArrowButton;
    private javax.swing.JLabel maleSection;
    private javax.swing.JLabel maleYearLevel;
    private javax.swing.JButton rightArrowButton;
    private javax.swing.JComboBox sectionComboBox;
    private javax.swing.JLabel sectionLabel;
    private javax.swing.JTable studentSectionTable;
    private javax.swing.JTable studentTable;
    private javax.swing.JLabel totalSectionStudentsLabel;
    private javax.swing.JLabel totalStudentsSection;
    private javax.swing.JLabel totalStudentsYearLevel;
    private javax.swing.JLabel yearLevel;
    private javax.swing.JComboBox yearLevelComboBox;
    private javax.swing.JLabel yearLevelLabel;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CreateUserAccountDialog.java
 *
 * Created on Sep 28, 2011, 10:58:58 PM
 */
package ised.gui.dialog;

import ised.model.Employee;
import ised.model.Enrollment;
import ised.model.SchoolYear;
import ised.model.Student;
import ised.model.StudentSubject;
import ised.model.SubjectGrade;
import ised.service.implementation.EnrollmentServiceImpl;
import ised.service.implementation.StudentServiceImpl;
import ised.service.implementation.StudentSubjectServiceImpl;
import ised.service.interfaces.EnrollmentService;
import ised.service.interfaces.StudentService;
import ised.service.interfaces.StudentSubjectService;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import ised.tools.Printer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rodel
 */
public class ViewStudentGradeDialog extends javax.swing.JDialog {

    private Employee employee;
    private Student selectedStudent;
    private javax.swing.JFrame parentFrame;
    private SchoolYear selectedSchoolYear;
    private StudentService studentService;
    private EnrollmentService enrollmentService;
    private StudentSubjectService studentSubjectService;
    private List<SchoolYear> schoolYearList;
    private List<StudentSubject> subjectList;
    private List<SubjectGrade> gradeList;
    private Enrollment enrollment;
    private DefaultTableModel gradeTableModel;
    private Printer printer;

    /** Creates new form CreateUserAccountDialog */
    public ViewStudentGradeDialog(javax.swing.JFrame parentFrame, boolean modal, Student student, SchoolYear currentSchoolYear) throws ExceptionHandler {
        super(parentFrame, modal);
        initComponents();
        this.parentFrame = parentFrame;
        selectedStudent = student;
        selectedSchoolYear = currentSchoolYear;
        schoolYearList = new ArrayList<SchoolYear>();
        gradeList = new ArrayList<SubjectGrade>();
        subjectList = new ArrayList<StudentSubject>();
        this.setLocationRelativeTo(parentFrame);
        gradeTableModel = (DefaultTableModel) gradeTable.getModel();
        studentService = new StudentServiceImpl();
        enrollmentService = new EnrollmentServiceImpl();
        studentSubjectService = new StudentSubjectServiceImpl();
        enrollment = enrollmentService.getEnrollment(student.getStudentID(), currentSchoolYear.getSchoolYearID());
        displayInfo();
        //setSchoolYearComboBox();
        displaySchoolYearInfo();
        displayGrades();

        //ComponentFormatter.colorGradeTable(gradeTable);
    }

    private void displayInfo() {
        idNumber.setText(selectedStudent.getStudentID() + "");
        name.setText(selectedStudent.getFullName() + "");
    }

    private void displaySchoolYearInfo() throws ExceptionHandler {
        schoolYear.setText(selectedSchoolYear.toString());
        yearLevelSection.setText(getYearLevel(enrollment.getAdmission().getYearLevelAdmitted()) + " " + enrollment.getSection().toString());
    }

    public String getYearLevel(int yearLevel) {
        if (yearLevel == 1) {
            return "I";
        } else if (yearLevel == 2) {
            return "II";
        } else if (yearLevel == 3) {
            return "III";
        } else if (yearLevel == 4) {
            return "IV";
        }
        return "";
    }

//    private void setSchoolYearComboBox() throws ExceptionHandler {
//        DefaultComboBoxModel schoolYearComboBoxModel = (DefaultComboBoxModel) schoolYearComboBox.getModel();
//        schoolYearList = studentService.getSchoolYearsAttended(selectedStudent.getStudentID());
//        schoolYearComboBoxModel.removeAllElements();
//        for (SchoolYear schoolYear : schoolYearList) {
//            schoolYearComboBoxModel.addElement(schoolYear);
//        }
//        schoolYearComboBox.setSelectedItem(selectedSchoolYear);
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        printablePanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        idNumber = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        yearLevelSection = new javax.swing.JLabel();
        schoolYear = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gradeTable = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        average = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel4.setBackground(new java.awt.Color(0, 153, 51));

        cancelButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/cancel.png"))); // NOI18N
        cancelButton.setText("Close");
        cancelButton.setOpaque(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 204, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        printablePanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel9.setText("GRADE REPORT");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("ID Number:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Name:");

        idNumber.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        idNumber.setText("---");

        name.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        name.setText("---");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("School Year:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Yr. Level & Section:");

        yearLevelSection.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        yearLevelSection.setText("---");

        schoolYear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        schoolYear.setText("---");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(idNumber))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(name)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(schoolYear))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearLevelSection)))
                .addGap(85, 85, 85))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(schoolYear))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(yearLevelSection)
                            .addComponent(jLabel5)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(idNumber))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(name))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        gradeTable.setFont(new java.awt.Font("Tahoma", 1, 11));
        gradeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SUBJECT", "UNIT", "1ST", "2ND", "3RD", "4TH", "FINAL ", "REMARK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        gradeTable.setGridColor(new java.awt.Color(255, 255, 255));
        gradeTable.setOpaque(false);
        gradeTable.setRowSelectionAllowed(false);
        gradeTable.setShowVerticalLines(false);
        gradeTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(gradeTable);
        gradeTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        gradeTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        gradeTable.getColumnModel().getColumn(2).setResizable(false);
        gradeTable.getColumnModel().getColumn(3).setResizable(false);
        gradeTable.getColumnModel().getColumn(4).setResizable(false);
        gradeTable.getColumnModel().getColumn(5).setResizable(false);
        gradeTable.getColumnModel().getColumn(6).setResizable(false);
        gradeTable.getColumnModel().getColumn(7).setResizable(false);
        gradeTable.getColumnModel().getColumn(7).setPreferredWidth(100);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel10.setText("Average:");

        average.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        average.setText("---");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/ised_header.jpg"))); // NOI18N

        javax.swing.GroupLayout printablePanelLayout = new javax.swing.GroupLayout(printablePanel);
        printablePanel.setLayout(printablePanelLayout);
        printablePanelLayout.setHorizontalGroup(
            printablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printablePanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addGap(100, 100, 100))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, printablePanelLayout.createSequentialGroup()
                .addContainerGap(197, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172))
            .addGroup(printablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(printablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(printablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(average, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(395, Short.MAX_VALUE))
        );
        printablePanelLayout.setVerticalGroup(
            printablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(printablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(average))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(printablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(printablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/printer.png"))); // NOI18N
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            this.printer = new Printer(printablePanel);
            this.printer.setDocumentType("Grade Card");
            this.printer.actionPerformed(evt);
        } catch (Exception e) {
            ExceptionHandler exception = new ExceptionHandler(e);
        } finally {
            dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void displayGrades() throws ExceptionHandler {
        ComponentFormatter.clearTable(gradeTableModel);
        subjectList = studentSubjectService.getStudentSubjectList(enrollment.getEnrollmentID());
        if (subjectList != null) {
            for (StudentSubject subject : subjectList) {
                gradeList = studentSubjectService.getSubjectGradeList(subject.getStudentSubjectID());
                gradeTableModel.addRow(new Object[]{subject.getSubject().getSubjectCode(), subject.getSubject().getUnits(), getGrade(gradeList.get(0).getGradingGrade()),
                            getGrade(gradeList.get(1).getGradingGrade()), getGrade(gradeList.get(2).getGradingGrade()), getGrade(gradeList.get(3).getGradingGrade()),
                            subject.getFinalGrade(), getRemarks(subject.getFinalGrade())});
            }
        }
        average.setText(enrollment.getAverage() + "");
    }

    private String getGrade(double grade) {
        if (grade == 0) {
            return "";
        } else {
            return grade + "";
        }
    }

    private String getRemarks(double grade) {
        if (grade == 0) {
            return "";
        } else if (grade > 3) {
            return "Failed";
        } else {
            return "Passed";
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel average;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTable gradeTable;
    private javax.swing.JLabel idNumber;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel name;
    private javax.swing.JPanel printablePanel;
    private javax.swing.JLabel schoolYear;
    private javax.swing.JLabel yearLevelSection;
    // End of variables declaration//GEN-END:variables
}

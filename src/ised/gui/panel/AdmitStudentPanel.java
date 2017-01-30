/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AdmitStudentPanel.java
 *
 * Created on 10 15, 11, 1:54:44 PM
 */
package ised.gui.panel;

import ised.gui.Registrar;
import ised.model.Alumni;
import ised.model.Enrollment;
import ised.model.SchoolYear;
import ised.model.Student;
import ised.service.implementation.AdmissionServiceImpl;
import ised.service.implementation.AlumniServiceImpl;
import ised.service.implementation.EnrollmentServiceImpl;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.interfaces.AdmissionService;
import ised.service.interfaces.AlumniService;
import ised.service.interfaces.EnrollmentService;
import ised.service.interfaces.SchoolYearService;
import ised.tools.ComponentFormatter;
import ised.tools.Theme;
import ised.tools.ExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ABDUL
 */
public class AdmitStudentPanel extends javax.swing.JPanel {

    private Registrar parentFrame;
    private List<Student> enrolledStudentsList;
    private List<Student> admittedStudentsList;
    private List<Alumni> alumniList;
    private SchoolYear currentSchoolYear;
    private SchoolYear nextSchoolYear;
    private DefaultTableModel enrolledStudentsTableModel;
    private DefaultTableModel admittedStudentsTableModel;
    private int selectedYearLevel;
    private String searchText;
    private AlumniService alumniService;
    private SchoolYearService schoolYearService;
    private EnrollmentService enrollmentService;
    private AdmissionService admissionService;

    /** Creates new form AdmitStudentPanel */
    public AdmitStudentPanel(Registrar parentFrame, SchoolYear schoolYear) throws ExceptionHandler {
        Theme.setDefaultLookAndFeel();
        initComponents();
        this.parentFrame = parentFrame;
        enrolledStudentsTableModel = (DefaultTableModel) enrolledStudentsTable.getModel();
        admittedStudentsTableModel = (DefaultTableModel) admittedStudentsTable.getModel();
        alumniService = new AlumniServiceImpl();
        schoolYearService = new SchoolYearServiceImpl();
        enrollmentService = new EnrollmentServiceImpl();
        admissionService = new AdmissionServiceImpl();
        alumniList = new ArrayList<Alumni>();
        currentSchoolYear = schoolYear;
        nextSchoolYear = schoolYearService.getNextSchoolYear(currentSchoolYear.getSchoolYearID());

        if (checkNextSchoolYear()) {
            nextSchoolYearLabel.setText(nextSchoolYear.toString());
        }

    }

    public void displayEnrolledStudentsList(List<Student> studentList) throws ExceptionHandler {
        ComponentFormatter.clearTable(enrolledStudentsTableModel);
        for (Student student : studentList) {
            enrolledStudentsTableModel.addRow(new Object[]{student.getFullName(), getRemarks(student)});
        }
    }

    public void displayEnrolledStudentsList() throws ExceptionHandler {
        enrolledStudentsList = enrollmentService.getNotAdmittedStudents(currentSchoolYear.getSchoolYearID(), selectedYearLevel);
        displayEnrolledStudentsList(enrolledStudentsList);
    }

    public int getAdmitTo() {
        String admitToText = admitToComboBox.getSelectedItem() + "";

        if (admitToText.equalsIgnoreCase("1st Year")) {
            return 1;
        } else if (admitToText.equalsIgnoreCase("2nd year")) {
            return 2;
        } else if (admitToText.equalsIgnoreCase("3rd year")) {
            return 3;
        } else if (admitToText.equalsIgnoreCase("4th year")) {
            return 4;
        } else if (admitToText.equalsIgnoreCase("College")) {
            return 5;
        } else {
            return 0;
        }
    }

    public void displayAdmittedStudentsList() throws ExceptionHandler {
        int admitTo = getAdmitTo();
        ComponentFormatter.clearTable(admittedStudentsTableModel);
        if (admitTo == 5) {
            int batchID = alumniService.getBatchByYear(currentSchoolYear.getYearTo());
            alumniList = alumniService.getAlumniList(batchID);
            for (Alumni alumni : alumniList) {
                admittedStudentsTableModel.addRow(new Object[]{alumni.getStudent().getFullName()});
            }
        } else {
            admittedStudentsList = admissionService.getAdmittedStudentsList(nextSchoolYear.getSchoolYearID(), admitTo);
            for (Student student : admittedStudentsList) {
                admittedStudentsTableModel.addRow(new Object[]{student.getFullName()});
            }
        }

    }

    public boolean checkNextSchoolYear() {
        if (nextSchoolYear == null) {
            noteTextField.setText("Note: Next school year is not added in the database. Please contact the administrator to add school year.");
            updateComponents(false);
            return false;
        }
        return true;
    }

    public void updateComponents(boolean flag) {
        searchLabel.setEnabled(flag);
        searchTextField.setEnabled(flag);
        searchButton.setEnabled(flag);
        yearLevelLabel.setEnabled(flag);
        yearLevelComboBox.setEnabled(flag);
        enrolledStudentsTable.setEnabled(flag);
        rightArrowButton.setEnabled(flag);
        leftArrowButton.setEnabled(flag);
        admitToLabel.setEnabled(flag);
        admitToComboBox.setEnabled(flag);
        schoolYearLabel.setEnabled(flag);
        nextSchoolYearLabel.setEnabled(flag);
        admittedStudentsTable.setEnabled(flag);
    }

    public void setAdmitToComboBox(int yearLevel) throws ExceptionHandler {
        String firstYear = "1st Year", secondYear = "2nd year", thirdYear = "3rd year", fourthYear = "4th year";
        admitToComboBox.removeAllItems();
        if (yearLevel == 1) {
            admitToComboBox.addItem(secondYear);
            admitToComboBox.addItem(firstYear);
        } else if (yearLevel == 2) {
            admitToComboBox.addItem(thirdYear);
            admitToComboBox.addItem(secondYear);
        } else if (yearLevel == 3) {
            admitToComboBox.addItem(fourthYear);
            admitToComboBox.addItem(thirdYear);
        } else if (yearLevel == 4) {
            admitToComboBox.addItem("College");
            admitToComboBox.addItem(fourthYear);
        }
    }

    private String getRemarks(Student student) throws ExceptionHandler {
        Enrollment enrollment = enrollmentService.getEnrollment(student.getStudentID(), currentSchoolYear.getSchoolYearID());
        if (!admissionService.hasCompleteGrade(enrollment.getEnrollmentID())) {
            return "Incomplete grade";
        } else if (admissionService.hasFailingGrade(enrollment.getEnrollmentID())) {
            return "Failed";
        } else {
            return "Passed";
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

        jPanel6 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        admittedStudentsTable = new javax.swing.JTable();
        admitToLabel = new javax.swing.JLabel();
        rightArrowButton = new javax.swing.JButton();
        leftArrowButton = new javax.swing.JButton();
        schoolYearLabel = new javax.swing.JLabel();
        nextSchoolYearLabel = new javax.swing.JLabel();
        admitToComboBox = new javax.swing.JComboBox();
        jPanel38 = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        searchLabel = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();
        yearLevelLabel = new javax.swing.JLabel();
        yearLevelComboBox = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        enrolledStudentsTable = new javax.swing.JTable();
        noteTextField = new javax.swing.JLabel();

        jPanel6.setBackground(new java.awt.Color(0, 153, 51));
        jPanel6.setFont(new java.awt.Font("Tahoma", 1, 11));

        jPanel26.setBackground(new java.awt.Color(0, 204, 51));
        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        admittedStudentsTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        admittedStudentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        admittedStudentsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                admittedStudentsTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                admittedStudentsTableMouseEntered(evt);
            }
        });
        jScrollPane6.setViewportView(admittedStudentsTable);

        jPanel26.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 310, 380));

        admitToLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        admitToLabel.setText("Admit to:");
        jPanel26.add(admitToLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, 20));

        rightArrowButton.setBackground(new java.awt.Color(0, 204, 51));
        rightArrowButton.setFont(new java.awt.Font("Tahoma", 1, 14));
        rightArrowButton.setText(">");
        rightArrowButton.setEnabled(false);
        rightArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightArrowButtonActionPerformed(evt);
            }
        });
        jPanel26.add(rightArrowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 60, 30));

        leftArrowButton.setBackground(new java.awt.Color(0, 204, 51));
        leftArrowButton.setFont(new java.awt.Font("Tahoma", 1, 14));
        leftArrowButton.setText("<");
        leftArrowButton.setEnabled(false);
        leftArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftArrowButtonActionPerformed(evt);
            }
        });
        jPanel26.add(leftArrowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 230, 60, 30));

        schoolYearLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        schoolYearLabel.setText("SY:");
        jPanel26.add(schoolYearLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, -1, 20));

        nextSchoolYearLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        nextSchoolYearLabel.setText("---");
        jPanel26.add(nextSchoolYearLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 80, 20));

        admitToComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        admitToComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1st year", "2nd year", "3rd year", "4th year", "College" }));
        admitToComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        admitToComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admitToComboBoxActionPerformed(evt);
            }
        });
        jPanel26.add(admitToComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 80, 20));

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
        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyPressed(evt);
            }
        });
        jPanel38.add(searchTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 210, 20));

        searchLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        searchLabel.setText("Search:");
        jPanel38.add(searchLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        searchButton.setBackground(new java.awt.Color(0, 204, 51));
        searchButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/find.png"))); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        jPanel38.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 40, 20));

        jPanel26.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 320, 40));

        yearLevelLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevelLabel.setText("Year Level:");
        jPanel26.add(yearLevelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, -1, 20));

        yearLevelComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevelComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1st year", "2nd year", "3rd year", "4th year" }));
        yearLevelComboBox.setToolTipText("");
        yearLevelComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        yearLevelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearLevelComboBoxActionPerformed(evt);
            }
        });
        jPanel26.add(yearLevelComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 86, -1));

        enrolledStudentsTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        enrolledStudentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Remark"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        enrolledStudentsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        enrolledStudentsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        enrolledStudentsTable.setDoubleBuffered(true);
        enrolledStudentsTable.setDragEnabled(true);
        enrolledStudentsTable.getTableHeader().setResizingAllowed(false);
        enrolledStudentsTable.getTableHeader().setReorderingAllowed(false);
        enrolledStudentsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                enrolledStudentsTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                enrolledStudentsTableMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                enrolledStudentsTableMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(enrolledStudentsTable);
        enrolledStudentsTable.getColumnModel().getColumn(1).setMinWidth(130);
        enrolledStudentsTable.getColumnModel().getColumn(1).setPreferredWidth(130);
        enrolledStudentsTable.getColumnModel().getColumn(1).setMaxWidth(130);

        jPanel26.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 320, 330));

        noteTextField.setFont(new java.awt.Font("Tahoma", 1, 11));
        noteTextField.setForeground(new java.awt.Color(255, 51, 0));
        jPanel26.add(noteTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 720, 20));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(411, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void leftArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftArrowButtonActionPerformed
        // TODO add your handling code here:
        try {
            if (admittedStudentsTable.getSelectedRowCount() > 0) {
                int index[] = admittedStudentsTable.getSelectedRows();
                for (int i = 0; i < index.length; i++) {

                    if (getAdmitTo() == 5) {
                        Alumni alumni = alumniList.get(index[i]);
                        Enrollment enrollment = enrollmentService.getEnrollment(alumni.getStudent().getStudentID(), currentSchoolYear.getSchoolYearID());
                        int batchID = alumniService.getBatch(enrollment.getSection().getSection().getSectionID());
                        alumniService.deleteAlumni(alumni.getStudent().getStudentID(), enrollment.getEnrollmentID());
                        if (alumniService.isSafeToDelete(batchID)) {
                            alumniService.deleteBatch(batchID);
                        }

                    } else {
                        Student student = admittedStudentsList.get(index[i]);
                        if (!admissionService.isStudentEnrolled(nextSchoolYear.getSchoolYearID(), student.getStudentID())) {
                            Enrollment enrollment = enrollmentService.getEnrollment(student.getStudentID(), currentSchoolYear.getSchoolYearID());
                            admissionService.cancelAdmit(nextSchoolYear.getSchoolYearID(), student.getStudentID(), enrollment.getEnrollmentID());
                        } else{
                            JOptionPane.showMessageDialog(parentFrame, "Admission can't be cancelled", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                displayEnrolledStudentsList();
                displayAdmittedStudentsList();
                leftArrowButton.setEnabled(false);
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AssignStudentSectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_leftArrowButtonActionPerformed

    private void rightArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightArrowButtonActionPerformed
        // TODO add your handling code here:
        try {
            if (enrolledStudentsTable.getSelectedRowCount() > 0) {
                int index[] = enrolledStudentsTable.getSelectedRows();
                for (int i = 0; i < index.length; i++) {
                    Student student = enrolledStudentsList.get(index[i]);

                    String remark = getRemarks(student);
                    if (remark.equalsIgnoreCase("Incomplete Grade")) {
                        JOptionPane.showMessageDialog(parentFrame, student.getFullName() + " can't be admitted because he/she has incomplete grade", "Warning", JOptionPane.WARNING_MESSAGE);
                        continue;
                    } else if (remark.equalsIgnoreCase("Failed") && admitToComboBox.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(parentFrame, student.getFullName() + " can't be admitted because he/she has failing grade", "Warning", JOptionPane.WARNING_MESSAGE);
                        continue;
                    } else {
                        Enrollment enrollment = enrollmentService.getEnrollment(student.getStudentID(), currentSchoolYear.getSchoolYearID());
                        if (getAdmitTo() == 5) {
                            int sectionID = enrollment.getSection().getSection().getSectionID();
                            if (alumniService.checkBatch(sectionID)) {
                                int batchID = alumniService.getBatch(sectionID);
                                alumniService.addAlumni(batchID, student.getStudentID(), enrollment.getEnrollmentID());
                            } else {
                                alumniService.addBatch(sectionID, enrollment.getSchoolYear().getYearTo());
                            }
                        } else {
                            admissionService.admitStudent(nextSchoolYear.getSchoolYearID(), getAdmitTo(), student.getStudentID(), enrollment.getEnrollmentID());
                        }
                    }

                }
                displayEnrolledStudentsList();
                displayAdmittedStudentsList();
                rightArrowButton.setEnabled(false);
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AssignStudentSectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rightArrowButtonActionPerformed

    private void enrolledStudentsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_enrolledStudentsTableMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_enrolledStudentsTableMouseClicked

    private void enrolledStudentsTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_enrolledStudentsTableMouseEntered
        // TODO add your handling code here:
        try {
            if (enrolledStudentsTable.getSelectedRow() >= 0) {
                rightArrowButton.setEnabled(true);
                leftArrowButton.setEnabled(false);
                admittedStudentsTable.removeRowSelectionInterval(0, admittedStudentsTable.getRowCount());
            }
        } catch (IllegalArgumentException ex) {
        }
}//GEN-LAST:event_enrolledStudentsTableMouseEntered

    private void enrolledStudentsTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_enrolledStudentsTableMousePressed
}//GEN-LAST:event_enrolledStudentsTableMousePressed

    private void searchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseClicked
        // TODO add your handling code here:
        searchTextField.setText("");
}//GEN-LAST:event_searchTextFieldMouseClicked

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        try {
            selectedYearLevel = yearLevelComboBox.getSelectedIndex();
            searchText = searchTextField.getText();
            if (selectedYearLevel == 0) {
                return;
            }
            enrolledStudentsList = enrollmentService.getNotAdmittedStudents(currentSchoolYear.getSchoolYearID(), searchText, selectedYearLevel);
            displayEnrolledStudentsList(enrolledStudentsList);
            searchTextField.setText("");
        } catch (ExceptionHandler ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid search", "Search", JOptionPane.WARNING_MESSAGE);
        }
}//GEN-LAST:event_searchButtonActionPerformed

    private void yearLevelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearLevelComboBoxActionPerformed
        // TODO add your handling code here:
        try {
            selectedYearLevel = yearLevelComboBox.getSelectedIndex();
            searchTextField.setText("");
            if (selectedYearLevel > 0) {
                setAdmitToComboBox(selectedYearLevel);
                displayEnrolledStudentsList();
                displayAdmittedStudentsList();
            } else {
                ComponentFormatter.clearTable(enrolledStudentsTableModel);
                ComponentFormatter.clearTable(admittedStudentsTableModel);
                admitToComboBox.removeAllItems();
            }
        } catch (ExceptionHandler ex) {
        }
}//GEN-LAST:event_yearLevelComboBoxActionPerformed

    private void searchTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldKeyPressed

    private void admittedStudentsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_admittedStudentsTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_admittedStudentsTableMouseClicked

    private void admitToComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admitToComboBoxActionPerformed
        try {
            // TODO add your handling code here:
            displayAdmittedStudentsList();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(AdmitStudentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_admitToComboBoxActionPerformed

    private void admittedStudentsTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_admittedStudentsTableMouseEntered
        // TODO add your handling code here:
        try {
            if (admittedStudentsTable.getSelectedRow() >= 0) {
                rightArrowButton.setEnabled(false);
                leftArrowButton.setEnabled(true);
                enrolledStudentsTable.removeRowSelectionInterval(0, enrolledStudentsTable.getRowCount());
            }
        } catch (IllegalArgumentException ex) {
        }
    }//GEN-LAST:event_admittedStudentsTableMouseEntered
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox admitToComboBox;
    private javax.swing.JLabel admitToLabel;
    private javax.swing.JTable admittedStudentsTable;
    private javax.swing.JTable enrolledStudentsTable;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton leftArrowButton;
    private javax.swing.JLabel nextSchoolYearLabel;
    private javax.swing.JLabel noteTextField;
    private javax.swing.JButton rightArrowButton;
    private javax.swing.JLabel schoolYearLabel;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JComboBox yearLevelComboBox;
    private javax.swing.JLabel yearLevelLabel;
    // End of variables declaration//GEN-END:variables
}

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
import ised.gui.dialog.SectionDialog;
import ised.model.Adviser;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import java.util.List;
import ised.model.Class;
import ised.model.SchoolYear;
import ised.model.Student;
import ised.service.implementation.AdviserServiceImpl;
import ised.service.implementation.ClassServiceImpl;
import ised.service.implementation.EnrollmentServiceImpl;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.implementation.SectionServiceImpl;
import ised.service.interfaces.AdviserService;
import ised.service.interfaces.ClassService;
import ised.service.interfaces.EnrollmentService;
import ised.service.interfaces.SchoolYearService;
import ised.service.interfaces.SectionService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ABDUL
 */
public class ManageSectionPanel extends javax.swing.JPanel {

    List<Class> classList;
    List<Student> studentList;
    DefaultTableModel sectionTableModel;
    DefaultTableModel studentTableModel;
    ClassService classService;
    SectionService sectionService;
    EnrollmentService enrollmentService;
    SchoolYearService schoolYearService;
    AdviserService adviserService;
    SchoolYear currentSchoolYear;
    Class selectedSection;
    String selectedStatus;
    Principal parentFrame;

    public ManageSectionPanel(Principal parentFrame, SchoolYear schoolYear) throws ExceptionHandler {
        initComponents();
        this.parentFrame = parentFrame;
        classService = new ClassServiceImpl();
        sectionService = new SectionServiceImpl();
        schoolYearService = new SchoolYearServiceImpl();
        enrollmentService = new EnrollmentServiceImpl();
        adviserService = new AdviserServiceImpl();
        sectionTableModel = (DefaultTableModel) sectionTable.getModel();
        studentTableModel = (DefaultTableModel) studentTable.getModel();
        currentSchoolYear = schoolYear;
        displayClassList();
    }

    private void displayClassList() throws ExceptionHandler {
        selectedStatus = statusComboBox.getSelectedItem().toString();
        classList = classService.getClassList(currentSchoolYear.getSchoolYearID(), selectedStatus);
        ComponentFormatter.clearTable(sectionTableModel);
        for (Class section : classList) {
            sectionTableModel.addRow(new Object[]{getYearLevel(section.getYearLevel()), section.getSection().getSectionName(),});
        }
        totalSection.setText(Integer.toString(sectionTableModel.getRowCount()));
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

    public boolean isSafeToInactive(int yearLevel) {
        int yearLevelCount = 0;
        for (Class section : classList) {
            if (yearLevel == section.getYearLevel()) {
                yearLevelCount++;
            }
        }
        if (yearLevelCount > 1) {
            return true;
        } else {
            return false;
        }
    }

    public void displayStudentList() throws ExceptionHandler {
        int maleCount = 0, femaleCount = 0;
        studentList = enrollmentService.getStudentsListBySection(currentSchoolYear.getSchoolYearID(), selectedSection.getClassID());
        ComponentFormatter.clearTable(studentTableModel);
        for (Student student : studentList) {
            studentTableModel.addRow(new Object[]{student.getFullName(), student.getGender()});
            if (student.getGender().equalsIgnoreCase("Male")) {
                maleCount++;
            } else if (student.getGender().equalsIgnoreCase("Female")) {
                femaleCount++;
            }
        }
        totalStudents.setText(Integer.toString(studentTableModel.getRowCount()));
        male.setText(Integer.toString(maleCount));
        female.setText(Integer.toString(femaleCount));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sectionTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Male = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        totalStudents = new javax.swing.JLabel();
        male = new javax.swing.JLabel();
        female = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        yearLevelSection = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        totalSection = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        changeStatusButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        curriculum = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        adviserText = new javax.swing.JLabel();

        jPanel6.setBackground(new java.awt.Color(0, 153, 51));

        jPanel1.setBackground(new java.awt.Color(0, 204, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sectionTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        sectionTable.setModel(new javax.swing.table.DefaultTableModel(
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
        sectionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sectionTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sectionTableMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sectionTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(sectionTable);
        sectionTable.getColumnModel().getColumn(0).setMinWidth(100);
        sectionTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        sectionTable.getColumnModel().getColumn(0).setMaxWidth(100);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 320, 360));

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
        jScrollPane2.setViewportView(studentTable);
        studentTable.getColumnModel().getColumn(1).setMinWidth(100);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(1).setMaxWidth(100);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 310, 330));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel1.setText("Total Students:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 430, -1, -1));

        Male.setFont(new java.awt.Font("Tahoma", 1, 12));
        Male.setText("Male:");
        jPanel1.add(Male, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 450, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel3.setText("Female:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 450, -1, -1));

        totalStudents.setFont(new java.awt.Font("Tahoma", 1, 12));
        totalStudents.setText("---");
        jPanel1.add(totalStudents, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 430, -1, -1));

        male.setFont(new java.awt.Font("Tahoma", 1, 12));
        male.setText("---");
        jPanel1.add(male, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 450, -1, -1));

        female.setFont(new java.awt.Font("Tahoma", 1, 12));
        female.setText("----");
        jPanel1.add(female, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Yr. Level & Section:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));

        yearLevelSection.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        yearLevelSection.setText("---");
        jPanel1.add(yearLevelSection, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 160, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel9.setText("Total Section:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, -1, -1));

        totalSection.setFont(new java.awt.Font("Tahoma", 1, 12));
        totalSection.setText("---");
        jPanel1.add(totalSection, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 440, -1, -1));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        addButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/add.png"))); // NOI18N
        addButton.setText("Add");
        addButton.setOpaque(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(addButton);

        editButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/pencil.png"))); // NOI18N
        editButton.setText("Edit");
        editButton.setEnabled(false);
        editButton.setOpaque(false);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(editButton);

        changeStatusButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        changeStatusButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/pencil.png"))); // NOI18N
        changeStatusButton.setText("Set as Inactive");
        changeStatusButton.setEnabled(false);
        changeStatusButton.setOpaque(false);
        changeStatusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeStatusButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(changeStatusButton);

        deleteButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/delete.png"))); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.setEnabled(false);
        deleteButton.setOpaque(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(deleteButton);

        jPanel1.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 314, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel11.setText("Status:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, 20));

        statusComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Active", "Inactive" }));
        statusComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        statusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusComboBoxActionPerformed(evt);
            }
        });
        jPanel1.add(statusComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 90, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("Update Section");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 440, 130, -1));

        curriculum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        curriculum.setText("---");
        jPanel1.add(curriculum, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 70, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Curriculum:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Adviser:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, -1, -1));

        adviserText.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        adviserText.setText("---");
        jPanel1.add(adviserText, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, 160, -1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(330, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sectionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sectionTableMouseClicked
        // TODO add your handling code here:
        try {
            int index = sectionTable.getSelectedRow();
            if (index >= 0) {
                selectedSection = classList.get(index);
                Adviser adviser = adviserService.getAdviser(selectedSection.getClassID());
                if (adviser != null) {
                    adviserText.setText(adviser.getEmployee().getFullName());
                }
                yearLevelSection.setText(getYearLevel(selectedSection.getYearLevel()) + " - " + selectedSection.getSection().getSectionName());
                curriculum.setText(selectedSection.getSection().getCurriculum().getCurriculumYear() + "");
                displayStudentList();
            }
        } catch (ExceptionHandler ex) {
        }
    }//GEN-LAST:event_sectionTableMouseClicked

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        try {
            SectionDialog addSection = new SectionDialog(parentFrame, true, null);
            addSection.setVisible(true);
            displayClassList();
        } catch (ExceptionHandler ex) {
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
        try {
            if (selectedSection != null) {
                SectionDialog editSection = new SectionDialog(parentFrame, true, selectedSection);
                editSection.setVisible(true);
                Class section = editSection.getSection();
                if (section != null) {
                    selectedSection = section;
                    displayClassList();
                }
            }
        } catch (ExceptionHandler ex) {
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void changeStatusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeStatusButtonActionPerformed
        // TODO add your handling code here
        try {
            if (selectedSection.getSection().getSectionStatus().equalsIgnoreCase("Active")) {
                sectionService.changeSectionStatus(selectedSection.getSection().getSectionID(), "Inactive");
                changeStatusButton.setText("Set as Active");
                displayClassList();
            } else {
                sectionService.changeSectionStatus(selectedSection.getSection().getSectionID(), "Active");
                changeStatusButton.setText("Set as Inactive");
                displayClassList();
            }
        } catch (ExceptionHandler ex) {
        }
    }//GEN-LAST:event_changeStatusButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        int option = JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to delete this student?",
                "Delete Student", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                sectionService.deleteSection(selectedSection.getSection().getSectionID());
                displayClassList();
            } catch (ExceptionHandler ex) {
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void statusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboBoxActionPerformed
        try {
            // TODO add your handling code here:
            displayClassList();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(ManageSectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_statusComboBoxActionPerformed

    private void sectionTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sectionTableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_sectionTableMouseEntered

    private void sectionTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sectionTableMousePressed
        // TODO add your handling code here:
        try {
            int index = sectionTable.getSelectedRow();
            if (index >= 0) {
                selectedSection = classList.get(index);
                editButton.setEnabled(true);

                if (selectedSection.getYearLevel() == 1 && !classService.hasStudents(selectedSection.getClassID())) {
                    deleteButton.setEnabled(true);
                } else {
                    deleteButton.setEnabled(false);
                }
                if (selectedSection.getSection().getSectionStatus().equalsIgnoreCase("Active")) {
                    if (selectedSection.getYearLevel() > 1 && !classService.hasStudents(selectedSection.getClassID()) && isSafeToInactive(selectedSection.getYearLevel())) {
                        changeStatusButton.setEnabled(true);
                        changeStatusButton.setText("Set as Inactive");
                    } else {
                        changeStatusButton.setEnabled(false);
                    }
                } else {
                    changeStatusButton.setEnabled(true);
                    changeStatusButton.setText("Set as Active");
                }
            }
        } catch (ExceptionHandler ex) {
        }
    }//GEN-LAST:event_sectionTableMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            SchoolYear prevSchoolYear = schoolYearService.getPreviousSchoolYear(currentSchoolYear.getSchoolYearID());
            if (prevSchoolYear != null) {
                List<Class> classList = classService.getClassList(prevSchoolYear.getSchoolYearID(), "Active");
                for (Class section : classList) {
                    if (!isSectionExist(section)) {
                        if (section.getYearLevel() <= 3) {
                            Class newSection = new Class();
                            newSection.setSchoolYear(currentSchoolYear);
                            newSection.setSection(section.getSection());
                            newSection.setYearLevel(section.getYearLevel() + 1);
                            classService.addClass(newSection);
                            displayClassList();
                        }
                    }

                }
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(ManageSectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private boolean isSectionExist(Class section) {
        for (Class section2 : classList) {
            if (section2.getSection().getSectionID() == section.getSection().getSectionID()) {
                return true;
            }
        }
        return false;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Male;
    private javax.swing.JButton addButton;
    private javax.swing.JLabel adviserText;
    private javax.swing.JButton changeStatusButton;
    private javax.swing.JLabel curriculum;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel female;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel male;
    private javax.swing.JTable sectionTable;
    private javax.swing.JComboBox statusComboBox;
    private javax.swing.JTable studentTable;
    private javax.swing.JLabel totalSection;
    private javax.swing.JLabel totalStudents;
    private javax.swing.JLabel yearLevelSection;
    // End of variables declaration//GEN-END:variables
}

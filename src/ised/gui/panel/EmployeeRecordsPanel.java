/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmployeeRecordsPanel.java
 *
 * Created on 10 15, 11, 1:54:01 PM
 */
package ised.gui.panel;

import ised.gui.Registrar;
import ised.gui.dialog.ChangeEmpStatusDialog;
import ised.gui.dialog.EmployeeDialog;
import ised.model.Employee;
import ised.service.implementation.EmployeeServiceImpl;
import ised.service.interfaces.EmployeeService;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import ised.tools.Theme;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Blob;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ABDUL
 */
public class EmployeeRecordsPanel extends javax.swing.JPanel {

    Registrar parentFrame;
    private Employee selectedEmployee;
    private EmployeeService employeeService;
    private int selectedEmployeeIndex;
    private DefaultTableModel employeeTableModel;
    private List<Employee> employeeList;
    private int searchOption;
    private int selectedYearLevel;
    private String searchText;
    private String selectedStatus;

    /** Creates new form EmployeeRecordsPanel */
    public EmployeeRecordsPanel(Registrar parentFrame) throws ExceptionHandler {
        Theme.setDefaultLookAndFeel();
        initComponents();
        this.parentFrame = parentFrame;
        employeeService = new EmployeeServiceImpl();
        employeeTableModel = (DefaultTableModel) employeeTable.getModel();
        displayEmployeeList();

        ComponentFormatter.stripTable(employeeTable);
        ComponentFormatter.setTableTextColor(employeeProfileTable);
        ComponentFormatter.colorTextInformationEmployeeTable(employeeProfileTable);
    }

    public void displayEmployeeList(List<Employee> employeeList) {
        ComponentFormatter.clearTable(employeeTableModel);
        for (Employee employee : employeeList) {
            employeeTableModel.addRow(new Object[]{employee.getEmployeeID(), employee.getFullName()});
        }
        totalEmployees.setText(Integer.toString(employeeTableModel.getRowCount()));
    }

    public void displayEmployeeList() throws ExceptionHandler {
        if (statusComboBox.getSelectedIndex() == 0) {
            ComponentFormatter.clearTable(employeeTableModel);
            return;
        } else {
            selectedStatus = statusComboBox.getSelectedItem().toString();
            employeeList = employeeService.getEmployeeList(selectedStatus);
        }
        displayEmployeeList(employeeList);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        pictureLabel = new javax.swing.JLabel();
        employeeProfileTable = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        addRecordButton = new javax.swing.JButton();
        editProfileButton = new javax.swing.JButton();
        changeEmpStatusButton = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        jPanel38 = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();
        statusComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        totalEmployees = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 204, 51));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);
        jToolBar1.add(jSeparator1);

        addRecordButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        addRecordButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/add-18.png"))); // NOI18N
        addRecordButton.setText("ADD RECORD");
        addRecordButton.setFocusable(false);
        addRecordButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        addRecordButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        addRecordButton.setOpaque(false);
        addRecordButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRecordButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(addRecordButton);

        editProfileButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        editProfileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/edit24.png"))); // NOI18N
        editProfileButton.setText("EDIT PROFILE");
        editProfileButton.setEnabled(false);
        editProfileButton.setFocusable(false);
        editProfileButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        editProfileButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        editProfileButton.setOpaque(false);
        editProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProfileButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(editProfileButton);

        changeEmpStatusButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        changeEmpStatusButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/employe_status.png"))); // NOI18N
        changeEmpStatusButton.setText("CHANGE EMPLOYEE STATUS");
        changeEmpStatusButton.setEnabled(false);
        changeEmpStatusButton.setFocusable(false);
        changeEmpStatusButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        changeEmpStatusButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        changeEmpStatusButton.setOpaque(false);
        changeEmpStatusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeEmpStatusButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(changeEmpStatusButton);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(pictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(employeeProfileTable, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeProfileTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        employeeProfileTable.getColumnModel().getColumn(0).setMinWidth(200);
        employeeProfileTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        employeeProfileTable.getColumnModel().getColumn(0).setMaxWidth(200);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(386, 20, -1, 490));

        jPanel22.setBackground(new java.awt.Color(0, 204, 51));
        jPanel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel22.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        employeeTable.setAutoCreateRowSorter(true);
        employeeTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Number", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        employeeTable.setDoubleBuffered(true);
        employeeTable.setDragEnabled(true);
        employeeTable.getTableHeader().setResizingAllowed(false);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                employeeTableMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                employeeTableMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(employeeTable);
        employeeTable.getColumnModel().getColumn(0).setMinWidth(100);
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(0).setMaxWidth(100);

        jPanel22.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 88, 300, 350));

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
        jPanel38.add(searchTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 190, 20));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel63.setText("Search:");
        jPanel38.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        searchButton.setBackground(new java.awt.Color(0, 204, 51));
        searchButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/find.png"))); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        jPanel38.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 40, 20));

        jPanel22.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 13, 300, 40));

        statusComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Active", "Inactive" }));
        statusComboBox.setToolTipText("");
        statusComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        statusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusComboBoxActionPerformed(evt);
            }
        });
        jPanel22.add(statusComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 59, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel1.setText("Status:");
        jPanel22.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(206, 64, -1, 13));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setText("Total Employees:");
        jPanel22.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        totalEmployees.setFont(new java.awt.Font("Tahoma", 1, 12));
        totalEmployees.setText("---");
        jPanel22.add(totalEmployees, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, -1, -1));

        jPanel1.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 20, 340, 490));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1229, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRecordButtonActionPerformed
        // TODO add your handling code here:
        try {
            EmployeeDialog addEmployee = new EmployeeDialog(parentFrame, true, null);
            addEmployee.setVisible(true);
            Employee employee = addEmployee.getSelectedEmployee();
            if (employee != null) {
                selectedEmployee = employee;
                displayEmployeeList();
                displayEmployeeProfile(selectedEmployee, employeeList.size() - 1);
            }
        } catch (ExceptionHandler ex) {
        }
}//GEN-LAST:event_addRecordButtonActionPerformed

    private void editProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProfileButtonActionPerformed
        // TODO add your handling code here:
        if (selectedEmployee != null) {
            EmployeeDialog editEmployee = new EmployeeDialog(parentFrame, true, selectedEmployee);
            editEmployee.setVisible(true);
            Employee employee = editEmployee.getSelectedEmployee();
            if (employee != null) {
                selectedEmployee = employee;
                if (employeeList.size() > 0) {
                    employeeList.set(selectedEmployeeIndex, employee);
                    employeeTableModel.setValueAt(employee.toString(), selectedEmployeeIndex, 1);
                }
                displayEmployeeProfile(selectedEmployee, selectedEmployeeIndex);
            }
        }
}//GEN-LAST:event_editProfileButtonActionPerformed

    private void changeEmpStatusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeEmpStatusButtonActionPerformed
        // TODO add your handling code here:
        ChangeEmpStatusDialog updateEmpStatus = new ChangeEmpStatusDialog(parentFrame, true, selectedEmployee);
        updateEmpStatus.setVisible(true);
        Employee employee = updateEmpStatus.getUpdatedEmployee();
        if (employee != null) {
            try {
                selectedEmployee = employee;
                employeeProfileTable.setValueAt(employee.getStatus(), 11, 1);
                displayEmployeeList();
            } catch (ExceptionHandler ex) {
                Logger.getLogger(EmployeeRecordsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

}//GEN-LAST:event_changeEmpStatusButtonActionPerformed

    private void employeeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTableMouseClicked
        // TODO add your handling code here:
        int index = employeeTable.getSelectedRow();
        if (index >= 0) {
            if (selectedEmployee != null && selectedEmployeeIndex == index) {
                displayEmployeeProfile(null, index);
                editProfileButton.setEnabled(false);
                changeEmpStatusButton.setEnabled(false);
            } else {
                Employee employee = employeeList.get(index);
                displayEmployeeProfile(employee, index);
                editProfileButton.setEnabled(true);
                changeEmpStatusButton.setEnabled(true);
            }
        }

}//GEN-LAST:event_employeeTableMouseClicked

    private void employeeTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTableMouseEntered
        // TODO add your handling code here:
}//GEN-LAST:event_employeeTableMouseEntered

    private void employeeTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTableMousePressed
}//GEN-LAST:event_employeeTableMousePressed

    private void searchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseClicked
        // TODO add your handling code here:
        searchTextField.setText("");
}//GEN-LAST:event_searchTextFieldMouseClicked

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        try {
            selectedStatus = statusComboBox.getSelectedItem().toString();
            searchText = searchTextField.getText();
            employeeList = employeeService.getEmployeeList(selectedStatus, searchText);
            displayEmployeeList(employeeList);
            searchTextField.setText("");
        } catch (ExceptionHandler ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid search", "Search", JOptionPane.WARNING_MESSAGE);
        }
}//GEN-LAST:event_searchButtonActionPerformed

    private void statusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboBoxActionPerformed
        try {
            // TODO add your handling code here:
            displayEmployeeList();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(StudentRecordsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_statusComboBoxActionPerformed

    public void displayEmployeeProfile(Employee employee, int selectedEmployeeIndex) {
        if (employee != null) {
            selectedEmployee = employee;
            this.selectedEmployeeIndex = selectedEmployeeIndex;
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
        } else {
            selectedEmployee = null;
            this.selectedEmployeeIndex = selectedEmployeeIndex;
            for (int i = 1; i <= 15; i++) {
                if (i != 12) {
                    employeeProfileTable.setValueAt(null, i, 1);
                }
            }
            pictureLabel.setIcon(null);
            pictureLabel.setText("PICTURE");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRecordButton;
    private javax.swing.JButton changeEmpStatusButton;
    private javax.swing.JButton editProfileButton;
    private javax.swing.JTable employeeProfileTable;
    private javax.swing.JTable employeeTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel pictureLabel;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JComboBox statusComboBox;
    private javax.swing.JLabel totalEmployees;
    // End of variables declaration//GEN-END:variables
}

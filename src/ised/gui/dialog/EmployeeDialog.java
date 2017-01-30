/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmployeeDialog.java
 *
 * Created on 10 19, 11, 5:16:07 AM
 */
package ised.gui.dialog;

import com.mysql.jdbc.Blob;
import ised.model.Employee;
import ised.service.implementation.EmployeeServiceImpl;
import ised.service.interfaces.EmployeeService;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import ised.tools.FormValidation;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author ABDUL
 */
public class EmployeeDialog extends javax.swing.JDialog {

    private Employee selectedEmployee;
    private javax.swing.JFrame parent;
    private JFileChooser fileChooser;
    private Object obj;
    private File file;
    private EmployeeService employeeService;
    private String title;
    boolean isAdd;

    /** Creates new form EmployeeDialog */
    public EmployeeDialog(javax.swing.JFrame parent, boolean modal, Employee employee) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        selectedEmployee = employee;
        this.parent = parent;
        title = "Add Employee Profile";
        isAdd = true;
        fileChooser = new JFileChooser();
        employeeService = new EmployeeServiceImpl();


        ComponentFormatter.setUpYear(yearComboBox, 1970, 18);
        ComponentFormatter.setUpYear(yearAdmittedComboBox, 1980);
        ComponentFormatter.setUpYear(yearGraduatedComboBox, 1970);
        ComponentFormatter.formatDateComboBox(yearComboBox, monthComboBox, dayComboBox);

        displayEmployeeInfo();
    }

    public void displayEmployeeInfo() {
        if (selectedEmployee != null) {
            isAdd = false;
            title = "Edit Employee Profile";
            idNumberTextField.setText(Integer.toString(selectedEmployee.getEmployeeID()));
            lastNameTextField.setText(selectedEmployee.getLastName());
            firstNameTextField.setText(selectedEmployee.getFirstName());
            middleNameTextField.setText(selectedEmployee.getMiddleName());
            ComponentFormatter.formatDateNoToWord(selectedEmployee.getDOB(), yearComboBox, monthComboBox, dayComboBox);
            genderComboBox.setSelectedItem(selectedEmployee.getGender());
            religionTextField.setText(selectedEmployee.getReligion());
            civilStatusComboBox.setSelectedItem(selectedEmployee.getCivilStatus());
            addressTextField.setText(selectedEmployee.getAddress());
            contactNoTextField.setText(selectedEmployee.getContactNo());
            positionTextField.setText(selectedEmployee.getPosition());
            yearAdmittedComboBox.setSelectedItem(selectedEmployee.getYearAdmitted());
            isTeacherCheckBox.setSelected(selectedEmployee.getIsTeacher() == 1 ? true : false);
            finishedDegreeTextField.setText(selectedEmployee.getFinishedDegree());
            schoolTextField.setText(selectedEmployee.getSchoolGraduated());
            yearGraduatedComboBox.setSelectedItem(selectedEmployee.getYearGraduated());

            if (selectedEmployee.getPicture() != null) {
                obj = selectedEmployee.getPicture();
                pictureLabel.setText("");
                if (obj instanceof com.mysql.jdbc.Blob) {
                    pictureLabel.setIcon(new javax.swing.ImageIcon(ComponentFormatter.convertToActualSizeImage((Blob) obj)));
                } else {
                    Image image = Toolkit.getDefaultToolkit().getImage(obj.toString()).getScaledInstance(ComponentFormatter.IMAGE_WIDTH, ComponentFormatter.IMAGE_HEIGHT, 129);
                    obj = new javax.swing.ImageIcon(image);
                    pictureLabel.setIcon((Icon) obj);
                }
            } else {
                pictureLabel.setIcon(null);
            }
        }
    }

    private void saveEmployee(Employee employee) {
        try {
            if (isAdd) {
                if (employeeService.checkEmployee(employee)) {
                    employee = employeeService.addEmployee(employee);
                    selectedEmployee = employee;
                    JOptionPane.showMessageDialog(this, "Employee Record has successfully added", title, JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(parent, "Record already present in the database", title, JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                if (employeeService.checkEmployee(employee) || selectedEmployee.toString().equalsIgnoreCase(employee.toString())) {
                    employeeService.editEmployee(employee);
                    selectedEmployee = employee;
                    JOptionPane.showMessageDialog(this, "Employee Record has successfully edited", title, JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(parent, "Record already present in the database", title, JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (ExceptionHandler ex) {
        }
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        middleNameTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        addressTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        schoolTextField = new javax.swing.JTextField();
        finishedDegreeTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        yearGraduatedComboBox = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        civilStatusComboBox = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        contactNoTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        positionTextField = new javax.swing.JTextField();
        isTeacherCheckBox = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        idNumberTextField = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        dayComboBox = new javax.swing.JComboBox();
        monthComboBox = new javax.swing.JComboBox();
        yearComboBox = new javax.swing.JComboBox();
        yearAdmittedComboBox = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        religionTextField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        pictureLabel = new javax.swing.JLabel();
        uploadButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBackground(new java.awt.Color(0, 204, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "EMPLOYEE'S INFORMATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel1.setText("Last Name");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 20));

        lastNameTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel2.add(lastNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 50, 140, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setText("First Name");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, -1, 20));

        firstNameTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel2.add(firstNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(321, 50, 160, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel3.setText("Middle Name");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, -1, -1));

        middleNameTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel2.add(middleNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 130, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel4.setText("Gender");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 20));

        addressTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel2.add(addressTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 620, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel5.setText("Address");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        genderComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        jPanel2.add(genderComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 78, -1));

        jPanel3.setBackground(new java.awt.Color(0, 204, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Educational Background", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel3.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel7.setText("Finished Highest Degree:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setText("School:");

        schoolTextField.setFont(new java.awt.Font("Tahoma", 1, 12));

        finishedDegreeTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        finishedDegreeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishedDegreeTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel6.setText("Year Graduated:");

        yearGraduatedComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearGraduatedComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(finishedDegreeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(schoolTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearGraduatedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(finishedDegreeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(schoolTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(yearGraduatedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 680, 90));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel10.setText("Civil Status");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, -1, 20));

        civilStatusComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        civilStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Single", "Married" }));
        civilStatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                civilStatusComboBoxActionPerformed(evt);
            }
        });
        jPanel2.add(civilStatusComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 88, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel11.setText("Contact No.");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        contactNoTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel2.add(contactNoTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 270, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel12.setText("Position:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, 20));

        positionTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel2.add(positionTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 290, -1));

        isTeacherCheckBox.setBackground(new java.awt.Color(0, 204, 102));
        isTeacherCheckBox.setFont(new java.awt.Font("Tahoma", 1, 12));
        isTeacherCheckBox.setText("Teacher");
        isTeacherCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isTeacherCheckBoxActionPerformed(evt);
            }
        });
        jPanel2.add(isTeacherCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel13.setText("Year Admitted:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, -1, 20));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel14.setText("ID No.");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        idNumberTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        idNumberTextField.setText("---");
        jPanel2.add(idNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        jPanel5.setBackground(new java.awt.Color(0, 204, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Birthdate", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel16.setText("Month");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel17.setText("Day");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel18.setText("Year");

        dayComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        monthComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        monthComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        monthComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                monthComboBoxItemStateChanged(evt);
            }
        });

        yearComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        yearComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                yearComboBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(dayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(227, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(dayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 680, 50));

        yearAdmittedComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearAdmittedComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(yearAdmittedComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, 70, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel20.setText("Religion:");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, -1, 20));

        religionTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        jPanel2.add(religionTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 110, -1));

        saveButton.setBackground(new java.awt.Color(0, 204, 51));
        saveButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/saveIcon1.png"))); // NOI18N
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setBackground(new java.awt.Color(0, 204, 51));
        cancelButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/cancel24.png"))); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        pictureLabel.setBackground(new java.awt.Color(255, 255, 255));
        pictureLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        pictureLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pictureLabel.setText("PICTURE");
        pictureLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pictureLabel.setOpaque(true);

        uploadButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        uploadButton.setForeground(new java.awt.Color(102, 0, 0));
        uploadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/upload.png"))); // NOI18N
        uploadButton.setText("UPLOAD");
        uploadButton.setOpaque(false);
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(saveButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(uploadButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(cancelButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(pictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(uploadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 924, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        if (!FormValidation.validateStringField(firstNameTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parent, "Invalid First Name", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(lastNameTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parent, "Invalid Last Name", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(middleNameTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parent, "Invalid Middle Name", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(religionTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parent, "Invalid Religion", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(addressTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parent, "Invalid Address", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateNumberField(contactNoTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parent, "Invalid Contact Number", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(finishedDegreeTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parent, "Finished Highest Degree is empty ", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(schoolTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parent, "School Graduated is empty", title, JOptionPane.WARNING_MESSAGE);
        } else {
            Employee employee = new Employee();

            if (selectedEmployee != null) {
                employee.setEmployeeID(selectedEmployee.getEmployeeID());
            }
            String firstName = ComponentFormatter.formatString(firstNameTextField.getText().trim());
            String lastName = ComponentFormatter.formatString(lastNameTextField.getText().trim());
            String middleName = ComponentFormatter.formatString(middleNameTextField.getText().trim());
            Date birthDate = ComponentFormatter.formatDate(yearComboBox, monthComboBox, dayComboBox);
            String religion = ComponentFormatter.formatString(religionTextField.getText().trim());
            String civilStatus = civilStatusComboBox.getSelectedItem().toString();
            String gender = genderComboBox.getSelectedItem().toString();
            String address = ComponentFormatter.formatString(addressTextField.getText().trim());
            String contactNo = contactNoTextField.getText().trim();
            String position = ComponentFormatter.formatString(positionTextField.getText().trim());
            int yearAdmitted = Integer.parseInt(yearAdmittedComboBox.getSelectedItem().toString());
            int isTeacher = (isTeacherCheckBox.isSelected() ? 1 : 0);
            String finsihedDegree = ComponentFormatter.formatString(finishedDegreeTextField.getText().trim());
            String schoolGraduated = ComponentFormatter.formatString(schoolTextField.getText().trim());
            int yearGraduated = Integer.parseInt(yearGraduatedComboBox.getSelectedItem().toString());
            //String specialization = null;

            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setMiddleName(middleName);
            employee.setDOB(birthDate);
            employee.setReligion(religion);
            employee.setCivilStatus(civilStatus);
            employee.setGender(gender);
            employee.setAddress(address);
            employee.setContactNo(contactNo);
            employee.setPosition(position);
            employee.setYearAdmitted(yearAdmitted);
            employee.setIsTeacher(isTeacher);
            employee.setFinishedDegree(finsihedDegree);
            employee.setSchoolGraduated(schoolGraduated);
            employee.setYearGraduated(yearGraduated);
            employee.setStatus("Active");

            if (obj != null && file != null) {
                obj = file;
                employee.setPicture(obj);
            }

            employee.setPicture(obj);
            saveEmployee(employee);
        }

}//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        selectedEmployee = null;
        this.setVisible(false);
}//GEN-LAST:event_cancelButtonActionPerformed

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed
        // TODO add your handling code here:
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            if (FormValidation.validateImageFileName(file)) {
                Image image = Toolkit.getDefaultToolkit().getImage(file.toString()).getScaledInstance(ComponentFormatter.IMAGE_WIDTH, ComponentFormatter.IMAGE_HEIGHT, 100);
                obj = new javax.swing.ImageIcon(image);
                pictureLabel.setIcon((Icon) obj);
                pictureLabel.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid File Picture", "Upload Picture", JOptionPane.WARNING_MESSAGE);
            }
        }
}//GEN-LAST:event_uploadButtonActionPerformed

    private void civilStatusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_civilStatusComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_civilStatusComboBoxActionPerformed

    private void finishedDegreeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishedDegreeTextFieldActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_finishedDegreeTextFieldActionPerformed

    private void isTeacherCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isTeacherCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_isTeacherCheckBoxActionPerformed

    private void monthComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_monthComboBoxItemStateChanged
        // TODO add your handling code here:
        ComponentFormatter.formatDateComboBox(yearComboBox, monthComboBox, dayComboBox);
    }//GEN-LAST:event_monthComboBoxItemStateChanged

    private void yearComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_yearComboBoxItemStateChanged
        // TODO add your handling code here:
        ComponentFormatter.formatDateComboBox(yearComboBox, monthComboBox, dayComboBox);
    }//GEN-LAST:event_yearComboBoxItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox civilStatusComboBox;
    private javax.swing.JTextField contactNoTextField;
    private javax.swing.JComboBox dayComboBox;
    private javax.swing.JTextField finishedDegreeTextField;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JComboBox genderComboBox;
    private javax.swing.JLabel idNumberTextField;
    private javax.swing.JCheckBox isTeacherCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JTextField middleNameTextField;
    private javax.swing.JComboBox monthComboBox;
    private javax.swing.JLabel pictureLabel;
    private javax.swing.JTextField positionTextField;
    private javax.swing.JTextField religionTextField;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField schoolTextField;
    private javax.swing.JButton uploadButton;
    private javax.swing.JComboBox yearAdmittedComboBox;
    private javax.swing.JComboBox yearComboBox;
    private javax.swing.JComboBox yearGraduatedComboBox;
    // End of variables declaration//GEN-END:variables
}

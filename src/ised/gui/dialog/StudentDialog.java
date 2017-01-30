/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StudentDialog.java
 *
 * Created on 10 16, 11, 3:51:42 PM
 */
package ised.gui.dialog;

import ised.model.Guardian;
import ised.model.SchoolYear;
import ised.model.Student;
import ised.service.implementation.StudentServiceImpl;
import ised.service.interfaces.StudentService;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import ised.tools.FormValidation;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.sql.Blob;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author ABDUL
 */
public class StudentDialog extends javax.swing.JDialog {

    private javax.swing.JFrame parentFrame;
    private Student selectedStudent;
    private StudentService studentService;
    private SchoolYear currentSchoolYear;
    private JFileChooser fileChooser;
    private Object obj;
    private File file;
    private String title;
    private boolean isAdd;

    /** Creates new form StudentDialog */
    public StudentDialog(javax.swing.JFrame parentFrame, boolean modal, Student student, SchoolYear schoolYear) {
        super(parentFrame, modal);
        initComponents();
        this.setLocationRelativeTo(parentFrame);
        selectedStudent = student;
        this.parentFrame = parentFrame;
        title = "Add Student Profile";
        isAdd = true;
        currentSchoolYear = schoolYear;

        fileChooser = new JFileChooser();
        studentService = new StudentServiceImpl();       
        ComponentFormatter.setUpYear(yearComboBox, 1990, 9);
        ComponentFormatter.formatDateComboBox(yearComboBox, monthComboBox, dayComboBox);
        displayStudentInfo();

    }

    public void displayStudentInfo() {
        if (selectedStudent != null) {
            isAdd = false;
            title = "Edit Student Profile";
            idNumTextField.setText(Integer.toString(selectedStudent.getStudentID()));
            lastNameTextField.setText(selectedStudent.getLastName());
            firstNameTextField.setText(selectedStudent.getFirstName());
            middleNameTextField.setText(selectedStudent.getMiddleName());
            ComponentFormatter.formatDateNoToWord(selectedStudent.getDOB(), yearComboBox, monthComboBox, dayComboBox);
            genderComboBox.setSelectedItem(selectedStudent.getGender());
            religionTextField.setText(selectedStudent.getReligion());
            ethnicGroupTextField.setText(selectedStudent.getEthnicGroup());
            entranceRatingTextField.setText(Integer.toString(selectedStudent.getEntranceTestRating()));
            homeAddTextField.setText(selectedStudent.getHomeAdd());
            presAddTextField.setText(selectedStudent.getPresentAdd());
            birthPlaceTextField.setText(selectedStudent.getPOB());
            schoolLastAttTextField.setText(selectedStudent.getLastSchoolAtt());
            guardianNameTextField.setText(selectedStudent.getGuardian().getName());
            guardianOccupTextField.setText(selectedStudent.getGuardian().getOccupation());
            contactNoTextField.setText(selectedStudent.getGuardian().getContactNo());
            guardianAddTextField.setText(selectedStudent.getGuardian().getAddress());

            if (selectedStudent.getPicture() != null) {
                obj = selectedStudent.getPicture();
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

    private void saveStudent(Student student) {
        try {
            if (isAdd) {
                if (studentService.checkStudent(student)) {
                    student = studentService.addStudent(student);
                    selectedStudent = student;
                    JOptionPane.showMessageDialog(this, "Student Record has successfully added", title, JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Record already present in the database", title, JOptionPane.WARNING_MESSAGE);
                }
            } else {
                if (studentService.checkStudent(student) || selectedStudent.toString().equalsIgnoreCase(student.toString())) {
                    studentService.editStudent(student);
                    selectedStudent = student;
                    JOptionPane.showMessageDialog(this, "Student Record has successfully edited", title, JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Record already present in the database", "Add Student", JOptionPane.WARNING_MESSAGE);
                }
            }

        } catch (ExceptionHandler ex) {
        }
    }

    public Student getStudent() {
        return selectedStudent;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        birthPlaceTextField = new javax.swing.JTextField();
        middleNameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dayComboBox = new javax.swing.JComboBox();
        monthComboBox = new javax.swing.JComboBox();
        yearComboBox = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        guardianAddTextField = new javax.swing.JTextField();
        guardianOccupTextField = new javax.swing.JTextField();
        contactNoTextField = new javax.swing.JTextField();
        guardianNameTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        homeAddTextField = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        entranceRatingTextField = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        presAddTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        schoolLastAttTextField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        idNumTextField = new javax.swing.JLabel();
        religionTextField = new javax.swing.JTextField();
        ethnicGroupTextField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        pictureLabel = new javax.swing.JLabel();
        uploadButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 204, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 204, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "STUDENT'S INFORMATION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel4.setText("Gender");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel5.setText("Home Address");

        genderComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        genderComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel9.setText("Birth Place");

        birthPlaceTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        middleNameTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel3.setText("Middle Name");

        firstNameTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setText("First Name");

        lastNameTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel1.setText("Last Name");

        jPanel4.setBackground(new java.awt.Color(0, 204, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Birthdate", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel6.setText("Month");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel7.setText("Day");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setText("Year");

        dayComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dayComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        dayComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        monthComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        monthComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        monthComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        monthComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                monthComboBoxItemStateChanged(evt);
            }
        });

        yearComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        yearComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1990" }));
        yearComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        yearComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                yearComboBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(dayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(dayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 204, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Guardian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel5.setMaximumSize(new java.awt.Dimension(0, 0));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel13.setText("Name");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel16.setText("Occupation");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel17.setText("Contact No.");

        guardianAddTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        guardianOccupTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        contactNoTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        guardianNameTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel15.setText("Address");

        jButton1.setText("...");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(10, 10, 10)
                        .addComponent(guardianNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(10, 10, 10)
                        .addComponent(guardianAddTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(guardianOccupTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addGap(10, 10, 10)
                        .addComponent(contactNoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel13))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(guardianNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))))
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel15))
                    .addComponent(guardianAddTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(guardianOccupTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel17))
                    .addComponent(contactNoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        homeAddTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
        homeAddTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeAddTextFieldActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel26.setText("Religion");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel27.setText("Ethnic Group");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel28.setText("Entrance Test Rating");

        entranceRatingTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel29.setText("Present Address");

        presAddTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
        presAddTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presAddTextFieldActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel11.setText("Name of School Last Attended");

        schoolLastAttTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel19.setText("ID No.");

        idNumTextField.setFont(new java.awt.Font("Tahoma", 1, 12));
        idNumTextField.setText("---");

        religionTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        ethnicGroupTextField.setFont(new java.awt.Font("Tahoma", 0, 12));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idNumTextField))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(10, 10, 10)
                                .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2)
                                .addGap(10, 10, 10)
                                .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addComponent(middleNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(10, 10, 10)
                        .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(religionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ethnicGroupTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(entranceRatingTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(schoolLastAttTextField))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(10, 10, 10)
                                    .addComponent(birthPlaceTextField))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel29)
                                    .addGap(10, 10, 10)
                                    .addComponent(presAddTextField))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(homeAddTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(idNumTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1))
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addComponent(middleNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)
                        .addComponent(religionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel27)
                        .addComponent(ethnicGroupTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel28)
                        .addComponent(entranceRatingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5))
                    .addComponent(homeAddTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel29))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(presAddTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(birthPlaceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(schoolLastAttTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 790, 460));

        saveButton.setBackground(new java.awt.Color(0, 204, 51));
        saveButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/saveIcon1.png"))); // NOI18N
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jPanel2.add(saveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 250, 120, 36));

        cancelButton.setBackground(new java.awt.Color(0, 204, 51));
        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/cancel24.png"))); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel2.add(cancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 290, 120, 36));

        pictureLabel.setBackground(new java.awt.Color(255, 255, 255));
        pictureLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        pictureLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pictureLabel.setText("PICTURE");
        pictureLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pictureLabel.setOpaque(true);
        jPanel2.add(pictureLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, 150, 150));

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
        jPanel2.add(uploadButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 180, 120, 40));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 230, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 986, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeAddTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeAddTextFieldActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_homeAddTextFieldActionPerformed

    private void presAddTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presAddTextFieldActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_presAddTextFieldActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        if (!FormValidation.validateStringField(lastNameTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Last Name", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(firstNameTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid First Name", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(middleNameTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Middle Name", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(religionTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Religion", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(ethnicGroupTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Ethnic Group", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateNumberField(entranceRatingTextField.getText())) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Entrance Test Rating", title, JOptionPane.WARNING_MESSAGE);
        } else if (homeAddTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Home Address", title, JOptionPane.WARNING_MESSAGE);
        } else if (presAddTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Present Address", title, JOptionPane.WARNING_MESSAGE);
        } else if (birthPlaceTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Birth Place", title, JOptionPane.WARNING_MESSAGE);
        } else if (schoolLastAttTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid School Last Attended ", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateStringField(guardianNameTextField.getText())) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Guardian name", title, JOptionPane.WARNING_MESSAGE);
        } else if (guardianOccupTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Guardian Occupation", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateAddress(guardianAddTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Guardian Address", title, JOptionPane.WARNING_MESSAGE);
        } else if (!FormValidation.validateNumberField(contactNoTextField.getText().trim())) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid Contact Number", title, JOptionPane.WARNING_MESSAGE);
        } else {
            Student student = new Student();
            if (selectedStudent == null) {
                student.setStudentID(currentSchoolYear.getYearFrom());
                student.setYearAdmitted(currentSchoolYear.getYearFrom());
            } else {
                student.setStudentID(selectedStudent.getStudentID());
                student.setYearAdmitted(selectedStudent.getYearAdmitted());
            }

            String firstName = ComponentFormatter.formatString(firstNameTextField.getText().trim());
            String lastName = ComponentFormatter.formatString(lastNameTextField.getText().trim());
            String middleName = ComponentFormatter.formatString(middleNameTextField.getText().trim());
            String birthPlace = ComponentFormatter.formatString(birthPlaceTextField.getText().trim());
            String religion = ComponentFormatter.formatString(religionTextField.getText().trim());
            String ethnicGroup = ComponentFormatter.formatString(ethnicGroupTextField.getText().trim());
            Date birthDate = ComponentFormatter.formatDate(yearComboBox, monthComboBox, dayComboBox);
            String gender = genderComboBox.getSelectedItem().toString();
            String homeAdd = ComponentFormatter.formatString(homeAddTextField.getText().trim());
            String presentAdd = ComponentFormatter.formatString(presAddTextField.getText().trim());
            String lastSchoolAttended = ComponentFormatter.formatString(schoolLastAttTextField.getText().trim());
            int entranceTestRating = Integer.parseInt(entranceRatingTextField.getText());
            String guardianName = ComponentFormatter.formatString(guardianNameTextField.getText().trim());
            String guardianOccupation = ComponentFormatter.formatString(guardianOccupTextField.getText().trim());
            String guardianAddress = ComponentFormatter.formatString(guardianAddTextField.getText().trim());
            String contactNumber = contactNoTextField.getText().trim();

            Guardian guardian = new Guardian();
            if (selectedStudent != null) {
                guardian.setGuardianID(selectedStudent.getGuardian().getGuardianID());
            }

            guardian.setName(guardianName);
            guardian.setContactNo(contactNumber);
            guardian.setAddress(guardianAddress);
            guardian.setOccupation(guardianOccupation);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setMiddleName(middleName);
            student.setPOB(birthPlace);
            student.setReligion(religion);
            student.setEthnicGroup(ethnicGroup);
            student.setPresentAdd(presentAdd);
            student.setHomeAdd(homeAdd);
            student.setDOB(birthDate);
            student.setGender(gender);
            student.setLastSchoolAtt(lastSchoolAttended);
            student.setEntranceTestRating(entranceTestRating);
            student.setGuardian(guardian);

            if (obj != null && file != null) {
                obj = file;
                student.setPicture(obj);
            }
            student.setPicture(obj);
            saveStudent(student);
        }

}//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        selectedStudent = null;
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            GuardianListDialog guardian = new GuardianListDialog(parentFrame, true);
            guardian.setVisible(true);
            Guardian selectedGuardian = guardian.getSelectedGuardian();
            if (selectedGuardian != null) {
                guardianNameTextField.setText(selectedGuardian.getName());
                guardianOccupTextField.setText(selectedGuardian.getOccupation());
                guardianAddTextField.setText(selectedGuardian.getAddress());
                contactNoTextField.setText(selectedGuardian.getContactNo());
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(StudentDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void monthComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_monthComboBoxItemStateChanged
        // TODO add your handling code here:
        //if (monthComboBox.getItemCount() > 0) {
            ComponentFormatter.formatDateComboBox(yearComboBox, monthComboBox, dayComboBox);
       // }
    }//GEN-LAST:event_monthComboBoxItemStateChanged

    private void yearComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_yearComboBoxItemStateChanged
        // TODO add your handling code here:
       // if (yearComboBox.getItemCount() > 0) {
            ComponentFormatter.formatDateComboBox(yearComboBox, monthComboBox, dayComboBox);
        //}
    }//GEN-LAST:event_yearComboBoxItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField birthPlaceTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField contactNoTextField;
    private javax.swing.JComboBox dayComboBox;
    private javax.swing.JTextField entranceRatingTextField;
    private javax.swing.JTextField ethnicGroupTextField;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JComboBox genderComboBox;
    private javax.swing.JTextField guardianAddTextField;
    private javax.swing.JTextField guardianNameTextField;
    private javax.swing.JTextField guardianOccupTextField;
    private javax.swing.JTextField homeAddTextField;
    private javax.swing.JLabel idNumTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JTextField middleNameTextField;
    private javax.swing.JComboBox monthComboBox;
    private javax.swing.JLabel pictureLabel;
    private javax.swing.JTextField presAddTextField;
    private javax.swing.JTextField religionTextField;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField schoolLastAttTextField;
    private javax.swing.JButton uploadButton;
    private javax.swing.JComboBox yearComboBox;
    // End of variables declaration//GEN-END:variables
}

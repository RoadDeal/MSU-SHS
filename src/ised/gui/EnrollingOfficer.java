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
import ised.model.Admission;
import ised.model.Enrollment;
import ised.model.UserAccount;
import ised.model.SchoolYear;
import ised.model.Student;
import ised.service.implementation.AdmissionServiceImpl;
import ised.service.implementation.EnrollmentServiceImpl;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.interfaces.AdmissionService;
import ised.service.interfaces.EnrollmentService;
import ised.service.interfaces.SchoolYearService;
import ised.tools.ComponentFormatter;
import ised.tools.Theme;
import ised.tools.ExceptionHandler;
import ised.tools.TimeRunnableObject;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ABDUL
 */
public final class EnrollingOfficer extends javax.swing.JFrame {

    LogIn parentFrame;
    UserAccount user;
    private List<Student> studentList;
    private SchoolYear currentSchoolYear;
    private DefaultTableModel studentTableModel;
    private int selectedYearLevel;
    private String searchText;
    private Student selectedStudent;
    private int selectedStudentIndex;
    private AdmissionService admissionService;
    private EnrollmentService enrollmentService;
    private SchoolYearService schoolYearService;
    private Lock lockObject = new ReentrantLock(true);
    private ExecutorService runner;
    private TimeRunnableObject timeObject;
    private Calendar currentDate;

    /** Creates new form Registrar */
    public EnrollingOfficer(LogIn parentFrame, UserAccount user) throws ExceptionHandler {
        initComponents();
        //Theme.setDefaultLookAndFeel();
        this.parentFrame = parentFrame;
        this.user = user;
        studentTableModel = (DefaultTableModel) studentTable.getModel();
        admissionService = new AdmissionServiceImpl();
        enrollmentService = new EnrollmentServiceImpl();
        schoolYearService = new SchoolYearServiceImpl();
        currentSchoolYear = schoolYearService.getCurrentSchoolYear();
        displayUserInfo();
        displayStudentList();
        checkEnrollmentDate();

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

    public void checkEnrollmentDate() throws ExceptionHandler {
        if (currentSchoolYear.getEnrollmentBegin() == null) {
            noteTextField.setText("Note: Enrollment period is not set for this school year. Please contact the administrator to set the enrollment date");
            disableComponents();
        } else if (currentSchoolYear.getEnrollmentBegin().after(currentDate.getTime())) {
            noteTextField.setText("Note: Enrollment period is not started. Enrollment period begins on "
                    + String.format("%1$ta, %1$tb %1$te", currentSchoolYear.getEnrollmentBegin()) + ".");
            disableComponents();
        } else if (currentSchoolYear.getEnrollmentEnd() == null) {
            noteTextField.setText("Warning: Enrollment end date is not set for this school year. Please contact the administrator to set the enrollment end date");
        } else if (currentSchoolYear.getEnrollmentEnd().before(currentDate.getTime())) {
            noteTextField.setText("Note: Enrollment period already ends on "
                    + String.format("%1$ta, %1$tb %1$te", currentSchoolYear.getEnrollmentEnd())
                    + ". You can no longer enroll students for this school year.");
            disableComponents();
        }
    }

    public void disableComponents() {
        searchLabel.setEnabled(false);
        searchTextField.setEnabled(false);
        searchButton.setEnabled(false);
        yearLevelLabel.setEnabled(false);
        yearLevelComboBox.setEnabled(false);
        studentTable.setEnabled(false);
        pictureLabel.setEnabled(false);
        studentProfileTable.setEnabled(false);
        enrollButton.setEnabled(false);
        cancelEnrollButton.setEnabled(false);
    }

    public void displayStudentList(List<Student> studentList) throws ExceptionHandler {
        ComponentFormatter.clearTable(studentTableModel);
        for (Student student : studentList) {
            studentTableModel.addRow(new Object[]{student.getStudentID(), student.getFullName(), isEnrolled(student) ? "Enrolled" : ""});
        }
    }

    public boolean isEnrolled(Student student) throws ExceptionHandler {
        return enrollmentService.isStudentEnrolled(currentSchoolYear.getSchoolYearID(), student.getStudentID());
    }

    public void displayStudentList() throws ExceptionHandler {
        selectedYearLevel = yearLevelComboBox.getSelectedIndex();
        if (selectedYearLevel == 0) {
            ComponentFormatter.clearTable(studentTableModel);
            return;
        } else {
            studentList = admissionService.getAdmittedStudentsList(currentSchoolYear.getSchoolYearID(), selectedYearLevel);
        }
        displayStudentList(studentList);
    }

    public void displayStudentInfo(Student student, int selectedStudentIndex) throws ExceptionHandler {
        if (student != null) {
            selectedStudent = student;
            Admission admission = admissionService.getAdmission(student.getStudentID(), currentSchoolYear.getSchoolYearID());
            this.selectedStudentIndex = selectedStudentIndex;
            studentProfileTable.setValueAt(" " + String.valueOf(student.getStudentID()), 0, 1);
            studentProfileTable.setValueAt(" " + student.getFullName(), 1, 1);
            studentProfileTable.setValueAt(" " + student.getGender(), 2, 1);
            studentProfileTable.setValueAt(" " + admission.getYearLevelAdmitted(), 3, 1);
            studentProfileTable.setValueAt(" " + admission.getSchoolYear(), 4, 1);
            studentProfileTable.setValueAt(ComponentFormatter.formatMonthDateYear(student.getDOB()).toString(), 5, 1);
            studentProfileTable.setValueAt(" " + student.getPOB(), 6, 1);
            studentProfileTable.setValueAt(" " + student.getGender(), 7, 1);
            studentProfileTable.setValueAt(" " + student.getEthnicGroup(), 8, 1);
            studentProfileTable.setValueAt(" " + student.getReligion(), 9, 1);
            studentProfileTable.setValueAt(" " + student.getHomeAdd(), 10, 1);
            studentProfileTable.setValueAt(" " + student.getPresentAdd(), 11, 1);

            if (student.getPicture() != null) {
                Object obj = student.getPicture();
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
            selectedStudent = null;
            this.selectedStudentIndex = selectedStudentIndex;
            for (int i = 0; i <= 11; i++) {
                studentProfileTable.setValueAt(null, i, 1);
            }
            pictureLabel.setIcon(null);
            pictureLabel.setText("PICTURE");
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
        updateButton = new javax.swing.JButton();
        logOutButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        jPanel38 = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        searchLabel = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();
        yearLevelComboBox = new javax.swing.JComboBox();
        yearLevelLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        studentProfileTable = new javax.swing.JTable();
        pictureLabel = new javax.swing.JLabel();
        enrollButton = new javax.swing.JButton();
        cancelEnrollButton = new javax.swing.JButton();
        noteTextField = new javax.swing.JLabel();
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
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "ENROLL STUDENT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel22.setBackground(new java.awt.Color(0, 204, 51));
        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        studentTable.setAutoCreateRowSorter(true);
        studentTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Number", "Name", "Remarks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        studentTable.setDoubleBuffered(true);
        studentTable.setDragEnabled(true);
        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                studentTableMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                studentTableMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(studentTable);
        studentTable.getColumnModel().getColumn(0).setMinWidth(70);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        studentTable.getColumnModel().getColumn(0).setMaxWidth(70);
        studentTable.getColumnModel().getColumn(2).setMinWidth(70);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(70);
        studentTable.getColumnModel().getColumn(2).setMaxWidth(70);

        jPanel22.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 92, 330, 390));

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
        jPanel38.add(searchTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 220, 20));

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
        jPanel38.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 40, 20));

        jPanel22.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 328, 40));

        yearLevelComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        yearLevelComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1st year", "2nd year", "3rd year", "4th year" }));
        yearLevelComboBox.setToolTipText("");
        yearLevelComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        yearLevelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearLevelComboBoxActionPerformed(evt);
            }
        });
        jPanel22.add(yearLevelComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 86, -1));

        yearLevelLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        yearLevelLabel.setText("Year Level:");
        jPanel22.add(yearLevelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 20));

        jPanel2.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 370, 490));

        jPanel3.setBackground(new java.awt.Color(0, 204, 51));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        studentProfileTable.setFont(new java.awt.Font("Tahoma", 1, 12));
        studentProfileTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {" ID NUMBER", null},
                {" NAME", null},
                {" GENDER", null},
                {" YEAR LEVEL ADMITTED", null},
                {" SCHOOL YEAR ADMITTED", null},
                {" BIRTH PLACE", null},
                {" BIRTH DATE", null},
                {" GENDER", null},
                {" ETHNIC GROUP", null},
                {" RELIGION", null},
                {" HOME ADDRESS", null},
                {" PRESENT ADDRESS", null}
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
        studentProfileTable.setRowHeight(22);
        studentProfileTable.setSelectionBackground(new java.awt.Color(255, 255, 255));
        studentProfileTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        studentProfileTable.setShowVerticalLines(false);
        jPanel3.add(studentProfileTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 550, -1));

        pictureLabel.setBackground(new java.awt.Color(255, 255, 255));
        pictureLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        pictureLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pictureLabel.setText("PICTURE");
        pictureLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pictureLabel.setOpaque(true);
        jPanel3.add(pictureLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 140, 140));

        enrollButton.setFont(new java.awt.Font("Tahoma", 1, 14));
        enrollButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/enrolled.png"))); // NOI18N
        enrollButton.setText("ENROLL");
        enrollButton.setEnabled(false);
        enrollButton.setOpaque(false);
        enrollButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enrollButtonActionPerformed(evt);
            }
        });
        jPanel3.add(enrollButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 300, 140, 40));

        cancelEnrollButton.setFont(new java.awt.Font("Tahoma", 1, 14));
        cancelEnrollButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/cancel24.png"))); // NOI18N
        cancelEnrollButton.setText("CANCEL ENROLL");
        cancelEnrollButton.setEnabled(false);
        cancelEnrollButton.setOpaque(false);
        cancelEnrollButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEnrollButtonActionPerformed(evt);
            }
        });
        jPanel3.add(cancelEnrollButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 300, -1, 40));

        noteTextField.setFont(new java.awt.Font("Tahoma", 1, 11));
        noteTextField.setForeground(new java.awt.Color(255, 0, 0));
        jPanel3.add(noteTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 720, 20));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 740, 380));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 1170, 530));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 1250, 540));

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

    private void studentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMouseClicked
        // TODO add your handling code here:
        try {
            int index = studentTable.getSelectedRow();
            if (index >= 0) {
                Student student = studentList.get(index);
                //selectedStudent = student;
                updateButtons(enrollmentService.isStudentEnrolled(currentSchoolYear.getSchoolYearID(), student.getStudentID()));
                if (selectedStudent != null && selectedStudentIndex == index) {
                    displayStudentInfo(null, index);
                } else {
                    displayStudentInfo(student, index);
                }
            }
        } catch (ExceptionHandler ex) {
        }
}//GEN-LAST:event_studentTableMouseClicked

    private void studentTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_studentTableMouseEntered

    private void studentTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMousePressed
}//GEN-LAST:event_studentTableMousePressed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        try {
            selectedYearLevel = yearLevelComboBox.getSelectedIndex();
            searchText = searchTextField.getText();
            studentList = admissionService.getAdmittedStudentsList(currentSchoolYear.getSchoolYearID(), searchText, selectedYearLevel);
            displayStudentList(studentList);
            searchTextField.setText("");
        } catch (ExceptionHandler ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid search", "Search", JOptionPane.WARNING_MESSAGE);
        }
}//GEN-LAST:event_searchButtonActionPerformed

    private void yearLevelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearLevelComboBoxActionPerformed
        // TODO add your handling code here:
        try {
            displayStudentList();
            searchTextField.setText("");
        } catch (ExceptionHandler ex) {
        }
}//GEN-LAST:event_yearLevelComboBoxActionPerformed

    private void cancelEnrollButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEnrollButtonActionPerformed
        // TODO add your handling code here:
        int answer = javax.swing.JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to cancel enrollment for "
                + selectedStudent.getFullName() + "?", "Cancel Enroll", javax.swing.JOptionPane.YES_NO_OPTION);
        if (answer == javax.swing.JOptionPane.YES_OPTION) {
            try {
                if (enrollmentService.isStudentEnrolled(currentSchoolYear.getSchoolYearID(), selectedStudent.getStudentID())) {
                    Enrollment enrollment = enrollmentService.getEnrollment(selectedStudent.getStudentID(), currentSchoolYear.getSchoolYearID());
                    if (!enrollmentService.isAssignedToSection(currentSchoolYear.getSchoolYearID(), enrollment.getEnrollmentID())) {
                        enrollmentService.cancelEnroll(enrollment.getEnrollmentID());
                        JOptionPane.showMessageDialog(parentFrame, "Enrollment for " + selectedStudent.getFullName()
                                + " has successfully cancelled", "Enroll Student", JOptionPane.INFORMATION_MESSAGE);
                        updateButtons(false);
                        displayStudentList();
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Enrollment can't be cancelled because the student is already assigned to section", "Enroll Student", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (ExceptionHandler ex) {
            }
        }
    }//GEN-LAST:event_cancelEnrollButtonActionPerformed

    private void enrollButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enrollButtonActionPerformed
        // TODO add your handling code here:
        try {
            if (selectedStudent != null) {
                if (!enrollmentService.isStudentEnrolled(currentSchoolYear.getSchoolYearID(), selectedStudent.getStudentID())) {
                    Admission admission = admissionService.getAdmission(selectedStudent.getStudentID(), currentSchoolYear.getSchoolYearID());
                    enrollmentService.enrollStudent(currentSchoolYear.getSchoolYearID(), admission.getAdmissionID());
                    JOptionPane.showMessageDialog(this, selectedStudent.getFullName()
                            + " has successfully enrolled", "Enroll Student", JOptionPane.INFORMATION_MESSAGE);
                    displayStudentList();
                    updateButtons(true);
                }
            }
        } catch (ExceptionHandler ex) {
        }
    }//GEN-LAST:event_enrollButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        updateAccount();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void searchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseClicked
        // TODO add your handling code here:
        searchTextField.setText("");
    }//GEN-LAST:event_searchTextFieldMouseClicked

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

    private void updateButtons(boolean flag) {
        enrollButton.setEnabled(!flag);
        cancelEnrollButton.setEnabled(flag);
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
    private javax.swing.JButton cancelEnrollButton;
    private javax.swing.JLabel dateTextField;
    private javax.swing.JButton enrollButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton logOutButton;
    private javax.swing.JLabel noteTextField;
    private javax.swing.JLabel pictureLabel;
    private javax.swing.JLabel schoolYearTextField;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTable studentProfileTable;
    private javax.swing.JTable studentTable;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel userNameTextField;
    private javax.swing.JComboBox yearLevelComboBox;
    private javax.swing.JLabel yearLevelLabel;
    // End of variables declaration//GEN-END:variables
}

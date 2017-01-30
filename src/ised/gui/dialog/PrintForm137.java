/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PrintForm137.java
 *
 * Created on Mar 5, 2011, 1:26:47 AM
 */
package ised.gui.dialog;

import ised.model.Enrollment;
import ised.model.Student;
import ised.model.StudentSubject;
import ised.model.SummerStudent;
import ised.service.implementation.EnrollmentServiceImpl;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.implementation.StudentServiceImpl;
import ised.service.implementation.StudentSubjectServiceImpl;
import ised.service.implementation.SummerStudentServiceImpl;
import ised.service.interfaces.EnrollmentService;
import ised.service.interfaces.SchoolYearService;
import ised.service.interfaces.StudentService;
import ised.service.interfaces.StudentSubjectService;
import ised.service.interfaces.SummerStudentService;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import ised.tools.Printer;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yanix_mrml
 */
public class PrintForm137 extends javax.swing.JDialog {

    private EnrollmentService enrollmentService;
    private StudentSubjectService studentSubjectService;
    private SummerStudentService summerStudentService;
    private SchoolYearService schoolYearService;
    private DefaultTableModel firstYearGradesTableModel;
    private DefaultTableModel firstYearSummerGradesTableModel;
    private DefaultTableModel secondYearGradesTableModel;
    private DefaultTableModel secondYearSummerGradesTableModel;
    private DefaultTableModel thirdYearGradesTableModel;
    private DefaultTableModel thirdYearSummerGradesTableModel;
    private DefaultTableModel fourthYearGradesTableModel;
    private DefaultTableModel fourthYearSummerGradesTableModel;
    private Student student;
    private Printer printer;

    /** Creates new form PrintForm137 */
    public PrintForm137(javax.swing.JFrame parentFrame, boolean modal, Student student) throws ExceptionHandler {
        super(parentFrame, modal);
        initComponents();
        this.setLocationRelativeTo(parentFrame);
        this.student = student;
        enrollmentService = new EnrollmentServiceImpl();
        studentSubjectService = new StudentSubjectServiceImpl();
        summerStudentService = new SummerStudentServiceImpl();
        schoolYearService = new SchoolYearServiceImpl();
        firstYearGradesTableModel = (DefaultTableModel) firstYearGradesTable.getModel();
        firstYearSummerGradesTableModel = (DefaultTableModel) firstYearSummerGradesTable.getModel();
        secondYearGradesTableModel = (DefaultTableModel) secondYearGradesTable.getModel();
        secondYearSummerGradesTableModel = (DefaultTableModel) secondYearSummerGradesTable.getModel();
        thirdYearGradesTableModel = (DefaultTableModel) thirdYearGradesTable.getModel();
        thirdYearSummerGradesTableModel = (DefaultTableModel) thirdYearSummerGradesTable.getModel();
        fourthYearGradesTableModel = (DefaultTableModel) fourthYearGradesTable.getModel();
        fourthYearSummerGradesTableModel = (DefaultTableModel) fourthYearSummerGradesTable.getModel();
        display();
    }

    private void display() throws ExceptionHandler {
        idNum.setText(student.getStudentID() + "");
        entranceExamRating.setText(student.getEntranceTestRating() + "");
        name.setText(student.getFullName());
        sex.setText(student.getGender());
        birthDate.setText(ComponentFormatter.formatMonthDateYear(student.getDOB()).toString());
        birthPlace.setText(student.getPOB());
        guardian.setText(student.getGuardian().getName());
        occupation.setText(student.getGuardian().getOccupation());
        address.setText(student.getHomeAdd());
        lastSchoolAttended.setText(student.getLastSchoolAtt());
        displayFirstYearGrades();
        displaySecondYearGrades();
        displayThirdYearGrades();
        displayFourthYearGrades();
        name2.setText(student.getFullName());
        Calendar currentDate = schoolYearService.getServerCurrentDate();
        date.setText(String.format("%1$tB %1$td, %1$tY", currentDate));

    }

    private void displayFirstYearGrades() throws ExceptionHandler {
        double totalUnits = 0;
        ComponentFormatter.clearTable(firstYearGradesTableModel);
        Enrollment enrollment = enrollmentService.getEnrollmentByYearLevel(student.getStudentID(), 1);
        if (enrollment != null) {
            List<StudentSubject> firstYearGrades = studentSubjectService.getStudentSubjectList(enrollment.getEnrollmentID());
            for (StudentSubject subject : firstYearGrades) {
                firstYearGradesTableModel.addRow(new Object[]{subject.getSubject().getSubjectCode(),
                            subject.getFinalGrade(), getActionTaken(subject.getFinalGrade()), subject.getSubject().getUnits()});
                totalUnits += subject.getSubject().getUnits();
            }
            firstYearSY.setText(enrollment.getSchoolYear() + "");
            firstYearTotalUnits.setText(totalUnits + "");
            firstYearAverage.setText(enrollment.getAverage() + "");
            displaySummerGrades(enrollment.getEnrollmentID(), firstYearSummerGradesTableModel);
        }
    }

    private void displaySecondYearGrades() throws ExceptionHandler {
        double totalUnits = 0;
        ComponentFormatter.clearTable(secondYearGradesTableModel);
        Enrollment enrollment = enrollmentService.getEnrollmentByYearLevel(student.getStudentID(), 2);
        if (enrollment != null) {
            List<StudentSubject> secondYearGrades = studentSubjectService.getStudentSubjectList(enrollment.getEnrollmentID());
            for (StudentSubject subject : secondYearGrades) {
                secondYearGradesTableModel.addRow(new Object[]{subject.getSubject().getSubjectCode(),
                            subject.getFinalGrade(), getActionTaken(subject.getFinalGrade()), subject.getSubject().getUnits()});
                totalUnits += subject.getSubject().getUnits();
            }
            secondYearSY.setText(enrollment.getSchoolYear() + "");
            secondYearTotalUnits.setText(totalUnits + "");
            secondYearAverage.setText(enrollment.getAverage() + "");
            displaySummerGrades(enrollment.getEnrollmentID(), secondYearSummerGradesTableModel);
        }
    }

    private void displayThirdYearGrades() throws ExceptionHandler {
        double totalUnits = 0;
        ComponentFormatter.clearTable(thirdYearGradesTableModel);
        Enrollment enrollment = enrollmentService.getEnrollmentByYearLevel(student.getStudentID(), 3);
        if (enrollment != null) {
            List<StudentSubject> thirdYearGrades = studentSubjectService.getStudentSubjectList(enrollment.getEnrollmentID());
            for (StudentSubject subject : thirdYearGrades) {
                thirdYearGradesTableModel.addRow(new Object[]{subject.getSubject().getSubjectCode(),
                            subject.getFinalGrade(), getActionTaken(subject.getFinalGrade()), subject.getSubject().getUnits()});
                totalUnits += subject.getSubject().getUnits();
            }
            thirdYearSY.setText(enrollment.getSchoolYear() + "");
            thirdYearTotalUnits.setText(totalUnits + "");
            thirdYearAverage.setText(enrollment.getAverage() + "");
            displaySummerGrades(enrollment.getEnrollmentID(), thirdYearSummerGradesTableModel);
        }
    }

    private void displayFourthYearGrades() throws ExceptionHandler {
        double totalUnits = 0;
        ComponentFormatter.clearTable(fourthYearGradesTableModel);
        Enrollment enrollment = enrollmentService.getEnrollmentByYearLevel(student.getStudentID(), 4);
        if (enrollment != null) {
            List<StudentSubject> fourthYearGrades = studentSubjectService.getStudentSubjectList(enrollment.getEnrollmentID());
            for (StudentSubject subject : fourthYearGrades) {
                fourthYearGradesTableModel.addRow(new Object[]{subject.getSubject().getSubjectCode(),
                            subject.getFinalGrade(), getActionTaken(subject.getFinalGrade()), subject.getSubject().getUnits()});
                totalUnits += subject.getSubject().getUnits();
            }
            fourthYearSY.setText(enrollment.getSchoolYear() + "");
            fourthYearTotalUnits.setText(totalUnits + "");
            fourthYearAverage.setText(enrollment.getAverage() + "");
            displaySummerGrades(enrollment.getEnrollmentID(), fourthYearSummerGradesTableModel);
        }
    }

    private void displaySummerGrades(int enrollmentID, DefaultTableModel tableModel) throws ExceptionHandler {
        List<SummerStudent> summerGrades = summerStudentService.getSubjectList(enrollmentID);
        ComponentFormatter.clearTable(tableModel);
        for (SummerStudent subject : summerGrades) {
            tableModel.addRow(new Object[]{subject.getStudentSubject().getSubject().getSubjectCode(),
                        subject.getCompleteGrade(), getActionTaken(subject.getCompleteGrade()), subject.getStudentSubject().getSubject().getUnits()});
        }
    }

    private String getActionTaken(double grade) {
        if (grade <= 3) {
            return "Passed";
        } else {
            return "Failed";
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        printablePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        firstYearGradesTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        birthDate = new javax.swing.JLabel();
        guardian = new javax.swing.JLabel();
        address = new javax.swing.JLabel();
        lastSchoolAttended = new javax.swing.JLabel();
        sex = new javax.swing.JLabel();
        birthPlace = new javax.swing.JLabel();
        occupation = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        idNum = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        reportsHeaderLabel = new javax.swing.JLabel();
        entranceExamRating = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        firstYearSY = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        secondYearGradesTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        secondYearSY = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        firstYearAverage = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        firstYearTotalUnits = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        secondYearAverage = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        secondYearTotalUnits = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        firstYearSummerGradesTable = new javax.swing.JTable();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        secondYearSummerGradesTable = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        thirdYearGradesTable = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        fourthYearGradesTable = new javax.swing.JTable();
        jLabel46 = new javax.swing.JLabel();
        thirdYearAverage = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        thirdYearTotalUnits = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        thirdYearSummerGradesTable = new javax.swing.JTable();
        jLabel51 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        fourthYearSummerGradesTable = new javax.swing.JTable();
        jLabel52 = new javax.swing.JLabel();
        fourthYearAverage = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        fourthYearTotalUnits = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        name2 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        thirdYearSY = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        fourthYearSY = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Print Preview Reports");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        printablePanel.setBackground(new java.awt.Color(255, 255, 255));
        printablePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        firstYearGradesTable.setFont(new java.awt.Font("Tahoma", 1, 11));
        firstYearGradesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SUBJECTS", "Final Grade", "Action Taken", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        firstYearGradesTable.setRowHeight(17);
        firstYearGradesTable.setRowSelectionAllowed(false);
        jScrollPane2.setViewportView(firstYearGradesTable);
        firstYearGradesTable.getColumnModel().getColumn(0).setResizable(false);
        firstYearGradesTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        firstYearGradesTable.getColumnModel().getColumn(1).setResizable(false);
        firstYearGradesTable.getColumnModel().getColumn(2).setResizable(false);
        firstYearGradesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        firstYearGradesTable.getColumnModel().getColumn(3).setResizable(false);
        firstYearGradesTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        printablePanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 327, 240));
        printablePanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 670, 10));
        printablePanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 670, 10));

        jLabel3.setText("Form 137-A");
        printablePanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel4.setText("Extract");
        printablePanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 12));
        jLabel5.setText("STUDENT'S PERMANENT RECORD");
        printablePanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 210, -1));

        jPanel4.setOpaque(false);

        jLabel6.setText("Name:");

        name.setFont(new java.awt.Font("Tahoma", 1, 11));
        name.setText("---");

        jLabel8.setText("Birthdate:");

        jLabel9.setText("Parent/Guardian:");

        jLabel10.setText("Address:");

        jLabel11.setText("Elementary Course Completed At:");

        jLabel12.setText("Occupation:");

        jLabel13.setText("Sex:");

        jLabel15.setText("Birthplace:");

        birthDate.setFont(new java.awt.Font("Tahoma", 1, 11));
        birthDate.setText("---");

        guardian.setFont(new java.awt.Font("Tahoma", 1, 11));
        guardian.setText("---");

        address.setFont(new java.awt.Font("Tahoma", 1, 11));
        address.setText("---");

        lastSchoolAttended.setFont(new java.awt.Font("Tahoma", 1, 11));
        lastSchoolAttended.setText("---");

        sex.setFont(new java.awt.Font("Tahoma", 1, 11));
        sex.setText("---");

        birthPlace.setFont(new java.awt.Font("Tahoma", 1, 11));
        birthPlace.setText("---");

        occupation.setFont(new java.awt.Font("Tahoma", 1, 11));
        occupation.setText("---");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guardian, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(birthDate, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(birthPlace, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(sex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(81, 81, 81))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(occupation, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(54, 54, 54))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(address, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lastSchoolAttended, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(name)
                    .addComponent(jLabel13)
                    .addComponent(sex))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel15)
                    .addComponent(birthDate)
                    .addComponent(birthPlace))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addComponent(guardian)
                    .addComponent(occupation, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(address))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lastSchoolAttended))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        printablePanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 670, 100));

        jLabel16.setText("Student's ID No.");
        printablePanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, -1));

        idNum.setFont(new java.awt.Font("Tahoma", 1, 11));
        idNum.setText("------");
        printablePanel.add(idNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        jLabel18.setText("Entrance Exam Rating:");
        printablePanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, -1, -1));

        reportsHeaderLabel.setFont(new java.awt.Font("Arial", 1, 14));
        reportsHeaderLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reportsHeaderLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/ised_header.jpg"))); // NOI18N
        reportsHeaderLabel.setText("Mindanao State University");
        printablePanel.add(reportsHeaderLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 376, -1));

        entranceExamRating.setFont(new java.awt.Font("Tahoma", 1, 11));
        entranceExamRating.setText("--");
        printablePanel.add(entranceExamRating, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, -1, -1));

        jPanel5.setOpaque(false);

        jLabel28.setText("Curriccular Year:");

        jLabel29.setText("SY");

        jLabel64.setText("First Year");

        firstYearSY.setFont(new java.awt.Font("Tahoma", 1, 11));
        firstYearSY.setText("---");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(firstYearSY)
                .addGap(56, 56, 56))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel64)
                    .addComponent(firstYearSY))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        printablePanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 320, 20));

        secondYearGradesTable.setFont(new java.awt.Font("Tahoma", 1, 12));
        secondYearGradesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SUBJECTS", "Final Grade", "Action Taken", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        secondYearGradesTable.setRowHeight(17);
        secondYearGradesTable.setRowSelectionAllowed(false);
        jScrollPane3.setViewportView(secondYearGradesTable);
        secondYearGradesTable.getColumnModel().getColumn(0).setResizable(false);
        secondYearGradesTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        secondYearGradesTable.getColumnModel().getColumn(1).setResizable(false);
        secondYearGradesTable.getColumnModel().getColumn(2).setResizable(false);
        secondYearGradesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        secondYearGradesTable.getColumnModel().getColumn(3).setResizable(false);
        secondYearGradesTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        printablePanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, 327, 240));

        jPanel6.setOpaque(false);

        jLabel30.setText("Curriccular Year:");

        jLabel31.setText("SY");

        jLabel66.setText("Second Year");

        secondYearSY.setFont(new java.awt.Font("Tahoma", 1, 11));
        secondYearSY.setText("---");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(secondYearSY)
                .addGap(56, 56, 56))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel66)
                    .addComponent(secondYearSY))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        printablePanel.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, 320, 20));

        jLabel32.setText("General Average:");
        printablePanel.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, -1, -1));

        firstYearAverage.setFont(new java.awt.Font("Tahoma", 1, 11));
        firstYearAverage.setText("---");
        printablePanel.add(firstYearAverage, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 490, -1, -1));

        jLabel34.setText("Total Units:");
        printablePanel.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 490, -1, -1));

        firstYearTotalUnits.setFont(new java.awt.Font("Tahoma", 1, 11));
        firstYearTotalUnits.setText("---");
        printablePanel.add(firstYearTotalUnits, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 490, -1, -1));

        jLabel36.setText("General Average:");
        printablePanel.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 490, -1, -1));

        secondYearAverage.setFont(new java.awt.Font("Tahoma", 1, 11));
        secondYearAverage.setText("---");
        printablePanel.add(secondYearAverage, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 490, -1, -1));

        jLabel38.setText("Total Units:");
        printablePanel.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 490, -1, -1));

        secondYearTotalUnits.setFont(new java.awt.Font("Tahoma", 1, 11));
        secondYearTotalUnits.setText("---");
        printablePanel.add(secondYearTotalUnits, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 490, -1, -1));

        firstYearSummerGradesTable.setFont(new java.awt.Font("Tahoma", 1, 12));
        firstYearSummerGradesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SUBJECTS", "Final Grade", "Action Taken", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        firstYearSummerGradesTable.setRowHeight(17);
        firstYearSummerGradesTable.setRowSelectionAllowed(false);
        jScrollPane4.setViewportView(firstYearSummerGradesTable);
        firstYearSummerGradesTable.getColumnModel().getColumn(0).setResizable(false);
        firstYearSummerGradesTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        firstYearSummerGradesTable.getColumnModel().getColumn(1).setResizable(false);
        firstYearSummerGradesTable.getColumnModel().getColumn(2).setResizable(false);
        firstYearSummerGradesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        firstYearSummerGradesTable.getColumnModel().getColumn(3).setResizable(false);
        firstYearSummerGradesTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        printablePanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 327, 64));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel40.setText("Summer");
        printablePanel.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, -1, -1));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel41.setText("Summer");
        printablePanel.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 510, -1, -1));

        secondYearSummerGradesTable.setFont(new java.awt.Font("Tahoma", 1, 12));
        secondYearSummerGradesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SUBJECTS", "Final Grade", "Action Taken", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        secondYearSummerGradesTable.setRowHeight(17);
        secondYearSummerGradesTable.setRowSelectionAllowed(false);
        jScrollPane5.setViewportView(secondYearSummerGradesTable);
        secondYearSummerGradesTable.getColumnModel().getColumn(0).setResizable(false);
        secondYearSummerGradesTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        secondYearSummerGradesTable.getColumnModel().getColumn(1).setResizable(false);
        secondYearSummerGradesTable.getColumnModel().getColumn(2).setResizable(false);
        secondYearSummerGradesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        secondYearSummerGradesTable.getColumnModel().getColumn(3).setResizable(false);
        secondYearSummerGradesTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        printablePanel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 530, 327, 64));

        jLabel42.setText("Curriccular Year:");
        printablePanel.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, -1, 10));

        jLabel43.setText("SY");
        printablePanel.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 600, -1, -1));

        thirdYearGradesTable.setFont(new java.awt.Font("Tahoma", 1, 12));
        thirdYearGradesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SUBJECTS", "Final Grade", "Action Taken", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        thirdYearGradesTable.setRowHeight(17);
        thirdYearGradesTable.setRowSelectionAllowed(false);
        jScrollPane6.setViewportView(thirdYearGradesTable);
        thirdYearGradesTable.getColumnModel().getColumn(0).setResizable(false);
        thirdYearGradesTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        thirdYearGradesTable.getColumnModel().getColumn(1).setResizable(false);
        thirdYearGradesTable.getColumnModel().getColumn(2).setResizable(false);
        thirdYearGradesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        thirdYearGradesTable.getColumnModel().getColumn(3).setResizable(false);
        thirdYearGradesTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        printablePanel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 327, 220));

        jLabel44.setText("Curriccular Year:");
        printablePanel.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 600, -1, -1));

        jLabel45.setText("SY");
        printablePanel.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 600, -1, -1));

        fourthYearGradesTable.setFont(new java.awt.Font("Tahoma", 1, 11));
        fourthYearGradesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SUBJECTS", "Final Grade", "Action Taken", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        fourthYearGradesTable.setRowHeight(17);
        fourthYearGradesTable.setRowSelectionAllowed(false);
        jScrollPane7.setViewportView(fourthYearGradesTable);
        fourthYearGradesTable.getColumnModel().getColumn(0).setResizable(false);
        fourthYearGradesTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        fourthYearGradesTable.getColumnModel().getColumn(1).setResizable(false);
        fourthYearGradesTable.getColumnModel().getColumn(2).setResizable(false);
        fourthYearGradesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        fourthYearGradesTable.getColumnModel().getColumn(3).setResizable(false);
        fourthYearGradesTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        printablePanel.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 620, 327, 220));

        jLabel46.setText("General Average:");
        printablePanel.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 850, -1, -1));

        thirdYearAverage.setFont(new java.awt.Font("Tahoma", 1, 11));
        thirdYearAverage.setText("---");
        printablePanel.add(thirdYearAverage, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 850, -1, -1));

        jLabel48.setText("Total Units:");
        printablePanel.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 850, -1, -1));

        thirdYearTotalUnits.setFont(new java.awt.Font("Tahoma", 1, 11));
        thirdYearTotalUnits.setText("---");
        printablePanel.add(thirdYearTotalUnits, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 850, -1, -1));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel50.setText("Summer");
        printablePanel.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 870, -1, -1));

        thirdYearSummerGradesTable.setFont(new java.awt.Font("Tahoma", 1, 12));
        thirdYearSummerGradesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SUBJECTS", "Final Grade", "Action Taken", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        thirdYearSummerGradesTable.setRowHeight(17);
        thirdYearSummerGradesTable.setRowSelectionAllowed(false);
        jScrollPane8.setViewportView(thirdYearSummerGradesTable);
        thirdYearSummerGradesTable.getColumnModel().getColumn(0).setResizable(false);
        thirdYearSummerGradesTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        thirdYearSummerGradesTable.getColumnModel().getColumn(1).setResizable(false);
        thirdYearSummerGradesTable.getColumnModel().getColumn(2).setResizable(false);
        thirdYearSummerGradesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        thirdYearSummerGradesTable.getColumnModel().getColumn(3).setResizable(false);
        thirdYearSummerGradesTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        printablePanel.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 890, 327, 64));

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel51.setText("Summer");
        printablePanel.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 870, -1, -1));

        fourthYearSummerGradesTable.setFont(new java.awt.Font("Tahoma", 1, 12));
        fourthYearSummerGradesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SUBJECTS", "Final Grade", "Action Taken", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        fourthYearSummerGradesTable.setRowHeight(17);
        fourthYearSummerGradesTable.setRowSelectionAllowed(false);
        jScrollPane9.setViewportView(fourthYearSummerGradesTable);
        fourthYearSummerGradesTable.getColumnModel().getColumn(0).setResizable(false);
        fourthYearSummerGradesTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        fourthYearSummerGradesTable.getColumnModel().getColumn(1).setResizable(false);
        fourthYearSummerGradesTable.getColumnModel().getColumn(2).setResizable(false);
        fourthYearSummerGradesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        fourthYearSummerGradesTable.getColumnModel().getColumn(3).setResizable(false);
        fourthYearSummerGradesTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        printablePanel.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 890, 327, 64));

        jLabel52.setText("General Average:");
        printablePanel.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 850, -1, -1));

        fourthYearAverage.setFont(new java.awt.Font("Tahoma", 1, 11));
        fourthYearAverage.setText("---");
        printablePanel.add(fourthYearAverage, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 850, -1, -1));

        jLabel54.setText("Total Units:");
        printablePanel.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 850, -1, -1));

        fourthYearTotalUnits.setFont(new java.awt.Font("Tahoma", 1, 11));
        fourthYearTotalUnits.setText("---");
        printablePanel.add(fourthYearTotalUnits, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 850, -1, -1));

        jLabel56.setText("This is to certify that this is true record of ");
        printablePanel.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 970, -1, -1));

        name2.setFont(new java.awt.Font("Tahoma", 1, 11));
        name2.setText("----");
        printablePanel.add(name2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 970, 250, -1));

        jLabel58.setText("is eligible for admission to");
        printablePanel.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 970, -1, -1));

        jLabel59.setText("1st year College.");
        printablePanel.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 970, 90, -1));

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel60.setText("MS. JOCELYN P. AMAN");
        printablePanel.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 1010, -1, -1));

        jLabel61.setText("Principal");
        printablePanel.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 1020, -1, -1));

        jLabel62.setText("Date:");
        printablePanel.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1000, -1, -1));

        date.setFont(new java.awt.Font("Tahoma", 1, 11));
        date.setText("----");
        printablePanel.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 1000, -1, -1));

        jLabel68.setText("Third Year");
        printablePanel.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 600, -1, -1));

        thirdYearSY.setFont(new java.awt.Font("Tahoma", 1, 11));
        thirdYearSY.setText("---");
        printablePanel.add(thirdYearSY, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 600, -1, -1));

        jLabel70.setText("Fourth Year");
        printablePanel.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 600, -1, -1));

        fourthYearSY.setFont(new java.awt.Font("Tahoma", 1, 11));
        fourthYearSY.setText("---");
        printablePanel.add(fourthYearSY, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 600, -1, -1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(printablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(printablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 770, 570));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 13));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/print.png"))); // NOI18N
        jButton1.setText("PRINT");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 610, 120, 40));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 13));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/cancel24.png"))); // NOI18N
        jButton2.setText("CANCEL");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 610, 120, 41));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            this.printer = new Printer(printablePanel);
            this.printer.setDocumentType("Form 137");
            this.printer.actionPerformed(evt);
        } catch (Exception e) {
            ExceptionHandler exception = new ExceptionHandler(e);
        } finally {
            dispose();
        }
}//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel address;
    private javax.swing.JLabel birthDate;
    private javax.swing.JLabel birthPlace;
    private javax.swing.JLabel date;
    private javax.swing.JLabel entranceExamRating;
    private javax.swing.JLabel firstYearAverage;
    private javax.swing.JTable firstYearGradesTable;
    private javax.swing.JLabel firstYearSY;
    private javax.swing.JTable firstYearSummerGradesTable;
    private javax.swing.JLabel firstYearTotalUnits;
    private javax.swing.JLabel fourthYearAverage;
    private javax.swing.JTable fourthYearGradesTable;
    private javax.swing.JLabel fourthYearSY;
    private javax.swing.JTable fourthYearSummerGradesTable;
    private javax.swing.JLabel fourthYearTotalUnits;
    private javax.swing.JLabel guardian;
    private javax.swing.JLabel idNum;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lastSchoolAttended;
    private javax.swing.JLabel name;
    private javax.swing.JLabel name2;
    private javax.swing.JLabel occupation;
    private javax.swing.JPanel printablePanel;
    private javax.swing.JLabel reportsHeaderLabel;
    private javax.swing.JLabel secondYearAverage;
    private javax.swing.JTable secondYearGradesTable;
    private javax.swing.JLabel secondYearSY;
    private javax.swing.JTable secondYearSummerGradesTable;
    private javax.swing.JLabel secondYearTotalUnits;
    private javax.swing.JLabel sex;
    private javax.swing.JLabel thirdYearAverage;
    private javax.swing.JTable thirdYearGradesTable;
    private javax.swing.JLabel thirdYearSY;
    private javax.swing.JTable thirdYearSummerGradesTable;
    private javax.swing.JLabel thirdYearTotalUnits;
    // End of variables declaration//GEN-END:variables
}

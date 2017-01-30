/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LogIn.java
 *
 * Created on Oct 15, 2011, 9:32:23 AM
 */
package ised.gui;

import ised.gui.dialog.ChangeDefaultPasswordDialog;
import ised.model.SchoolYear;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ised.model.UserAccount;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.implementation.UserAccountServiceImpl;
import ised.service.interfaces.SchoolYearService;
import ised.service.interfaces.UserAccountService;
import ised.tools.ExceptionHandler;
import ised.tools.Position;
import ised.tools.Theme;

/**
 *
 * @author ABDUL
 */
public class LogIn extends javax.swing.JFrame {

    private UserAccountService userAccountService;
    private UserAccount user;

    /** Creates new form LogIn */
    public LogIn(){
        Theme.setDefaultLookAndFeel();
        initComponents();
        Position.setCenter(this.getSize(), this);
        userAccountService = new UserAccountServiceImpl();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        userNameTextField = new javax.swing.JTextField();
        logInButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 168, 51), 10, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 14));
        jLabel4.setText("Username");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, -1, 20));

        userNameTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
        jPanel1.add(userNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 190, -1));

        logInButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        logInButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/accept.png"))); // NOI18N
        logInButton.setText("Log In");
        logInButton.setOpaque(false);
        logInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButtonActionPerformed(evt);
            }
        });
        jPanel1.add(logInButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 220, -1, 30));
        logInButton.getAccessibleContext().setAccessibleParent(this);

        exitButton.setBackground(new java.awt.Color(0, 204, 51));
        exitButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/delete18.png"))); // NOI18N
        exitButton.setText("Exit");
        exitButton.setOpaque(false);
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        jPanel1.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 91, 30));

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 14));
        jLabel5.setText("Password");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, -1, 30));

        passwordField.setFont(new java.awt.Font("Tahoma", 0, 12));
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });
        jPanel1.add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 190, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/LOGIN copy.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
}//GEN-LAST:event_exitButtonActionPerformed

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed
        // TODO add your handling code here:
        String userName = userNameTextField.getText();
        String password = String.copyValueOf(passwordField.getPassword());

        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty username. Please enter "
                    + "username.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty password. Please enter "
                    + "password.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            userAccountService = new UserAccountServiceImpl();
            try {
                if (!userAccountService.checkUserName(userName)) {
                    user = userAccountService.getUserAccount(userName);
                    if (user.getStatus().equalsIgnoreCase("Active")) {
                        if (user.getPassword().equals(password)) {
                            if (user.getPassword().equals("123456")) {
                                JOptionPane.showMessageDialog(this, "You are using default password. Please change your password", "Change Password", JOptionPane.INFORMATION_MESSAGE);
                                ChangeDefaultPasswordDialog changePassword = new ChangeDefaultPasswordDialog(this, true, user);
                                changePassword.setLocationRelativeTo(this);
                                changePassword.setVisible(true);
                                passwordField.setText(null);
                            } else {
                                if (user.getUserType().equals("Administrator")) {
                                    Administrator admin = new Administrator(this, user);
                                    this.setVisible(false);
                                    admin.setLocationRelativeTo(this);
                                    admin.setVisible(true);
                                }
                                if (checkCurrentSchoolYear()) {
                                    if (user.getUserType().equals("Registrar")) {
                                        Registrar registrar = new Registrar(this, user);
                                        this.setVisible(false);
                                        registrar.setLocationRelativeTo(this);
                                        registrar.setVisible(true);
                                    } else if (user.getUserType().equals("Enrolling Officer")) {
                                        EnrollingOfficer eo = new EnrollingOfficer(this, user);
                                        this.setVisible(false);
                                        eo.setLocationRelativeTo(this);
                                        eo.setVisible(true);
                                    } else if (user.getUserType().equals("Principal")) {
                                        Principal principal = new Principal(this, user);
                                        this.setVisible(false);
                                        principal.setLocationRelativeTo(this);
                                        principal.setVisible(true);
                                    } else if (user.getUserType().equals("Teacher")) {
                                        Teacher teacher = new Teacher(this, user);
                                        this.setVisible(false);
                                        teacher.setLocationRelativeTo(this);
                                        teacher.setVisible(true);
                                    } else if (user.getUserType().equals("Academic Consultant")) {
                                        AcademicConsultant ac = new AcademicConsultant(this, user);
                                        this.setVisible(false);
                                        ac.setLocationRelativeTo(this);
                                        ac.setVisible(true);
                                    }
                                }
                                userNameTextField.setText("");
                                passwordField.setText("");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid password", "Error", JOptionPane.ERROR_MESSAGE);
                            passwordField.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Account Inactive. Please Contact the Administrator", "Error", JOptionPane.WARNING_MESSAGE);
                        userNameTextField.setText("");
                        passwordField.setText("");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username.", "Error", JOptionPane.ERROR_MESSAGE);
                    userNameTextField.setText("");
                    passwordField.setText("");
                }
            } catch (ExceptionHandler ex) {
                Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_logInButtonActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
        logInButton.doClick();
    }//GEN-LAST:event_passwordFieldActionPerformed

    private boolean checkCurrentSchoolYear() throws ExceptionHandler {
        SchoolYearService schoolYearService = new SchoolYearServiceImpl();
        SchoolYear currentSchoolYear = schoolYearService.getCurrentSchoolYear();
        if (currentSchoolYear == null) {
            JOptionPane.showMessageDialog(this, "No school year is active. Please contact the administrator to set school year. ", "No School Year", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogIn().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton logInButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField userNameTextField;
    // End of variables declaration//GEN-END:variables
}

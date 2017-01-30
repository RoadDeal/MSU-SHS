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

import ised.gui.Administrator;
import ised.gui.dialog.CreateUserAccountDialog;
import ised.gui.dialog.EditUserAccountDialog;
import ised.gui.dialog.SchoolYearDialog;
import ised.gui.dialog.UpdateAccountDialog;
import ised.model.SchoolYear;
import ised.model.UserAccount;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.implementation.UserAccountServiceImpl;
import ised.service.interfaces.SchoolYearService;
import ised.service.interfaces.UserAccountService;
import ised.tools.ComponentFormatter;
import ised.tools.ExceptionHandler;
import ised.tools.Theme;
import ised.tools.TimeRunnableObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ABDUL
 */
public class Administrator extends javax.swing.JFrame {

    private LogIn parentFrame;
    private UserAccount user;
    private UserAccountService userAccountService;
    private SchoolYearService schoolYearService;
    private List<UserAccount> userAccountList;
    private List<SchoolYear> schoolYearList;
    private DefaultTableModel userAccountTableModel;
    private DefaultTableModel schoolYearTableModel;
    private String selectedUserType;
    private SchoolYear currentSchoolYear;
    private SchoolYear selectedSchoolYear;
    private Lock lockObject = new ReentrantLock(true);
    private ExecutorService runner;
    private TimeRunnableObject timeObject;

    /** Creates new form Registrar */
    public Administrator(LogIn parentFrame, UserAccount user) throws ExceptionHandler {
        initComponents();
        //Theme.setDefaultLookAndFeel();
        this.parentFrame = parentFrame;
        this.user = user;
        userAccountList = new ArrayList<UserAccount>();
        schoolYearList = new ArrayList<SchoolYear>();
        schoolYearService = new SchoolYearServiceImpl();
        userAccountService = new UserAccountServiceImpl();
        currentSchoolYear = schoolYearService.getCurrentSchoolYear();
        userAccountTableModel = (DefaultTableModel) userAccountTable.getModel();
        schoolYearTableModel = (DefaultTableModel) schoolYearTable.getModel();
        displayUserAccountList();
        displaySchoolYearList();
        displayUserInfo();

        ComponentFormatter.stripTable(schoolYearTable);
        ComponentFormatter.stripTable(userAccountTable);
    }

    public void displayUserInfo() throws ExceptionHandler {
        Calendar currentDate = schoolYearService.getServerCurrentDate();
        if (currentSchoolYear != null) {
            schoolYearTextField.setText(currentSchoolYear.toString());
        }
        dateTextField.setText(String.format("%1$tB %1$td, %1$tY", currentDate));
        userNameTextField.setText(user.getUserType());
        jLabel3.setText(user.getEmployee().getFullName());
        runner = Executors.newFixedThreadPool(1);
        timeObject = new TimeRunnableObject(lockObject, dateTextField, currentDate);
        runner.execute(timeObject);
        runner.shutdown();
    }

    private void displayUserAccountList(List<UserAccount> userAccountList) {
        ComponentFormatter.clearTable(userAccountTableModel);
        for (UserAccount userAccount : userAccountList) {
            userAccountTableModel.addRow(new Object[]{userAccount.getEmployee().getFullName(), userAccount.getUserType(),
                        userAccount.getUserName(), userAccount.getStatus()});
        }
        totalUserAccounts.setText(Integer.toString(userAccountTableModel.getRowCount()));
    }

    private void displayUserAccountList() throws ExceptionHandler {
        selectedUserType = userTypeComboBox.getSelectedItem().toString();
        if (selectedUserType.equalsIgnoreCase("All")) {
            userAccountList = userAccountService.getUserAccounts();
        } else {
            userAccountList = userAccountService.getUserAccounts(selectedUserType);
        }
        displayUserAccountList(userAccountList);
    }

    private void displaySchoolYearList() throws ExceptionHandler {
        schoolYearList = schoolYearService.getSchoolYearList();
        ComponentFormatter.clearTable(schoolYearTableModel);
        for (SchoolYear schoolYear : schoolYearList) {
            schoolYearTableModel.addRow(new Object[]{schoolYear.toString(), schoolYear.getSchoolYearStatus(),
                        schoolYear.getEnrollmentBegin() == null ? " Not Set" : ComponentFormatter.formatMonthDateYear(schoolYear.getEnrollmentBegin()),
                        schoolYear.getEnrollmentEnd() == null ? " Not Set" : ComponentFormatter.formatMonthDateYear(schoolYear.getEnrollmentEnd()),
                        schoolYear.getStartOfClasses() == null ? " Not Set" : ComponentFormatter.formatMonthDateYear(schoolYear.getStartOfClasses())});
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

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jToolBar2 = new javax.swing.JToolBar();
        updateAccountButton = new javax.swing.JButton();
        logOutButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userAccountTable = new javax.swing.JTable();
        jPanel39 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        userTypeComboBox = new javax.swing.JComboBox();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        createUserAccountButton = new javax.swing.JButton();
        editUserAccountButton = new javax.swing.JButton();
        deleteUserAccountButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        totalUserAccounts = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        schoolYearTable = new javax.swing.JTable();
        addSchoolYearButton = new javax.swing.JButton();
        editSchoolYearButton = new javax.swing.JButton();
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
        jMenu4 = new javax.swing.JMenu();

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
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

        updateAccountButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        updateAccountButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/update24.png"))); // NOI18N
        updateAccountButton.setText("UPDATE ACCOUNT");
        updateAccountButton.setFocusable(false);
        updateAccountButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        updateAccountButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        updateAccountButton.setOpaque(false);
        updateAccountButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        updateAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateAccountButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(updateAccountButton);

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

        getContentPane().add(jToolBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 0, 240, 30));

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 13));

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));

        jPanel2.setBackground(new java.awt.Color(0, 153, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        userAccountTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        userAccountTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee Name", "Account Type", "Username", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userAccountTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(userAccountTable);
        userAccountTable.getColumnModel().getColumn(1).setMinWidth(200);
        userAccountTable.getColumnModel().getColumn(1).setMaxWidth(200);
        userAccountTable.getColumnModel().getColumn(2).setMinWidth(200);
        userAccountTable.getColumnModel().getColumn(2).setMaxWidth(200);
        userAccountTable.getColumnModel().getColumn(3).setMinWidth(100);
        userAccountTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        userAccountTable.getColumnModel().getColumn(3).setMaxWidth(100);

        jPanel39.setBackground(new java.awt.Color(0, 153, 51));
        jPanel39.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel66.setText("View by User Account Type:");
        jPanel39.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 160, 20));

        userTypeComboBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        userTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Registrar", "Administrator", "Principal", "Academic Consultant", "Teacher", "Enrolling Officer" }));
        userTypeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                userTypeComboBoxItemStateChanged(evt);
            }
        });
        userTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userTypeComboBoxActionPerformed(evt);
            }
        });
        jPanel39.add(userTypeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 160, 20));

        searchTextField.setFont(new java.awt.Font("Tahoma", 0, 12));
        searchTextField.setText("Type employee name");
        searchTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTextFieldMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchTextFieldMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                searchTextFieldMouseReleased(evt);
            }
        });
        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyTyped(evt);
            }
        });
        jPanel39.add(searchTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 150, 20));

        searchButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/find.png"))); // NOI18N
        searchButton.setText("Search");
        searchButton.setOpaque(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        jPanel39.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, 90, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel7.setText("Search:");
        jPanel39.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, 20));

        jPanel3.setBackground(new java.awt.Color(0, 153, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        createUserAccountButton.setBackground(new java.awt.Color(0, 204, 51));
        createUserAccountButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        createUserAccountButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/add.png"))); // NOI18N
        createUserAccountButton.setText("Create User Account");
        createUserAccountButton.setOpaque(false);
        createUserAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createUserAccountButtonActionPerformed(evt);
            }
        });

        editUserAccountButton.setBackground(new java.awt.Color(0, 204, 51));
        editUserAccountButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        editUserAccountButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/pencil.png"))); // NOI18N
        editUserAccountButton.setText("Edit User Account");
        editUserAccountButton.setOpaque(false);
        editUserAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUserAccountButtonActionPerformed(evt);
            }
        });

        deleteUserAccountButton.setBackground(new java.awt.Color(0, 204, 51));
        deleteUserAccountButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        deleteUserAccountButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/delete.png"))); // NOI18N
        deleteUserAccountButton.setText("Delete User Account");
        deleteUserAccountButton.setOpaque(false);
        deleteUserAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserAccountButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(deleteUserAccountButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(createUserAccountButton, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editUserAccountButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createUserAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(editUserAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteUserAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setText("Total User Accounts:");

        totalUserAccounts.setFont(new java.awt.Font("Tahoma", 1, 12));
        totalUserAccounts.setText("---");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(totalUserAccounts))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
                            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 289, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalUserAccounts)
                            .addComponent(jLabel8))
                        .addGap(3, 3, 3)))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("USER ACCOUNTS", new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/user_accounts_icon.png")), jPanel1); // NOI18N

        jPanel4.setBackground(new java.awt.Color(0, 153, 51));

        jPanel5.setBackground(new java.awt.Color(0, 153, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        schoolYearTable.setAutoCreateRowSorter(true);
        schoolYearTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        schoolYearTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SCHOOL YEAR", "STATUS", "ENROLLMENT BEGIN", "ENROLLMENT END", "START OF CLASSES"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        schoolYearTable.setRowHeight(20);
        jScrollPane2.setViewportView(schoolYearTable);
        schoolYearTable.getColumnModel().getColumn(0).setMinWidth(200);
        schoolYearTable.getColumnModel().getColumn(0).setMaxWidth(200);

        addSchoolYearButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        addSchoolYearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/add.png"))); // NOI18N
        addSchoolYearButton.setText("Add School Year");
        addSchoolYearButton.setFocusable(false);
        addSchoolYearButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        addSchoolYearButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        addSchoolYearButton.setOpaque(false);
        addSchoolYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSchoolYearButtonActionPerformed(evt);
            }
        });

        editSchoolYearButton.setFont(new java.awt.Font("Tahoma", 1, 12));
        editSchoolYearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/pencil.png"))); // NOI18N
        editSchoolYearButton.setText("Edit School Year");
        editSchoolYearButton.setFocusable(false);
        editSchoolYearButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        editSchoolYearButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        editSchoolYearButton.setOpaque(false);
        editSchoolYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSchoolYearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(addSchoolYearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editSchoolYearButton)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addSchoolYearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editSchoolYearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("SCHOOL YEAR", new javax.swing.ImageIcon(getClass().getResource("/ised/resources/images/school_year_icon.png")), jPanel4); // NOI18N

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1250, 560));
        jTabbedPane1.getAccessibleContext().setAccessibleName("USER ACCOUNTS");

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
        jLabel18.setText("private void updateAccount() {         UpdateAccountDialog updateAccount = new UpdateAccountDialog(this, true, user);         updateAccount.setVisible(true);         user = updateAccount.getUserAccount();         updateAccount.dispose();         userNameTextField.setText(user.getUserName());     }");
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

        jMenu4.setText("Help");
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu4);

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

    private void userTypeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_userTypeComboBoxItemStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_userTypeComboBoxItemStateChanged

    private void searchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseClicked
        // TODO add your handling code here:
        searchTextField.setText("");
}//GEN-LAST:event_searchTextFieldMouseClicked

    private void searchTextFieldMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseExited
        // TODO add your handling code here:
        String in = searchTextField.getText();
        if (in.equals("")) {
            searchTextField.setText("Type employee name");
        }
}//GEN-LAST:event_searchTextFieldMouseExited

    private void searchTextFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseReleased
        // TODO add your handling code here:
}//GEN-LAST:event_searchTextFieldMouseReleased

    private void searchTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyTyped
        // TODO add your handling code here:
}//GEN-LAST:event_searchTextFieldKeyTyped

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        try {
            String searchText = searchTextField.getText();
            userAccountList = userAccountService.getUserAccounts(selectedUserType, searchText);
            if (userAccountList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No result for " + searchText, "Search", JOptionPane.INFORMATION_MESSAGE);
            } else if (!userAccountList.isEmpty() && !searchText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "There are " + userAccountList.size() + " result(s) for " + searchText, "Search", JOptionPane.INFORMATION_MESSAGE);
            }
            displayUserAccountList(userAccountList);
            searchTextField.setText("");
        } catch (ExceptionHandler ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid search", "Search", JOptionPane.WARNING_MESSAGE);
        }
}//GEN-LAST:event_searchButtonActionPerformed

    private void createUserAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createUserAccountButtonActionPerformed
        // TODO add your handling code here:
        try {
            CreateUserAccountDialog createUserAccount = new CreateUserAccountDialog(parentFrame, true);
            createUserAccount.setVisible(true);
            if (createUserAccount.success) {
                displayUserAccountList();
            }
        } catch (ExceptionHandler ex) {
        }
    }//GEN-LAST:event_createUserAccountButtonActionPerformed

    private void editUserAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUserAccountButtonActionPerformed
        // TODO add your handling code here:
        try {
            int index = userAccountTable.getSelectedRow();
            if (index >= 0) {
                UserAccount user = userAccountList.get(index);
                if (user.getUserID() != this.user.getUserID()) {
                    EditUserAccountDialog editUserAccount = new EditUserAccountDialog(parentFrame, true, user);
                    editUserAccount.setVisible(true);
                    if (editUserAccount.success) {
                        displayUserAccountList();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "You can't edit your own account", "Edit User Account", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select user account", "Edit User Account", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ExceptionHandler ex) {
        }
}//GEN-LAST:event_editUserAccountButtonActionPerformed

    private void userTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userTypeComboBoxActionPerformed
        try {
            // TODO add your handling code here:
            displayUserAccountList();
        } catch (ExceptionHandler ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_userTypeComboBoxActionPerformed

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
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void updateAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateAccountButtonActionPerformed
        // TODO add your handling code here:
        updateAccount();
    }//GEN-LAST:event_updateAccountButtonActionPerformed

    private void deleteUserAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserAccountButtonActionPerformed
        // TODO add your handling code here:
        try {
            int index = userAccountTable.getSelectedRow();
            if (index >= 0) {
                int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete user account?", "Delete User Account", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    UserAccount user = userAccountList.get(index);
                    if (user.getUserID() != this.user.getUserID()) {
                        userAccountService.deleteUserAccount(user.getUserID());
                        JOptionPane.showMessageDialog(this, "User account has successfully deleted", "Delete User Account", JOptionPane.INFORMATION_MESSAGE);
                        displayUserAccountList();
                    } else {
                        JOptionPane.showMessageDialog(this, "You can't delete your own account", "Delete User Account", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select user account", "Delete User Account", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ExceptionHandler ex) {
        }
    }//GEN-LAST:event_deleteUserAccountButtonActionPerformed

    private void addSchoolYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSchoolYearButtonActionPerformed
        // TODO add your handling code here:
        try {
            SchoolYearDialog addSchoolYear = new SchoolYearDialog(parentFrame, true, getNewSchoolYear(), true);
            addSchoolYear.setVisible(true);
            if (addSchoolYear.success) {
                displaySchoolYearList();
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addSchoolYearButtonActionPerformed

    private void editSchoolYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSchoolYearButtonActionPerformed
        // TODO add your handling code here:
        try {
            int index = schoolYearTable.getSelectedRow();
            if (index >= 0) {
                selectedSchoolYear = schoolYearList.get(index);
                SchoolYearDialog editSchoolYear = new SchoolYearDialog(parentFrame, true, selectedSchoolYear, false);
                editSchoolYear.setVisible(true);
                if (editSchoolYear.success) {
                    displaySchoolYearList();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select school year", "Edit School Year", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ExceptionHandler ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editSchoolYearButtonActionPerformed

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
        // TODO add your handling code here:\
//        Runtime r= Runtime.getRuntime();
//Process p = r.exec("C:/Program Files/Adobe/Reader 8.0/Reader/AcroRd32.exe Test.Pdf");
        try //try statement
        {
            Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "D:\\Libraries\\Documents\\MSU-OCS\\review lit\\clearance_qguide.pdf");   //open the file chart.pdf
            p.waitFor();
        } catch (Exception e) //catch any exceptions here
        {
            System.out.println("Error" + e);  //print the error
        }
    }//GEN-LAST:event_jMenu4ActionPerformed

    private SchoolYear getNewSchoolYear() throws ExceptionHandler {
        SchoolYear lastAdded = null;
        SchoolYear newSchoolYear = new SchoolYear();
        if (schoolYearList.size() > 0) {
            lastAdded = schoolYearList.get(schoolYearList.size() - 1);
            newSchoolYear.setYearFrom(lastAdded.getYearTo());
            newSchoolYear.setYearTo(newSchoolYear.getYearFrom() + 1);
        } else {
            newSchoolYear.setYearFrom(getCurrentYear());
            newSchoolYear.setYearTo(newSchoolYear.getYearFrom() + 1);
        }
        return newSchoolYear;
    }

    private int getCurrentYear() throws ExceptionHandler {
        Calendar currentDate = schoolYearService.getServerCurrentDate();
        return currentDate.YEAR;
    }

    private void updateAccount() {
        UpdateAccountDialog updateAccount = new UpdateAccountDialog(this, true, user);
        updateAccount.setVisible(true);
        user = updateAccount.getUserAccount();
        updateAccount.dispose();
        userNameTextField.setText(user.getUserName());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSchoolYearButton;
    private javax.swing.JButton createUserAccountButton;
    private javax.swing.JLabel dateTextField;
    private javax.swing.JButton deleteUserAccountButton;
    private javax.swing.JButton editSchoolYearButton;
    private javax.swing.JButton editUserAccountButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton logOutButton;
    private javax.swing.JTable schoolYearTable;
    private javax.swing.JLabel schoolYearTextField;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JLabel totalUserAccounts;
    private javax.swing.JButton updateAccountButton;
    private javax.swing.JTable userAccountTable;
    private javax.swing.JLabel userNameTextField;
    private javax.swing.JComboBox userTypeComboBox;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import ised.DAO.interfaces.DBManager;
import ised.DAO.interfaces.UserAccountDAO;
import ised.model.Employee;
import ised.model.UserAccount;
import ised.service.implementation.EmployeeServiceImpl;
import ised.service.interfaces.EmployeeService;
import ised.tools.DataDispatcher;
import ised.tools.ExceptionHandler;
import java.sql.CallableStatement;

/**
 *
 * @author ABDUL
 */
public class UserAccountDAOImpl implements UserAccountDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public UserAccountDAOImpl() {
        super();
    }

    public UserAccount getUserAccount(String userName) throws ExceptionHandler {
        sql = "SELECT * FROM user_account WHERE  userName = '" + userName + "'LIMIT 1";
        UserAccount user = null;
        Employee employee = null;
        EmployeeService employeeService = new EmployeeServiceImpl();

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                employee = employeeService.getEmployee(resultSet.getInt("employeeID"));
                user = new UserAccount(resultSet.getInt("userID"), resultSet.getString("userName"),
                        resultSet.getString("password"), resultSet.getString("userType"), resultSet.getString("status"), employee);
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
        return user;
    }

    public List<UserAccount> getUserAccounts() throws ExceptionHandler {
        sql = "SELECT * FROM user_account";
        List<UserAccount> list = new ArrayList<UserAccount>();
        Employee employee = null;
        EmployeeService employeeService = new EmployeeServiceImpl();

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                employee = employeeService.getEmployee(resultSet.getInt("employeeID"));
                UserAccount user = new UserAccount(resultSet.getInt("userID"), resultSet.getString("userName"),
                        resultSet.getString("password"), resultSet.getString("userType"), resultSet.getString("status"), employee);
                list.add(user);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public void resetPassword(int userID) throws ExceptionHandler {
        sql = "UPDATE user_account SET password=123456 WHERE userID =?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareStatement(sql);
            pStatement.setInt(1, userID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } catch (Exception ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public void changePassword(UserAccount userAccount) throws ExceptionHandler {
        sql = "UPDATE user_account SET password=? WHERE userID =?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareStatement(sql);
            pStatement.setString(1, userAccount.getPassword());
            pStatement.setInt(2, userAccount.getUserID());
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } catch (Exception ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public void changeUserName(UserAccount userAccount) throws ExceptionHandler {
        sql = "UPDATE user_account SET userName=? WHERE userID =?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareStatement(sql);
            pStatement.setString(1, userAccount.getUserName());
            pStatement.setInt(2, userAccount.getUserID());
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } catch (Exception ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public boolean checkUserName(String userName) throws ExceptionHandler {
        sql = "SELECT * FROM user_account WHERE userName = '" + userName + "'";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }

    }

    public boolean checkUserAccount(int employeeID,String userType) throws ExceptionHandler {
        sql = "SELECT * FROM user_account NATURAL JOIN employee WHERE employeeID = " + employeeID + " AND userType = '"+userType+"'";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }

    }

    public void addUserAccount(UserAccount account) throws ExceptionHandler {
        sql = "INSERT INTO user_account (userName,password,userType,status,employeeID) VALUES(?,?,?,?,?)";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setString(1, account.getUserName());
            pStatement.setString(2, account.getPassword());
            pStatement.setString(3, account.getUserType());
            pStatement.setString(4, account.getStatus());
            pStatement.setInt(5, account.getEmployee().getEmployeeID());
            pStatement.execute();
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public void editUserAccount(UserAccount account) throws ExceptionHandler {
        sql = "UPDATE user_account SET userName=?, password=?, userType=?, status=?, employeeID=? WHERE userID=?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setString(1, account.getUserName());
            pStatement.setString(2, account.getPassword());
            pStatement.setString(3, account.getUserType());
            pStatement.setString(4, account.getStatus());
            pStatement.setInt(5, account.getEmployee().getEmployeeID());
            pStatement.setInt(6, account.getUserID());
            pStatement.execute();
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

     public void deleteUserAccount(int userID) throws ExceptionHandler {
        sql = "DELETE from user_account WHERE userID = " + userID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public List<UserAccount> getUserAccounts(String userType) throws ExceptionHandler {
        Employee employee = null;
        EmployeeService employeeService = new EmployeeServiceImpl();
        List<UserAccount> list = new ArrayList<UserAccount>();
        sql = "SELECT * FROM user_account WHERE userType = '" + userType + "'";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                employee = employeeService.getEmployee(resultSet.getInt("employeeID"));
                UserAccount user = new UserAccount(resultSet.getInt("userID"), resultSet.getString("userName"),
                        resultSet.getString("password"), resultSet.getString("userType"), resultSet.getString("status"), employee);
                list.add(user);
            }
            return list;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }

    }

    public List<UserAccount> getUserAccounts(String userType, String searchText) throws ExceptionHandler {
        Employee employee = null;
        EmployeeService employeeService = new EmployeeServiceImpl();
        List<UserAccount> list = new ArrayList<UserAccount>();

        if(userType.equalsIgnoreCase("All")){
            sql = "SELECT * FROM user_account NATURAL JOIN employee WHERE "
                + " lastName LIKE '%" + searchText + "%'"
                + " OR firstName LIKE '%" + searchText + "%' ORDER by lastName";
        } else{
             sql = "SELECT * FROM user_account NATURAL JOIN employee WHERE userType = '" + userType + "'"
                + " AND (lastName LIKE '%" + searchText + "%'"
                + " OR firstName LIKE '%" + searchText + "%') ORDER by lastName";
        }
       
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                employee = employeeService.getEmployee(resultSet.getInt("employeeID"));
                UserAccount user = new UserAccount(resultSet.getInt("userID"), resultSet.getString("userName"),
                        resultSet.getString("password"), resultSet.getString("userType"), resultSet.getString("status"), employee);
                list.add(user);
            }
            return list;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import ised.DAO.interfaces.DBManager;
import ised.DAO.interfaces.EmployeeDAO;
import ised.model.Employee;
import ised.tools.DataDispatcher;
import ised.tools.ExceptionHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class EmployeeDAOImpl implements EmployeeDAO {

    private DBManager connectionDB;
    private Connection connection;
    private CallableStatement cStatement;
    private PreparedStatement pStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public Employee addEmployee(Employee employee) throws ExceptionHandler {
        sql = "{ CALL addEmployee (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = (CallableStatement) connection.prepareCall(sql);
            cStatement.setString(1, employee.getFirstName());
            cStatement.setString(2, employee.getMiddleName());
            cStatement.setString(3, employee.getLastName());
            cStatement.setString(4, employee.getGender());
            cStatement.setDate(5, (java.sql.Date) (Date) employee.getDOB());
            cStatement.setString(6, employee.getCivilStatus());
            cStatement.setString(7, employee.getAddress());
            cStatement.setString(8, employee.getReligion());
            cStatement.setString(9, employee.getPosition());
            cStatement.setInt(10, employee.getIsTeacher());
            cStatement.setString(11, employee.getContactNo());
            cStatement.setString(12, employee.getFinishedDegree());
            cStatement.setString(13, employee.getSchoolGraduated());
            cStatement.setInt(14, employee.getYearGraduated());
            cStatement.setInt(16, employee.getYearAdmitted());
            cStatement.setString(17, employee.getStatus());

            if (employee.getPicture() == null) {
                cStatement.setBinaryStream(15, null);
            } else {
                File file = (File) (employee.getPicture());
                FileInputStream fs = new FileInputStream(file);
                cStatement.setBlob(15, fs, file.length());
            }
            if (cStatement.execute()) {
                resultSet = cStatement.getResultSet();
                if (resultSet.next()) {
                    employee.setEmployeeID(resultSet.getInt("employeeID"));
                }
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (FileNotFoundException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, cStatement, connection);
        }
        return employee;
    }

    public void editEmployee(Employee employee) throws ExceptionHandler {
        sql = "UPDATE employee SET firstName=?,middleName=?,lastName=?,gender=?,"
                + "DOB=?,civilStatus=?,address=?,religion=?,position=?,isTeacher=?,"
                + "contactNo=?,finishedHighestDegree=?,schoolGraduated=?,"
                + "yearGraduated=?,image=?,yearAdmitted=? WHERE employeeID =?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (CallableStatement) connection.prepareCall(sql);
            pStatement.setString(1, employee.getFirstName());
            pStatement.setString(2, employee.getMiddleName());
            pStatement.setString(3, employee.getLastName());
            pStatement.setString(4, employee.getGender());
            pStatement.setDate(5, (java.sql.Date) (Date) employee.getDOB());
            pStatement.setString(6, employee.getCivilStatus());
            pStatement.setString(7, employee.getAddress());
            pStatement.setString(8, employee.getReligion());
            pStatement.setString(9, employee.getPosition());
            pStatement.setInt(10, employee.getIsTeacher());
            pStatement.setString(11, employee.getContactNo());
            pStatement.setString(12, employee.getFinishedDegree());
            pStatement.setString(13, employee.getSchoolGraduated());
            pStatement.setInt(14, employee.getYearGraduated());
            pStatement.setInt(16, employee.getYearAdmitted());
            pStatement.setInt(17, employee.getEmployeeID());

            if (employee.getPicture() == null) {
                pStatement.setBinaryStream(15, null);
            } else {
                if(employee.getPicture() instanceof com.mysql.jdbc.Blob){
                    pStatement.setBlob(15, (Blob) employee.getPicture());
                }else{
                    File file = (File) (employee.getPicture());
                    FileInputStream fs = new FileInputStream(file);
                    pStatement.setBlob(15, fs, file.length());
                }
            }

            pStatement.execute();

        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (FileNotFoundException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, cStatement, connection);
        }
    }

    public Employee getEmployee(int employeeID) throws ExceptionHandler {
        Employee employee = null;
        sql = "SELECT * FROM employee WHERE  employeeID = " + employeeID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                Blob picture = null;
                if (resultSet.getBlob("image") != null) {
                    picture = (Blob) resultSet.getBlob("image");
                }

                employee = new Employee(resultSet.getInt("employeeID"), resultSet.getString("firstName"),
                        resultSet.getString("middleName"), resultSet.getString("lastName"),
                        resultSet.getString("gender"), resultSet.getDate("DOB"),
                        resultSet.getString("civilStatus"), resultSet.getString("address"),
                        resultSet.getString("religion"), resultSet.getString("contactNo"),
                        resultSet.getInt("yearAdmitted"), resultSet.getString("employeeStatus"),
                        picture, resultSet.getString("position"), resultSet.getInt("isTeacher"),
                        resultSet.getString("finishedHighestDegree"),
                        resultSet.getString("schoolGraduated"), resultSet.getInt("yearGraduated"));
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
        return employee;
    }

    public List<Employee> getEmployeeList() throws ExceptionHandler {

        List<Employee> list = new ArrayList<Employee>();

        sql = "SELECT * FROM employee ORDER by lastName, firstName, middleName";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Blob picture = null;
                if (resultSet.getBlob("image") != null) {
                    picture = (Blob) resultSet.getBlob("image");
                }

                Employee employee = new Employee(resultSet.getInt("employeeId"), resultSet.getString("lastName"),
                        resultSet.getString("middleName"), resultSet.getString("firstName"),
                        resultSet.getString("gender"), resultSet.getDate("DOB"),
                        resultSet.getString("civilStatus"), resultSet.getString("address"),
                        resultSet.getString("religion"), resultSet.getString("contactNo"),
                        resultSet.getInt("yearAdmitted"), resultSet.getString("employeeStatus"),
                        picture, resultSet.getString("position"), resultSet.getInt("isTeacher"),
                        resultSet.getString("finishedHighestDegree"),
                        resultSet.getString("schoolGraduated"), resultSet.getInt("yearGraduated"));

                list.add(employee);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }

        return list;
    }

    public List<Employee> getEmployeeList(String status) throws ExceptionHandler {

        List<Employee> list = new ArrayList<Employee>();

        sql = "SELECT * FROM employee WHERE employeeStatus = '" + status + "' ORDER by lastName, firstName, middleName";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Blob picture = null;
                if (resultSet.getBlob("image") != null) {
                    picture = (Blob) resultSet.getBlob("image");
                }

                Employee employee = new Employee(resultSet.getInt("employeeId"), resultSet.getString("lastName"),
                        resultSet.getString("middleName"), resultSet.getString("firstName"),
                        resultSet.getString("gender"), resultSet.getDate("DOB"),
                        resultSet.getString("civilStatus"), resultSet.getString("address"),
                        resultSet.getString("religion"), resultSet.getString("contactNo"),
                        resultSet.getInt("yearAdmitted"), resultSet.getString("employeeStatus"),
                        picture, resultSet.getString("position"), resultSet.getInt("isTeacher"),
                        resultSet.getString("finishedHighestDegree"),
                        resultSet.getString("schoolGraduated"), resultSet.getInt("yearGraduated"));

                list.add(employee);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }

        return list;
    }

    public List<Employee> getEmployeeList(String status, String search) throws ExceptionHandler {

        List<Employee> list = new ArrayList<Employee>();

        if (status.equalsIgnoreCase(" ")) {
            sql = "SELECT * FROM employee"
                    + " WHERE (employeeID LIKE '%" + search + "%' OR firstName LIKE '%" + search + "%' OR lastName LIKE '%" + search + "%')"
                    + " ORDER by lastName, firstName, middleName";
        } else {
            sql = "SELECT * FROM employee"
                    + " WHERE employeeStatus = '" + status + "'"
                    + " AND (employeeID LIKE '%" + search + "%' OR firstName LIKE '%" + search + "%' OR lastName LIKE '%" + search + "%')"
                    + " ORDER by lastName, firstName, middleName";
        }

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Blob picture = null;
                if (resultSet.getBlob("image") != null) {
                    picture = (Blob) resultSet.getBlob("image");
                }

                Employee employee = new Employee(resultSet.getInt("employeeId"), resultSet.getString("lastName"),
                        resultSet.getString("middleName"), resultSet.getString("firstName"),
                        resultSet.getString("gender"), resultSet.getDate("DOB"),
                        resultSet.getString("civilStatus"), resultSet.getString("address"),
                        resultSet.getString("religion"), resultSet.getString("contactNo"),
                        resultSet.getInt("yearAdmitted"), resultSet.getString("employeeStatus"),
                        picture, resultSet.getString("position"), resultSet.getInt("isTeacher"),
                        resultSet.getString("finishedHighestDegree"),
                        resultSet.getString("schoolGraduated"), resultSet.getInt("yearGraduated"));

                list.add(employee);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }

        return list;
    }

    public List<Employee> getFacultyList() throws ExceptionHandler {

        List<Employee> list = new ArrayList<Employee>();

        sql = "SELECT * FROM employee WHERE employeeStatus = 'Active' AND isTeacher = 1 ORDER by lastName, firstName, middleName";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Blob picture = null;
                if (resultSet.getBlob("image") != null) {
                    picture = (Blob) resultSet.getBlob("image");
                }

                Employee employee = new Employee(resultSet.getInt("employeeId"), resultSet.getString("lastName"),
                        resultSet.getString("middleName"), resultSet.getString("firstName"),
                        resultSet.getString("gender"), resultSet.getDate("DOB"),
                        resultSet.getString("civilStatus"), resultSet.getString("address"),
                        resultSet.getString("religion"), resultSet.getString("contactNo"),
                        resultSet.getInt("yearAdmitted"), resultSet.getString("employeeStatus"),
                        picture, resultSet.getString("position"), resultSet.getInt("isTeacher"),
                        resultSet.getString("finishedHighestDegree"),
                        resultSet.getString("schoolGraduated"), resultSet.getInt("yearGraduated"));

                list.add(employee);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }

        return list;
    }

    public boolean checkEmployee(Employee employee) throws ExceptionHandler {
        sql = "SELECT * FROM employee WHERE firstName = '" + employee.getFirstName() + "' AND middleName = '" + employee.getMiddleName()
                + "' AND lastName = '" + employee.getLastName() + "' ";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return true;
    }

    public void changeEmployeeStatus(Employee employee) throws ExceptionHandler {
        sql = "{CALL changeEmployeeStatus(?,?)};";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = (CallableStatement) connection.prepareCall(sql);
            cStatement.setInt(1, employee.getEmployeeID());
            cStatement.setString(2, employee.getStatus());
            cStatement.execute();
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }
}

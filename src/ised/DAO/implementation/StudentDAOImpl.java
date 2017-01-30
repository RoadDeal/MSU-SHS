/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.DBManager;
import ised.DAO.interfaces.StudentDAO;
import ised.model.Guardian;
import ised.model.SchoolYear;
import ised.model.Student;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.interfaces.SchoolYearService;
import ised.tools.DataDispatcher;
import ised.tools.ExceptionHandler;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class StudentDAOImpl implements StudentDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public Student getStudent(int studentID) throws ExceptionHandler {
        sql = "SELECT * FROM student NATURAL JOIN guardian WHERE  studentID = '" + studentID + "'LIMIT 1";
        Student student = new Student();

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Guardian guardian = new Guardian(resultSet.getInt("guardianID"), resultSet.getString("guardianName"),
                        resultSet.getString("occupation"), resultSet.getString("contactNo"),
                        resultSet.getString("address"));

                Blob picture = null;
                if (resultSet.getBlob("picture") != null) {
                    picture = (Blob) resultSet.getBlob("picture");
                }

                student = new Student(resultSet.getInt("studentID"), resultSet.getString("firstName"), resultSet.getString("middleName"),
                        resultSet.getString("lastName"), resultSet.getDate("DOB"), resultSet.getString("POB"),
                        resultSet.getString("religion"), resultSet.getString("ethnicGroup"), resultSet.getString("gender"),
                        resultSet.getString("homeAddress"), resultSet.getString("presentAddress"), resultSet.getString("lastSchoolAttended"),
                        resultSet.getInt("entranceTestRating"), guardian, picture, resultSet.getInt("yearAdmitted"));
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
        return student;
    }

    public Student addStudent(Student student) throws ExceptionHandler {
        sql = "{CALL addStudent(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = (CallableStatement) connection.prepareCall(sql);
            cStatement.setInt(1, student.getStudentID());
            cStatement.setString(2, student.getFirstName());
            cStatement.setString(3, student.getMiddleName());
            cStatement.setString(4, student.getLastName());
            cStatement.setDate(5, (java.sql.Date) student.getDOB());
            cStatement.setString(6, student.getPOB());
            cStatement.setString(7, student.getReligion());
            cStatement.setString(8, student.getEthnicGroup());
            cStatement.setString(9, student.getGender());
            cStatement.setString(10, student.getHomeAdd());
            cStatement.setString(11, student.getPresentAdd());
            cStatement.setString(13, student.getLastSchoolAtt());
            cStatement.setInt(14, student.getEntranceTestRating());
            cStatement.setInt(15, student.getYearAdmitted());
            cStatement.setString(16, student.getGuardian().getName());
            cStatement.setString(17, student.getGuardian().getOccupation());
            cStatement.setString(18, student.getGuardian().getContactNo());
            cStatement.setString(19, student.getGuardian().getAddress());

            if (student.getPicture() == null) {
                cStatement.setBinaryStream(12, null);
            } else {
                if (student.getPicture() instanceof com.mysql.jdbc.Blob) {
                    cStatement.setBlob(12, (Blob) student.getPicture());
                } else {
                    File file = (File) (student.getPicture());
                    FileInputStream fs = new FileInputStream(file);
                    cStatement.setBlob(12, fs, file.length());
                }
            }
            if (cStatement.execute()) {
                resultSet = cStatement.getResultSet();
                if (resultSet.next()) {
                    student.setStudentID(resultSet.getInt("studentID"));
                    student.getGuardian().setGuardianID(resultSet.getInt("guardianID"));
                }
            }

        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, cStatement, connection);
        }
        return student;
    }

    public void editStudent(Student student) throws ExceptionHandler {
        sql = "{CALL editStudent(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = (CallableStatement) connection.prepareCall(sql);
            cStatement.setInt(1, student.getStudentID());
            cStatement.setString(2, student.getFirstName());
            cStatement.setString(3, student.getMiddleName());
            cStatement.setString(4, student.getLastName());
            cStatement.setDate(5, (java.sql.Date) student.getDOB());
            cStatement.setString(6, student.getPOB());
            cStatement.setString(7, student.getReligion());
            cStatement.setString(8, student.getEthnicGroup());
            cStatement.setString(9, student.getGender());
            cStatement.setString(10, student.getHomeAdd());
            cStatement.setString(11, student.getPresentAdd());
            cStatement.setString(13, student.getLastSchoolAtt());
            cStatement.setInt(14, student.getEntranceTestRating());
            cStatement.setInt(15, student.getGuardian().getGuardianID());
            cStatement.setString(16, student.getGuardian().getName());
            cStatement.setString(17, student.getGuardian().getOccupation());
            cStatement.setString(18, student.getGuardian().getContactNo());
            cStatement.setString(19, student.getGuardian().getAddress());

            if (student.getPicture() == null) {
                cStatement.setBinaryStream(12, null);
            } else {
                if (student.getPicture() instanceof com.mysql.jdbc.Blob) {
                    cStatement.setBlob(12, (Blob) student.getPicture());
                } else {
                    File file = (File) (student.getPicture());
                    FileInputStream fs = new FileInputStream(file);
                    cStatement.setBlob(12, fs, file.length());
                }
            }

            cStatement.execute();

        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, cStatement, connection);
        }
    }

    public boolean isSafeToDelete(int studentID) throws ExceptionHandler {
        sql = "SELECT * FROM enrollment NATURAL JOIN admission WHERE studentID = " + studentID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void deleteStudent(int studentID) throws ExceptionHandler {
        sql = "DELETE FROM student WHERE studentID = " + studentID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public boolean checkStudent(Student student) throws ExceptionHandler {
        sql = "SELECT * FROM student WHERE lastName = '" + student.getLastName() + "' AND firstName = '" + student.getFirstName() + "' AND "
                + " middleName = '" + student.getMiddleName() + "'";
        boolean checker = true;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                checker = false;
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return checker;
    }

    public List<SchoolYear> getSchoolYearsAttended(int studentID) throws ExceptionHandler {
        List<SchoolYear> list = new ArrayList<SchoolYear>();
        SchoolYear schoolYear = null;
        SchoolYearService schoolYearService = new SchoolYearServiceImpl();

        sql = "SELECT schoolYearEnrolledID FROM enrollment NATURAL JOIN admission WHERE studentID = " + studentID;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                schoolYear = schoolYearService.getSchoolYear(resultSet.getInt("schoolYearEnrolledID"));
                list.add(schoolYear);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<Guardian> getGuardianList() throws ExceptionHandler {
        List<Guardian> list = new ArrayList<Guardian>();

        sql = "SELECT * from guardian";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Guardian guardian = new Guardian(resultSet.getInt("guardianID"), resultSet.getString("guardianName"),
                        resultSet.getString("occupation"), resultSet.getString("contactNo"), resultSet.getString ("address"));
                list.add(guardian);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

     public List<Guardian> getGuardianList(String search) throws ExceptionHandler {
        List<Guardian> list = new ArrayList<Guardian>();

        sql = "SELECT * from guardian WHERE guardianName LIKE '%" + search + "%'";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Guardian guardian = new Guardian(resultSet.getInt("guardianID"), resultSet.getString("guardianName"),
                        resultSet.getString("occupation"), resultSet.getString("contactNo"), resultSet.getString ("address"));
                list.add(guardian);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }
}

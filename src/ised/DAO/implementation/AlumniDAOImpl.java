/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.AlumniDAO;
import ised.DAO.interfaces.DBManager;
import ised.model.Alumni;
import ised.model.Batch;
import ised.model.Student;
import ised.model.Section;
import ised.service.implementation.SectionServiceImpl;
import ised.service.implementation.StudentServiceImpl;
import ised.service.interfaces.SectionService;
import ised.service.interfaces.StudentService;
import ised.tools.DataDispatcher;
import ised.tools.ExceptionHandler;
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
public class AlumniDAOImpl implements AlumniDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public List<Alumni> getAlumniList(int batchID) throws ExceptionHandler {
        List<Alumni> list = new ArrayList<Alumni>();
        Student student = null;
        Section section = null;
        StudentService studentService = new StudentServiceImpl();
        SectionService sectionService = new SectionServiceImpl();

        sql = "SELECT * from alumni NATURAL JOIN student NATURAL JOIN batch"
                + " WHERE batchID = " + batchID
                + " ORDER by lastName, firstName, middleName";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                student = studentService.getStudent(resultSet.getInt("studentID"));
                section = sectionService.getSection(resultSet.getInt("sectionID"));
                Batch batch = new Batch(resultSet.getInt("batchID"), section, resultSet.getInt("yearGraduated"));
                Alumni alumni = new Alumni(resultSet.getInt("alumniID"), student, batch, resultSet.getDouble("cumulativeGradeAverage"));
                list.add(alumni);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<Alumni> getAlumniList(int batchID, String search) throws ExceptionHandler {
        List<Alumni> list = new ArrayList<Alumni>();
        Student student = null;
        Section section = null;
        StudentService studentService = new StudentServiceImpl();
        SectionService sectionService = new SectionServiceImpl();

        sql = "SELECT * from alumni NATURAL JOIN student NATURAL JOIN batch"
                + " WHERE batchID = " + batchID
                + " AND (lastName LIKE '%" + search + "%' OR firstName LIKE '%" + search + "%' OR middleName LIKE '%" + search + "%')"
                + " ORDER by lastName, firstName, middleName";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                student = studentService.getStudent(resultSet.getInt("studentID"));
                section = sectionService.getSection(resultSet.getInt("sectionID"));
                Batch batch = new Batch(resultSet.getInt("batchID"), section, resultSet.getInt("yearGraduated"));
                Alumni alumni = new Alumni(resultSet.getInt("alumniID"), student, batch, resultSet.getDouble("cumulativeGradeAverage"));
                list.add(alumni);
            }
            return list;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }

    }

    public List<Batch> getBatchList() throws ExceptionHandler {
        List<Batch> list = new ArrayList<Batch>();
        Section section = null;
        SectionService sectionService = new SectionServiceImpl();

        sql = "SELECT * from batch ORDER by yearGraduated DESC";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                section = sectionService.getSection(resultSet.getInt("sectionID"));
                Batch batch = new Batch(resultSet.getInt("batchID"), section, resultSet.getInt("yearGraduated"));
                list.add(batch);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public void addAlumni(int batchID, int studentID, int enrollmentID) throws ExceptionHandler {
        sql = "{CALL addAlumni (?,?,?)}";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = (CallableStatement) connection.prepareCall(sql);
            cStatement.setInt(1, batchID);
            cStatement.setInt(2, studentID);
            cStatement.setInt(3, enrollmentID);
            cStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void deleteAlumni(int studentID, int enrollmentID) throws ExceptionHandler {
        sql = "{CALL deleteAlumni (?,?)}";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = (CallableStatement) connection.prepareCall(sql);
            cStatement.setInt(1, studentID);
            cStatement.setInt(2, enrollmentID);
            cStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void addBatch(int sectionID, int yearGraduated) throws ExceptionHandler {
        sql = "INSERT INTO batch(sectionID, yearGraduated) VALUES (?, ?)";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setInt(1, sectionID);
            pStatement.setInt(2, yearGraduated);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public boolean isSafeToDelete(int batchID) throws ExceptionHandler {
        sql = "SELECT * FROM alumni WHERE batchID = " + batchID;
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

    public boolean checkBatch(int sectionID) throws ExceptionHandler {
        sql = "SELECT * FROM batch WHERE sectionID = " + sectionID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public int getBatch(int sectionID) throws ExceptionHandler {
        sql = "SELECT batchID FROM batch WHERE sectionID = " + sectionID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("batchID");
            }
            return 0;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public int getBatchByYear(int yearGraduated) throws ExceptionHandler {
        sql = "SELECT batchID FROM batch WHERE yearGraduated = " + yearGraduated;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("batchID");
            }
            return 0;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void deleteBatch(int batchID) throws ExceptionHandler {
        sql = "DELETE FROM batch WHERE batchID = " + batchID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }
}

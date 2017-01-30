/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.CurriculumDAO;
import ised.DAO.interfaces.DBManager;
import ised.model.Curriculum;
import ised.model.Subject;
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
public class CurriculumDAOImpl implements CurriculumDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public Curriculum getCurriculum(int curriculumID) throws ExceptionHandler {
        sql = "SELECT * FROM curriculum WHERE curriculumID = " + curriculumID;
        Curriculum curriculum = null;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                curriculum = new Curriculum(resultSet.getInt("curriculumID"), resultSet.getInt("curriculumYear"), resultSet.getString("status"));
            }

            return curriculum;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public Curriculum getCurrentCurriculum() throws ExceptionHandler {
        sql = "SELECT * FROM curriculum WHERE status = 'Active'";
        Curriculum curriculum = null;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                curriculum = new Curriculum(resultSet.getInt("curriculumID"), resultSet.getInt("curriculumYear"), resultSet.getString("status"));
            }
            return curriculum;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public Curriculum getCurriculumByYear(int curriculumYear) throws ExceptionHandler {
        sql = "SELECT * FROM curriculum WHERE curriculumYear = " + curriculumYear;
        Curriculum curriculum = null;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                curriculum = new Curriculum(resultSet.getInt("curriculumID"), resultSet.getInt("curriculumYear"), resultSet.getString("status"));
            }

            return curriculum;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public List<Curriculum> getCurriculumList() throws ExceptionHandler {
        List<Curriculum> list = new ArrayList<Curriculum>();
        sql = "SELECT * FROM curriculum ORDER by curriculumYear DESC";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Curriculum curriculum = new Curriculum(resultSet.getInt("curriculumID"), resultSet.getInt("curriculumYear"), resultSet.getString("status"));
                list.add(curriculum);
            }
            return list;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void addCurriculum(int curriculumYear) throws ExceptionHandler {
        sql = "{CALL addCurriculum(?)}";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = connection.prepareCall(sql);
            cStatement.setInt(1, curriculumYear);
            cStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void editCurriculum(int curriculumID, String status) throws ExceptionHandler {
        sql = "{CALL editCurriculum(?,?)}";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = connection.prepareCall(sql);
            cStatement.setInt(1, curriculumID);
            cStatement.setString(2, status);
            cStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void deleteCurriculum(int curriculumID) throws ExceptionHandler {
        sql = "{CALL deleteCurriculum(?)}";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = (CallableStatement) connection.prepareCall(sql);
            cStatement.setInt(1, curriculumID);
            cStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public boolean checkCurriculum(int curriculumYear) throws ExceptionHandler {
        sql = "SELECT * FROM curriculum WHERE curriculumYear = " + curriculumYear;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public boolean hasSubjects(int curriculumID) throws ExceptionHandler {
        sql = "SELECT * FROM curriculum_subjects NATURAL JOIN curriculum WHERE curriculumID = " + curriculumID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public boolean isSafeToDelete(int curriculumID) throws ExceptionHandler {
        sql = "SELECT * FROM section WHERE curriculumID = " + curriculumID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void addSubject(int subjectID, int curriculumID) throws ExceptionHandler {
        sql = "INSERT INTO curriculum_subjects(curriculumID, subjectID) VALUES(?,?)";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setInt(1, curriculumID);
            pStatement.setInt(2, subjectID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void deleteSubject(int subjectID, int curriculumID) throws ExceptionHandler {
        sql = "DELETE from curriculum_subjects WHERE curriculumID = ? AND subjectID = ?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setInt(1, curriculumID);
            pStatement.setInt(2, subjectID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public boolean checkSubject(int subjectID, int curriculumID) throws ExceptionHandler {
        sql = "SELECT * from curriculum_subjects WHERE subjectID = ? AND curriculumID = ?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setInt(1, curriculumID);
            pStatement.setInt(2, subjectID);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

     public boolean isSubjectUsed(int subjectID, int curriculumID) throws ExceptionHandler {
        sql = "SELECT * from student_subject WHERE subjectID = " + subjectID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

//    public void getSubjectList(int curriculumID, int yearLevel) throws ExceptionHandler {
//        List<Subject> list = new ArrayList<Subject>();
//        sql = "SELECT * from curriculum_subjects NATURAL JOIN subject WHERE curriculumID = " + curriculumID + " AND yearLevel = " + yearLevel;
//        try {
//            connectionDB = DBManagerImpl.getInstance();
//            connection = (Connection) connectionDB.getConnection();
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                Subject subject = new Subject(resultSet.getInt("subjectID"),resultSet.getString("subjectCode"),resultSet.getString("description"),
//                        resultSet.getDouble("units"),resultSet.getInt("yearLevel"),resultSet.getString("status"));
//                list.add(subject);
//            }
//        } catch (SQLException ex) {
//            throw new ExceptionHandler(ex);
//        } finally {
//            DataDispatcher.dispatch(resultSet, statement, connection);
//        }
//    }
}

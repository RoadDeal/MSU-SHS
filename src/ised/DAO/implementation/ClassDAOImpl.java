/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.ClassDAO;
import ised.DAO.interfaces.DBManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import ised.model.Section;
import ised.model.Class;
import ised.model.SchoolYear;
import ised.service.implementation.CurriculumServiceImpl;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.implementation.SectionServiceImpl;
import ised.service.interfaces.SchoolYearService;
import ised.service.interfaces.SectionService;
import ised.tools.DataDispatcher;
import ised.tools.ExceptionHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class ClassDAOImpl implements ClassDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public List<Class> getClassList(int schoolYearID) throws ExceptionHandler {
        List<Class> list = new ArrayList<Class>();
        Section section = null;
        SchoolYear schoolYear = null;
        Class classSection = null;
        SectionService sectionService = new SectionServiceImpl();
        SchoolYearService schoolYearService = new SchoolYearServiceImpl();
        sql = "SELECT * FROM class WHERE schoolYearID = " + schoolYearID + " ORDER by yearLevel";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                section = sectionService.getSection(resultSet.getInt("sectionID"));
                schoolYear = schoolYearService.getSchoolYear(resultSet.getInt("schoolYearID"));
                classSection = new Class(resultSet.getInt("classID"), section, resultSet.getInt("yearLevel"), schoolYear);
                list.add(classSection);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<Class> getClassList(int schoolYearID, String status) throws ExceptionHandler {
        List<Class> list = new ArrayList<Class>();
        Section section = null;
        SchoolYear schoolYear = null;
        Class classSection = null;
        SectionService sectionService = new SectionServiceImpl();
        SchoolYearService schoolYearService = new SchoolYearServiceImpl();
        sql = "SELECT class.* FROM class NATURAL JOIN section WHERE schoolYearID = " + schoolYearID
                + " AND sectionStatus = '" + status + "'"
                + " ORDER by yearLevel";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                section = sectionService.getSection(resultSet.getInt("sectionID"));
                schoolYear = schoolYearService.getSchoolYear(resultSet.getInt("schoolYearID"));
                classSection = new Class(resultSet.getInt("classID"), section, resultSet.getInt("yearLevel"), schoolYear);
                list.add(classSection);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<Class> getClassList(int schoolYearID, int yearLevel) throws ExceptionHandler {
        List<Class> list = new ArrayList<Class>();
        Section section = null;
        SchoolYear schoolYear = null;
        Class classSection = null;
        SectionService sectionService = new SectionServiceImpl();
        SchoolYearService schoolYearService = new SchoolYearServiceImpl();
        sql = "SELECT class.* FROM class NATURAL JOIN section WHERE schoolYearID=? AND yearLevel=? AND sectionStatus='ACTIVE' ORDER by yearLevel";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setInt(1, schoolYearID);
            pStatement.setInt(2, yearLevel);
            pStatement.execute();
            resultSet = pStatement.getResultSet();
            while (resultSet.next()) {
                section = sectionService.getSection(resultSet.getInt("sectionID"));
                schoolYear = schoolYearService.getSchoolYear(resultSet.getInt("schoolYearID"));
                classSection = new Class(resultSet.getInt("classID"), section, resultSet.getInt("yearLevel"), schoolYear);
                list.add(classSection);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public Class getClass(int classID) throws ExceptionHandler {

        Section section = null;
        SchoolYear schoolYear = null;
        Class classSection = null;
        SectionService sectionService = new SectionServiceImpl();
        SchoolYearService schoolYearService = new SchoolYearServiceImpl();
        sql = "SELECT * FROM class WHERE classID = " + classID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                section = sectionService.getSection(resultSet.getInt("sectionID"));
                schoolYear = schoolYearService.getSchoolYear(resultSet.getInt("schoolYearID"));
                classSection = new Class(resultSet.getInt("classID"), section, resultSet.getInt("yearLevel"), schoolYear);
            }
            return classSection;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public boolean hasStudents(int classID) throws ExceptionHandler {
        sql = "SELECT * from enrollment WHERE classID = " + classID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
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

    public void addClass(Class section) throws ExceptionHandler {
        sql = "INSERT INTO class(sectionID, yearLevel, schoolYearID) VALUES (?,?,?)";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setInt(1, section.getSection().getSectionID());
            pStatement.setInt(2, section.getYearLevel());
            pStatement.setInt(3, section.getSchoolYear().getSchoolYearID());
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void clearClassList(int schoolYearID) throws ExceptionHandler {
        sql = "DELETE from class WHERE schoolYearID" + schoolYearID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }
}

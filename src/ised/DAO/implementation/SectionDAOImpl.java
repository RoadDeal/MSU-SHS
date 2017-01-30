/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.DBManager;
import ised.DAO.interfaces.SectionDAO;
import ised.model.Curriculum;
import ised.model.SchoolYear;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import ised.model.Section;
import ised.service.implementation.CurriculumServiceImpl;
import ised.service.interfaces.CurriculumService;
import ised.tools.DataDispatcher;
import ised.tools.ExceptionHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class SectionDAOImpl implements SectionDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public void addSection(String sectionName) throws ExceptionHandler {
        sql = "{ CALL addSection(?) }";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = (CallableStatement) connection.prepareCall(sql);
            cStatement.setString(1, sectionName);
            cStatement.execute();

        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }

    }

    public void editSection(String sectionName, int sectionID) throws ExceptionHandler {
        sql = "UPDATE section SET sectionName = '" + sectionName + "' WHERE sectionID = " + sectionID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void deleteSection(int sectionID) throws ExceptionHandler {
        sql = "DELETE FROM section WHERE sectionID = " + sectionID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void changeSectionStatus(int sectionID, String status) throws ExceptionHandler {
        sql = "UPDATE section SET sectionStatus = '"+ status + "' WHERE sectionID = " + sectionID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public Section getSection(int sectionID) throws ExceptionHandler {
        sql = "SELECT * FROM section WHERE sectionID = " + sectionID;
        Section section = null;
        Curriculum curriculum = null;
        CurriculumService curriculumService = new CurriculumServiceImpl();
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                curriculum = curriculumService.getCurriculum(resultSet.getInt("curriculumID"));
                section = new Section(resultSet.getInt("sectionID"), resultSet.getString("sectionName"),
                        resultSet.getString("sectionStatus"), resultSet.getInt("yearStarted"), curriculum);
            }

            return section;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public List<Section> getSectionList() throws ExceptionHandler {
        List<Section> list = new ArrayList<Section>();
        Section section = null;
        sql = "SELECT * FROM section ORDER by sectionName";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                section = getSection(resultSet.getInt("sectionID"));
                list.add(section);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<Section> getSectionList(String status) throws ExceptionHandler {
        List<Section> list = new ArrayList<Section>();
        Section section = null;
        sql = "SELECT * FROM section WHERE sectionStatus = '" + status + "' ORDER by sectionName";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                section = getSection(resultSet.getInt("sectionID"));
                list.add(section);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public boolean isSectionExist(String sectionName) throws ExceptionHandler {
        sql = "SELECT * FROM section WHERE sectionName = '" + sectionName + "'";

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
}

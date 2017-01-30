/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.DBManager;
import ised.DAO.interfaces.ClassScheduleDAO;
import ised.model.ClassSchedule;
import ised.model.Schedule;
import ised.model.Class;
import ised.model.Employee;
import ised.model.Subject;
import ised.service.implementation.ClassServiceImpl;
import ised.service.implementation.EmployeeServiceImpl;
import ised.service.implementation.SubjectServiceImpl;
import ised.service.interfaces.ClassService;
import ised.service.interfaces.EmployeeService;
import ised.service.interfaces.SubjectService;
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
import java.sql.Types;

/**
 *
 * @author ABDUL
 */
public class ClassScheduleDAOImpl implements ClassScheduleDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public List<ClassSchedule> getClassScheduleList(int classID, char day) throws ExceptionHandler {
        List<ClassSchedule> list = new ArrayList<ClassSchedule>();
        ClassSchedule classSchedule = null;
        Schedule schedule = null;
        Class section = null;
        Employee employee = null;
        Subject subject = null;
        ClassService classService = new ClassServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
        SubjectService subjectService = new SubjectServiceImpl();

        sql = "SELECT * from class_schedule NATURAL JOIN schedule"
                + " WHERE classID = " + classID
                + " AND days LIKE '%" + day + "%'"
                + " ORDER by startTime, endTime";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                section = classService.getClass(resultSet.getInt("classID"));
                employee = employeeService.getEmployee(resultSet.getInt("employeeID"));
                subject = subjectService.getSubject(resultSet.getInt("subjectID"));
                schedule = new Schedule(resultSet.getInt("scheduleID"), resultSet.getString("days"), resultSet.getTime("startTime"), resultSet.getTime("endTime"));
                classSchedule = new ClassSchedule(resultSet.getInt("classScheduleID"), section, employee, schedule, subject, resultSet.getString("nonSubject"));
                list.add(classSchedule);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<ClassSchedule> getClassScheduleList(int classID) throws ExceptionHandler {
        List<ClassSchedule> list = new ArrayList<ClassSchedule>();
        ClassSchedule classSchedule = null;
        Schedule schedule = null;
        Class section = null;
        Employee employee = null;
        Subject subject = null;
        ClassService classService = new ClassServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
        SubjectService subjectService = new SubjectServiceImpl();

        sql = "SELECT * from class_schedule NATURAL JOIN schedule"
                + " WHERE classID = " + classID
                + " ORDER by startTime, endTime";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                section = classService.getClass(resultSet.getInt("classID"));
                employee = employeeService.getEmployee(resultSet.getInt("employeeID"));
                subject = subjectService.getSubject(resultSet.getInt("subjectID"));
                schedule = new Schedule(resultSet.getInt("scheduleID"), resultSet.getString("days"), resultSet.getTime("startTime"), resultSet.getTime("endTime"));
                classSchedule = new ClassSchedule(resultSet.getInt("classScheduleID"), section, employee, schedule, subject, resultSet.getString("nonSubject"));
                list.add(classSchedule);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }


    public List<ClassSchedule> getSubjectList(int classID) throws ExceptionHandler {
        List<ClassSchedule> list = new ArrayList<ClassSchedule>();
        ClassSchedule classSchedule = null;
        Schedule schedule = null;
        Class section = null;
        Employee employee = null;
        Subject subject = null;
        ClassService classService = new ClassServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
        SubjectService subjectService = new SubjectServiceImpl();

        sql = "SELECT * from class_schedule NATURAL JOIN schedule"
                + " WHERE classID = " + classID
                + " AND subjectID IS NOT NULL"
                + " ORDER by startTime, endTime";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                section = classService.getClass(resultSet.getInt("classID"));
                employee = employeeService.getEmployee(resultSet.getInt("employeeID"));
                subject = subjectService.getSubject(resultSet.getInt("subjectID"));
                schedule = new Schedule(resultSet.getInt("scheduleID"), resultSet.getString("days"), resultSet.getTime("startTime"), resultSet.getTime("endTime"));
                classSchedule = new ClassSchedule(resultSet.getInt("classScheduleID"), section, employee, schedule, subject, resultSet.getString("nonSubject"));
                list.add(classSchedule);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<ClassSchedule> getTeacherLoadingsList(int employeeID, int schoolYearID) throws ExceptionHandler {
        List<ClassSchedule> list = new ArrayList<ClassSchedule>();
        ClassSchedule classSchedule = null;
        Schedule schedule = null;
        Class section = null;
        Employee employee = null;
        Subject subject = null;
        ClassService classService = new ClassServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
        SubjectService subjectService = new SubjectServiceImpl();

        sql = "SELECT * from class_schedule NATURAL JOIN schedule NATURAL JOIN class"
                + " WHERE employeeID = " + employeeID
                + " AND schoolYearID = " + schoolYearID
                + " ORDER by startTime, endTime";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                section = classService.getClass(resultSet.getInt("classID"));
                employee = employeeService.getEmployee(resultSet.getInt("employeeID"));
                subject = subjectService.getSubject(resultSet.getInt("subjectID"));
                schedule = new Schedule(resultSet.getInt("scheduleID"), resultSet.getString("days"), resultSet.getTime("startTime"), resultSet.getTime("endTime"));
                classSchedule = new ClassSchedule(resultSet.getInt("classScheduleID"), section, employee, schedule, subject, resultSet.getString("nonSubject"));
                list.add(classSchedule);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<Schedule> getScheduleList() throws ExceptionHandler {
        List<Schedule> list = new ArrayList<Schedule>();
        Schedule schedule = null;

        sql = "SELECT * FROM schedule";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                schedule = new Schedule(resultSet.getInt("scheduleID"), resultSet.getString("days"), resultSet.getTime("startTime"), resultSet.getTime("endTime"));
                list.add(schedule);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public void addClassSchedule(ClassSchedule classSchedule) throws ExceptionHandler {

        sql = "INSERT INTO class_schedule(classID,scheduleID,subjectID,nonSubject) VALUES(?,?,?,?)";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setInt(1, classSchedule.getSection().getClassID());
            pStatement.setInt(2, classSchedule.getSchedule().getScheduleID());

            if (classSchedule.getSubject() == null) {
                pStatement.setNull(3, Types.INTEGER);
            } else {
                pStatement.setInt(3, classSchedule.getSubject().getSubjectID());
            }

            pStatement.setString(4, classSchedule.getNonSubject());
            pStatement.execute();

        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public void editClassSchedule(ClassSchedule classSchedule) throws ExceptionHandler {

        sql = "UPDATE class_schedule SET classID=?, scheduleID=?, subjectID=?, nonSubject=? WHERE classScheduleID=?";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setInt(1, classSchedule.getSection().getClassID());
            pStatement.setInt(2, classSchedule.getSchedule().getScheduleID());

            if (classSchedule.getSubject() == null) {
                pStatement.setNull(3, Types.INTEGER);
            } else {
                pStatement.setInt(3, classSchedule.getSubject().getSubjectID());
            }

            pStatement.setString(4, classSchedule.getNonSubject());
            pStatement.setInt(5, classSchedule.getClassScheduleID());
            pStatement.execute();

        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public void deleteClassSchedule(int classScheduleID) throws ExceptionHandler {

        sql = "DELETE from class_schedule WHERE classScheduleID = " + classScheduleID;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public void addSchedule(Schedule schedule) throws ExceptionHandler {
        sql = "INSERT INTO schedule(days,startTime,endTime) VALUES(?,?,?)";

        try {

            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setString(1, schedule.getDays());
            pStatement.setTime(2, schedule.getStartTime());
            pStatement.setTime(3, schedule.getEndTime());
            pStatement.execute();

        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public void setTeacher(int classScheduleID, int employeeID) throws ExceptionHandler {
        sql = "UPDATE class_schedule SET employeeID = ? WHERE classScheduleID = ?";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setInt(1, employeeID);
            pStatement.setInt(2, classScheduleID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public void removeTeacher(int classScheduleID) throws ExceptionHandler {
        sql = "UPDATE class_schedule SET employeeID = NULL WHERE classScheduleID = " + classScheduleID;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }
}

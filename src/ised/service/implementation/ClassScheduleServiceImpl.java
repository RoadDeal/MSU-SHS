/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import ised.DAO.implementation.ClassScheduleDAOImpl;
import ised.DAO.interfaces.ClassScheduleDAO;
import ised.model.ClassSchedule;
import ised.model.Schedule;
import ised.service.interfaces.ClassScheduleService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class ClassScheduleServiceImpl implements ClassScheduleService {

    ClassScheduleDAO dao;

    public ClassScheduleServiceImpl() {
        dao = new ClassScheduleDAOImpl();
    }

    public List<ClassSchedule> getClassScheduleList(int classID, char day) throws ExceptionHandler {
        return dao.getClassScheduleList(classID, day);
    }

    public List<ClassSchedule> getClassScheduleList(int classID) throws ExceptionHandler {
        return dao.getClassScheduleList(classID);
    }

    public List<ClassSchedule> getTeacherLoadingsList(int employeeID, int schoolYearID) throws ExceptionHandler {
        return dao.getTeacherLoadingsList(employeeID, schoolYearID);
    }

    public List<ClassSchedule> getSubjectList(int classID) throws ExceptionHandler {
        return dao.getSubjectList(classID);
    }

    public List<Schedule> getScheduleList() throws ExceptionHandler {
        return dao.getScheduleList();
    }

    public void addClassSchedule(ClassSchedule classSchedule) throws ExceptionHandler {
        dao.addClassSchedule(classSchedule);
    }

    public void editClassSchedule(ClassSchedule classSchedule) throws ExceptionHandler {
        dao.editClassSchedule(classSchedule);
    }

    public void deleteClassSchedule(int classScheduleID) throws ExceptionHandler {
        dao.deleteClassSchedule(classScheduleID);
    }

    public void addSchedule(Schedule schedule) throws ExceptionHandler {
        dao.addSchedule(schedule);
    }

    public void setTeacher(int classScheduleID, int employeeID) throws ExceptionHandler {
        dao.setTeacher(classScheduleID, employeeID);
    }

    public void removeTeacher(int classScheduleID) throws ExceptionHandler {
        dao.removeTeacher(classScheduleID);
    }
}

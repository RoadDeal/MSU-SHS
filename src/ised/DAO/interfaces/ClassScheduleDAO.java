/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.interfaces;

import ised.model.ClassSchedule;
import ised.model.Schedule;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface ClassScheduleDAO {

    List<ClassSchedule> getClassScheduleList(int classID, char day) throws ExceptionHandler;

    List<ClassSchedule> getClassScheduleList(int classID) throws ExceptionHandler;

    List<ClassSchedule> getTeacherLoadingsList(int employeeID, int schoolYearID) throws ExceptionHandler;

    List<Schedule> getScheduleList() throws ExceptionHandler;

    List<ClassSchedule> getSubjectList(int classID) throws ExceptionHandler;

    void addClassSchedule(ClassSchedule classSchedule) throws ExceptionHandler;

    void editClassSchedule(ClassSchedule classSchedule) throws ExceptionHandler;

    void deleteClassSchedule(int classScheduleID) throws ExceptionHandler;

    void addSchedule(Schedule schedule) throws ExceptionHandler;

    void setTeacher(int classScheduleID, int employeeID) throws ExceptionHandler;

    void removeTeacher(int classScheduleID) throws ExceptionHandler;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import ised.DAO.implementation.ClassDAOImpl;
import ised.DAO.interfaces.ClassDAO;
import ised.service.interfaces.ClassService;
import ised.model.Class;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class ClassServiceImpl implements ClassService {

    ClassDAO dao;

    public ClassServiceImpl() {
        dao = new ClassDAOImpl();
    }

    public List<Class> getClassList(int schoolYearID) throws ExceptionHandler {
        return dao.getClassList(schoolYearID);
    }

    public List<Class> getClassList(int schoolYearID, String status) throws ExceptionHandler {
        return dao.getClassList(schoolYearID, status);
    }

    public List<Class> getClassList(int schoolYearID, int yearLevel) throws ExceptionHandler {
        return dao.getClassList(schoolYearID, yearLevel);
    }

    public Class getClass(int classID) throws ExceptionHandler {
        return dao.getClass(classID);
    }

    public boolean hasStudents(int classID) throws ExceptionHandler {
        return dao.hasStudents(classID);
    }

    public void addClass(Class section) throws ExceptionHandler {
        dao.addClass(section);
    }

    public void clearClassList(int schoolYearID) throws ExceptionHandler {
        dao.clearClassList(schoolYearID);
    }
}

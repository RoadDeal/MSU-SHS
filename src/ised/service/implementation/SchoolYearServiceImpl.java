/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import ised.DAO.implementation.SchoolYearDAOImpl;
import ised.DAO.interfaces.SchoolYearDAO;
import ised.model.SchoolYear;
import ised.service.interfaces.SchoolYearService;
import ised.tools.ExceptionHandler;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class SchoolYearServiceImpl implements SchoolYearService {

    private SchoolYearDAO dao;

    public SchoolYearServiceImpl() {
        dao = new SchoolYearDAOImpl();
    }

    public void addSchoolYear(SchoolYear SchoolYear) throws ExceptionHandler {
        dao.addSchoolYear(SchoolYear);
    }

    public void editSchoolYear(SchoolYear SchoolYear) throws ExceptionHandler {
        dao.editSchoolYear(SchoolYear);
    }

    public SchoolYear getSchoolYear(int schoolYearID) throws ExceptionHandler {
        return dao.getSchoolYear(schoolYearID);
    }

    public List<SchoolYear> getSchoolYearList() throws ExceptionHandler {
        return dao.getSchoolYearList();
    }

    public SchoolYear getCurrentSchoolYear() throws ExceptionHandler {
        return dao.getCurrentSchoolYear();
    }

    public SchoolYear getPreviousSchoolYear(int schoolYearID) throws ExceptionHandler {
        return dao.getPreviousSchoolYear(schoolYearID);
    }

    public SchoolYear getNextSchoolYear(int schoolYearID) throws ExceptionHandler {
        return dao.getNextSchoolYear(schoolYearID);
    }

    public Calendar getServerCurrentDate() throws ExceptionHandler {
        return dao.getServerCurrentDate();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.service.implementation;

import ised.DAO.implementation.AdviserDAOImpl;
import ised.DAO.interfaces.AdviserDAO;
import ised.model.Adviser;
import ised.service.interfaces.AdviserService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class AdviserServiceImpl implements AdviserService{

    AdviserDAO dao;

    public AdviserServiceImpl(){
        dao = new AdviserDAOImpl();
    }

    public Adviser getAdviser(int classID) throws ExceptionHandler {
        return dao.getAdviser(classID);
    }

    public Adviser getAdvisse(int employeeID, int schoolYearID) throws ExceptionHandler {
        return dao.getAdvisse(employeeID, schoolYearID);
    }

    public void addAdvisee(int classID, int employeeID) throws ExceptionHandler {
        dao.addAdvisee(classID, employeeID);
    }

    public void editAdvisee(int classID, int employeeID) throws ExceptionHandler {
        dao.editAdvisee(classID, employeeID);
    }

    public void deleteAdvisee(int adviserID) throws ExceptionHandler {
        dao.deleteAdvisee(adviserID);
    }
}

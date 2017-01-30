/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.DAO.interfaces;

import ised.model.Adviser;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface AdviserDAO {

    Adviser getAdviser(int classID) throws ExceptionHandler;

    Adviser getAdvisse(int employeeID, int schoolYearID) throws ExceptionHandler;

    void addAdvisee(int classID, int employeeID) throws ExceptionHandler;

    void editAdvisee(int classID, int adviserID) throws ExceptionHandler;

    void deleteAdvisee(int adviserID) throws ExceptionHandler;

}

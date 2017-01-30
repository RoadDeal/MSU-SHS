/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.service.interfaces;

import ised.tools.ExceptionHandler;
import ised.model.Class;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface ClassService {

    List<Class> getClassList(int schoolYearID) throws ExceptionHandler;

    List<Class> getClassList(int schoolYearID, String status) throws ExceptionHandler;

    List<Class> getClassList(int schoolYearID, int yearLevel) throws ExceptionHandler;

    Class getClass(int classID) throws ExceptionHandler;

    boolean hasStudents (int classID) throws ExceptionHandler;

    void addClass(Class section) throws ExceptionHandler;

    void clearClassList(int schoolYearID) throws ExceptionHandler;
}

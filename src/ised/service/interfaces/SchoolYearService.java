/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.interfaces;

import ised.model.SchoolYear;
import ised.tools.ExceptionHandler;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface SchoolYearService {

    void addSchoolYear(SchoolYear schoolYear) throws ExceptionHandler;

    void editSchoolYear(SchoolYear schoolYear) throws ExceptionHandler;

    Calendar getServerCurrentDate() throws ExceptionHandler;

    SchoolYear getSchoolYear(int schoolYearID) throws ExceptionHandler;

    SchoolYear getCurrentSchoolYear() throws ExceptionHandler;

    SchoolYear getPreviousSchoolYear(int schoolYearID) throws ExceptionHandler;

    SchoolYear getNextSchoolYear(int schoolYearID) throws ExceptionHandler;

    List<SchoolYear> getSchoolYearList() throws ExceptionHandler;
}

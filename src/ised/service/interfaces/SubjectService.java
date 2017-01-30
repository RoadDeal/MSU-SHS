/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.interfaces;

import ised.model.Subject;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface SubjectService {

    List<Subject> getSubjectList(int curriculumID, int yearLevel) throws ExceptionHandler;

    List<Subject> getSubjectList(int curriculumID) throws ExceptionHandler;

    List<Subject> getSubjectFromPrevCurrList(int curriculumID, int yearLevel) throws ExceptionHandler;

    Subject getSubject(int subjectID) throws ExceptionHandler;

    Subject getSubject(String subjectCode) throws ExceptionHandler;

    void addSubject(Subject subject) throws ExceptionHandler;

    void editSubject(Subject subject) throws ExceptionHandler;

    void deleteSubject(int subjectID) throws ExceptionHandler;

    boolean checkSubject(String subjectCode) throws ExceptionHandler;

    boolean isSafeToDelete(int subjectID, int curriculumID) throws ExceptionHandler;
}

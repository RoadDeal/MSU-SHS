/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.interfaces;

import ised.model.StudentSubject;
import ised.model.Subject;
import ised.model.SubjectGrade;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface StudentSubjectService {

    StudentSubject getStudentSubject(int studentSubjectID) throws ExceptionHandler;

    void addStudentSubject(int enrollmentID, int subjectID) throws ExceptionHandler;

    void deleteStudentSubjects(int enrollmnetID) throws ExceptionHandler;

    void enterStudentGrade(SubjectGrade subjectGrade) throws ExceptionHandler;

    List<SubjectGrade> getStudentGradeList(int classID, int subjectID, int grading) throws ExceptionHandler;

    List<StudentSubject> getFailedStudentList(int schoolYearID) throws ExceptionHandler;

    void lockGrades(int studentSubjectID, int grading, int flag) throws ExceptionHandler;

    double getFailedSubjectUnits(int enrollmentID) throws ExceptionHandler;

    List<StudentSubject> getStudentSubjectList(int enrollmentID) throws ExceptionHandler;

    List<SubjectGrade> getSubjectGradeList(int studentSubjectID) throws ExceptionHandler;
}

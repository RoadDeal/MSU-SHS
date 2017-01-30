/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.DAO.interfaces;

import ised.model.Curriculum;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface CurriculumDAO {

    Curriculum getCurriculum(int curriculumID) throws ExceptionHandler;

    Curriculum getCurrentCurriculum() throws ExceptionHandler;

    Curriculum getCurriculumByYear(int curriculumYear) throws ExceptionHandler;

    List<Curriculum> getCurriculumList() throws ExceptionHandler;

    void addCurriculum(int curriculumYear) throws ExceptionHandler;

    void editCurriculum(int curriculumID, String status) throws ExceptionHandler;

    void deleteCurriculum(int curriculumID) throws ExceptionHandler;

    boolean checkCurriculum(int curriculumYear) throws ExceptionHandler;

    boolean hasSubjects(int curriculumID) throws ExceptionHandler;

    boolean isSafeToDelete(int curriculumID) throws ExceptionHandler;

    void addSubject(int subjectID, int curriculumID) throws ExceptionHandler;

    void deleteSubject(int subjectID, int curriculumID) throws ExceptionHandler;

    boolean checkSubject(int subjectID, int curriculumID) throws ExceptionHandler;

    boolean isSubjectUsed(int subjectID, int curriculumID) throws ExceptionHandler;

}

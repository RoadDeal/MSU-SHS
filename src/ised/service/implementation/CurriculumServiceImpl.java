/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import ised.DAO.implementation.CurriculumDAOImpl;
import ised.DAO.interfaces.CurriculumDAO;
import ised.model.Curriculum;
import ised.service.interfaces.CurriculumService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class CurriculumServiceImpl implements CurriculumService {

    CurriculumDAO dao;

    public CurriculumServiceImpl() {
        dao = new CurriculumDAOImpl();
    }

    public Curriculum getCurriculum(int curriculumID) throws ExceptionHandler {
        return dao.getCurriculum(curriculumID);
    }

    public Curriculum getCurerentCurriculum() throws ExceptionHandler {
        return dao.getCurrentCurriculum();
    }

    public Curriculum getCurriculumByYear(int curriculumYear) throws ExceptionHandler {
        return dao.getCurriculumByYear(curriculumYear);
    }

    public List<Curriculum> getCurriculumList() throws ExceptionHandler {
        return dao.getCurriculumList();
    }

    public void addCurriculum(int curriculumYear) throws ExceptionHandler {
        dao.addCurriculum(curriculumYear);
    }

    public void editCurriculum(int curriculumID, String status) throws ExceptionHandler {
        dao.editCurriculum(curriculumID, status);
    }

    public void deleteCurriculum(int curriculumID) throws ExceptionHandler {
        dao.deleteCurriculum(curriculumID);
    }

    public boolean checkCurriculum(int curriculumYear) throws ExceptionHandler {
        return dao.checkCurriculum(curriculumYear);
    }

    public boolean hasSubjects(int curriculumID) throws ExceptionHandler {
        return dao.hasSubjects(curriculumID);
    }

    public boolean isSafeToDelete(int curriculumID) throws ExceptionHandler {
        return dao.isSafeToDelete(curriculumID);
    }

    public void addSubject(int subjectID, int curriculumID) throws ExceptionHandler {
        dao.addSubject(subjectID, curriculumID);
    }

    public void deleteSubject(int subjectID, int curriculumID) throws ExceptionHandler {
        dao.deleteSubject(subjectID, curriculumID);
    }

    public boolean checkSubject(int subjectID, int curriculumID) throws ExceptionHandler {
        return dao.checkSubject(subjectID, curriculumID);
    }

    public Curriculum getCurrentCurriculum() throws ExceptionHandler {
        return dao.getCurrentCurriculum();
    }

    public boolean isSubjectUsed(int subjectID, int curriculumID) throws ExceptionHandler {
        return dao.isSubjectUsed(subjectID, curriculumID);
    }
}

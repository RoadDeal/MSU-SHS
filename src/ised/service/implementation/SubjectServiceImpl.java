/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.service.implementation;

import ised.DAO.implementation.SubjectDAOImpl;
import ised.DAO.interfaces.SubjectDAO;
import ised.model.Subject;
import ised.service.interfaces.SubjectService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class SubjectServiceImpl implements SubjectService{

    SubjectDAO dao;

    public SubjectServiceImpl(){
        dao = new SubjectDAOImpl();
    }

    public List<Subject> getSubjectList(int curriculumID, int yearLevel) throws ExceptionHandler{
        return dao.getSubjectList(curriculumID, yearLevel);
    }

    public List<Subject> getSubjectList(int curriculumID) throws ExceptionHandler{
        return dao.getSubjectList(curriculumID);
    }

    public List<Subject> getSubjectFromPrevCurrList(int curriculumID, int yearLevel) throws ExceptionHandler{
        return dao.getSubjectFromPrevCurrList(curriculumID, yearLevel);
    }

    public Subject getSubject(int subjectID) throws ExceptionHandler{
        return dao.getSubject(subjectID);
    }

    public Subject getSubject(String subjectCode) throws ExceptionHandler{
        return dao.getSubject(subjectCode);
    }

    public void addSubject(Subject subject) throws ExceptionHandler{
        dao.addSubject(subject);
    }

    public void editSubject(Subject subject) throws ExceptionHandler{
        dao.editSubject(subject);
    }

    public void deleteSubject(int subjectID) throws ExceptionHandler{
        dao.deleteSubject(subjectID);
    }

    public boolean checkSubject(String subjectCode) throws ExceptionHandler{
        return dao.checkSubject(subjectCode);
    }

    public boolean isSafeToDelete(int subjectID, int curriculumID) throws ExceptionHandler{
        return dao.isSafeToDelete(subjectID, curriculumID);
    }
}

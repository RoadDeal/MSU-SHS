/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import ised.DAO.implementation.AlumniDAOImpl;
import ised.DAO.interfaces.AlumniDAO;
import ised.model.Alumni;
import ised.model.Batch;
import ised.service.interfaces.AlumniService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class AlumniServiceImpl implements AlumniService {

    AlumniDAO dao;

    public AlumniServiceImpl() {
        dao = new AlumniDAOImpl();
    }

    public List<Alumni> getAlumniList(int batchID) throws ExceptionHandler {
        return dao.getAlumniList(batchID);
    }

    public List<Alumni> getAlumniList(int batchID, String search) throws ExceptionHandler {
        return dao.getAlumniList(batchID, search);
    }

    public List<Batch> getBatchList() throws ExceptionHandler {
        return dao.getBatchList();
    }

    public void addAlumni(int batchID, int studentID, int enrollmentID) throws ExceptionHandler {
        dao.addAlumni(batchID, studentID, enrollmentID);
    }

    public void deleteAlumni(int studentID, int enrollmentID) throws ExceptionHandler {
        dao.deleteAlumni(studentID, enrollmentID);
    }

    public void addBatch(int sectionID, int yearGraduated) throws ExceptionHandler {
        dao.addBatch(sectionID, yearGraduated);
    }

     public void deleteBatch(int batchID) throws ExceptionHandler{
         dao.deleteBatch(batchID);
     }

    public boolean checkBatch(int sectionID) throws ExceptionHandler {
        return dao.checkBatch(sectionID);
    }

    public boolean isSafeToDelete(int batchID) throws ExceptionHandler {
        return dao.isSafeToDelete(batchID);
    }

    public int getBatch(int sectionID) throws ExceptionHandler{
        return dao.getBatch(sectionID);
    }

    public int getBatchByYear(int yearGraduated) throws ExceptionHandler{
        return dao.getBatchByYear(yearGraduated);
    }
}

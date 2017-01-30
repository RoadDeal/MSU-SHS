/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.interfaces;

import ised.model.Alumni;
import ised.model.Batch;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface AlumniDAO {

    List<Alumni> getAlumniList(int batchID) throws ExceptionHandler;

    List<Alumni> getAlumniList(int batchID, String search) throws ExceptionHandler;

    List<Batch> getBatchList() throws ExceptionHandler;

    void addAlumni(int batchID, int studentID, int enrollmentID) throws ExceptionHandler;

    void deleteAlumni(int studentID, int enrollmentID) throws ExceptionHandler;

    void addBatch(int sectionID, int yearGraduated) throws ExceptionHandler;

    void deleteBatch(int batchID) throws ExceptionHandler;

    boolean checkBatch(int sectionID) throws ExceptionHandler;

    boolean isSafeToDelete(int batchID) throws ExceptionHandler;

    int getBatch(int sectionID) throws ExceptionHandler;

    int getBatchByYear(int yearGraduated) throws ExceptionHandler;
}

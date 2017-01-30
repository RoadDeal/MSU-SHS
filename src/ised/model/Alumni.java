/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class Alumni {

    private int alumniID;
    private Student student;
    private Batch batch;
    private double CGA;

    public Alumni(int alumniID, Student student, Batch batch, double CGA) {
        this.alumniID = alumniID;
        this.student = student;
        this.batch = batch;
        this.CGA = CGA;
    }

    public double getCGA() {
        return CGA;
    }

    public void setCGA(double CGA) {
        this.CGA = CGA;
    }

    public int getAlumniID() {
        return alumniID;
    }

    public void setAlumniID(int alumniID) {
        this.alumniID = alumniID;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    
}

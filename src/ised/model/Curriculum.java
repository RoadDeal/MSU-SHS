/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.model;

/**
 *
 * @author ABDUL
 */
public class Curriculum {

    private int curriculumID;
    private int curriculumYear;
    private String status;

    public Curriculum(int curriculumID, int curriculumYear, String status) {
        this.curriculumID = curriculumID;
        this.curriculumYear = curriculumYear;
        this.status = status;
    }

    @Override
    public String toString() {
        return "" + curriculumYear;
    }

    public int getCurriculumID() {
        return curriculumID;
    }

    public void setCurriculumID(int curriculumID) {
        this.curriculumID = curriculumID;
    }

    public int getCurriculumYear() {
        return curriculumYear;
    }

    public void setCurriculumYear(int curriculumYear) {
        this.curriculumYear = curriculumYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class Subject {

    private int subjectID;
    private String subjectCode;
    private String description;
    private double units;
    private int yearLevel;

    public Subject(int subjectID, String subjectCode, String description, double units, int yearLevel) {
        this.subjectID = subjectID;
        this.subjectCode = subjectCode;
        this.description = description;
        this.units = units;
        this.yearLevel = yearLevel;
    }

    public Subject(String subjectCode, String description, double units, int yearLevel) {
        this.subjectCode = subjectCode;
        this.description = description;
        this.units = units;
        this.yearLevel = yearLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }
    
}

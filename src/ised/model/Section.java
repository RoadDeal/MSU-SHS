/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class Section {

    private int sectionID;
    private String sectionName;
    private String sectionStatus;
    private int yearStarted;
    private Curriculum curriculum;

    public Section(int sectionID, String sectionName, String sectionStatus, int yearStarted, Curriculum curriculum) {
        this.sectionID = sectionID;
        this.sectionName = sectionName;
        this.sectionStatus = sectionStatus;
        this.yearStarted = yearStarted;
        this.curriculum = curriculum;
    }

    @Override
    public String toString(){
        return this.sectionName;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionStatus() {
        return sectionStatus;
    }

    public void setSectionStatus(String sectionStatus) {
        this.sectionStatus = sectionStatus;
    }

    public int getYearStarted() {
        return yearStarted;
    }

    public void setYearStarted(int yearStarted) {
        this.yearStarted = yearStarted;
    }

    
}

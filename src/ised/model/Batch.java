/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class Batch {

    private int batchID;
    private Section section;
    private int yearLevelGraduated;

    public Batch(int batchID, Section section, int yearLevelGraduated) {
        this.batchID = batchID;
        this.section = section;
        this.yearLevelGraduated = yearLevelGraduated;
    }

    @Override
    public String toString(){
        return section.getSectionName();
    }

    public int getBatchID() {
        return batchID;
    }

    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public int getYearLevelGraduated() {
        return yearLevelGraduated;
    }

    public void setYearLevelGraduated(int yearLevelGraduated) {
        this.yearLevelGraduated = yearLevelGraduated;
    }

    

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class Class {

    private int classID;
    private Section section;
    private int yearLevel;
    private SchoolYear schoolYear;

    public Class(){

    }
    public Class(int classID, Section section, int yearLevel, SchoolYear schoolYear) {
        this.classID = classID;
        this.section = section;
        this.yearLevel = yearLevel;
        this.schoolYear = schoolYear;
    }

    @Override
    public String toString(){
        return section.getSectionName();
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    
}

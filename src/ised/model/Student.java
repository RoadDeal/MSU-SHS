/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

import java.util.Date;

/**
 *
 * @author ABDUL
 */
public class Student {

    private int studentID;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date DOB;
    private String POB;
    private String religion;
    private String ethnicGroup;
    private String gender;
    private String homeAdd;
    private String presentAdd;
    private String lastSchoolAtt;
    private int entranceTestRating;
    private Object picture;
    private int yearAdmitted;
    private Guardian guardian;

    public Student(){

    }

    public Student(int studentID, String firstName, String middleName, String lastName,
            Date DOB, String POB, String religion, String ethnicGroup, String gender,
            String homeAdd, String presentAdd, String lastSchoolAtt, int entranceTestRating,
            Guardian guardian, Object picture, int yearAdmitted) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.POB = POB;
        this.religion = religion;
        this.ethnicGroup = ethnicGroup;
        this.gender = gender;
        this.homeAdd = homeAdd;
        this.presentAdd = presentAdd;
        this.lastSchoolAtt = lastSchoolAtt;
        this.entranceTestRating = entranceTestRating;
        this.guardian = guardian;
        this.picture = picture;
        this.yearAdmitted = yearAdmitted;
    }

    public Student(int studentID, String firstName, String middleName, String lastName, String gender) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
    }




    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public int getEntranceTestRating() {
        return entranceTestRating;
    }

    public void setEntranceTestRating(int entranceTestRating) {
        this.entranceTestRating = entranceTestRating;
    }

    public String getPOB() {
        return POB;
    }

    public void setPOB(String POB) {
        this.POB = POB;
    }

    public String getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeAdd() {
        return homeAdd;
    }

    public void setHomeAdd(String homeAdd) {
        this.homeAdd = homeAdd;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastSchoolAtt() {
        return lastSchoolAtt;
    }

    public void setLastSchoolAtt(String lastSchoolAtt) {
        this.lastSchoolAtt = lastSchoolAtt;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Object getPicture() {
        return picture;
    }

    public void setPicture(Object picture) {
        this.picture = picture;
    }

    public String getPresentAdd() {
        return presentAdd;
    }

    public void setPresentAdd(String presentAdd) {
        this.presentAdd = presentAdd;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getYearAdmitted() {
        return yearAdmitted;
    }

    public void setYearAdmitted(int yearAdmitted) {
        this.yearAdmitted = yearAdmitted;
    }

    public Guardian getGuardian() {
        return guardian;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    public String getFullName(){
        return lastName+", "+firstName+" "+middleName;
    }

    public String toString(){
        return lastName+", "+firstName+" "+middleName;
    }

}

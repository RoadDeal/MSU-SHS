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
public class Employee {

    private int employeeID;
    private String lastName;
    private String middleName;
    private String firstName;
    private String gender;
    private Date DOB;
    private String POB;
    private String civilStatus;
    private String address;
    private String religion;
    private String contactNo;
    private int yearAdmitted;
    private String status;
    private Object picture;
    private String position;
    private int isTeacher;
    private String finishedDegree;
    private String schoolGraduated;
    private int yearGraduated;

    public Employee(){

    }

    public Employee(int employeeID, String lastName, String middleName, String firstName,
            String gender, Date DOB, String civilStatus, String address,
            String religion, String contactNo, int yearAdmitted, String status, Object image,
            String position, int isTeacher, String finishedDegree,
            String schoolGraduated, int yearGraduated) {
        this.employeeID = employeeID;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.gender = gender;
        this.DOB = DOB;
        this.POB = POB;
        this.civilStatus = civilStatus;
        this.address = address;
        this.religion = religion;
        this.contactNo = contactNo;
        this.yearAdmitted = yearAdmitted;
        this.status = status;
        this.picture = image;
        this.position = position;
        this.isTeacher = isTeacher;
        this.finishedDegree = finishedDegree;
        this.schoolGraduated = schoolGraduated;
        this.yearGraduated = yearGraduated;
        //this.schoolYear = schoolYear;
    }

    @Override
    public String toString(){
        return this.lastName + ", " + this.firstName + " " + this.middleName;
    }

    public String getFullName(){
        return this.lastName + ", " + this.firstName + " " + this.middleName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getPOB() {
        return POB;
    }

    public void setPOB(String POB) {
        this.POB = POB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getYearAdmitted() {
        return yearAdmitted;
    }

    public void setYearAdmitted(int yearAdmitted) {
        this.yearAdmitted = yearAdmitted;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFinishedDegree() {
        return finishedDegree;
    }

    public void setFinishedDegree(String finishedDegree) {
        this.finishedDegree = finishedDegree;
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

    public Object getPicture() {
        return picture;
    }

    public void setPicture(Object picture) {
        this.picture = picture;
    }

    public int getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(int isTeacher) {
        this.isTeacher = isTeacher;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getSchoolGraduated() {
        return schoolGraduated;
    }

    public void setSchoolGraduated(String schoolGraduated) {
        this.schoolGraduated = schoolGraduated;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getYearGraduated() {
        return yearGraduated;
    }

    public void setYearGraduated(int yearGraduated) {
        this.yearGraduated = yearGraduated;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class UserAccount {

    private int userID;
    private String userName;
    private String password;
    private String userType;
    private String status;
    private Employee employee;
    
    public String DEFAULT_PASSWORD = "123456";
    
    public UserAccount(int userID, String userName, String password, String userType, String status, Employee employee) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.status = status;
        this.employee = employee;
    }

    public UserAccount(String userName, String userType, Employee employee) {
        this.userName = userName;
        this.password = DEFAULT_PASSWORD;
        this.userType = userType;
        this.status = "Active";
        this.employee = employee;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}

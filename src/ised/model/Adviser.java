/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class Adviser {

    private int adviserID;
    private Class section;
    private Employee employee;

    public Adviser(int adviserID, Class section, Employee employee) {
        this.adviserID = adviserID;
        this.section = section;
        this.employee = employee;
    }

    public int getAdviserID() {
        return adviserID;
    }

    public void setAdviserID(int adviserID) {
        this.adviserID = adviserID;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Class getSection() {
        return section;
    }

    public void setSection(Class section) {
        this.section = section;
    }

    
}

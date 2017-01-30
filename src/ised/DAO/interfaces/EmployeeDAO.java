/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.interfaces;

import java.util.List;
import ised.model.Employee;
import ised.tools.ExceptionHandler;

/**
 *
 * @author ABDUL
 */
public interface EmployeeDAO {

    Employee addEmployee(Employee employee) throws ExceptionHandler;

    void editEmployee(Employee employee) throws ExceptionHandler;

    Employee getEmployee(int empId) throws ExceptionHandler;

    List<Employee> getEmployeeList() throws ExceptionHandler;

    List<Employee> getEmployeeList(String status) throws ExceptionHandler;

    List<Employee> getEmployeeList(String status, String search) throws ExceptionHandler;

    List<Employee> getFacultyList() throws ExceptionHandler;

    boolean checkEmployee(Employee employee) throws ExceptionHandler;

    void changeEmployeeStatus(Employee employee) throws ExceptionHandler;
}

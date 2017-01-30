/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import java.util.List;
import ised.DAO.implementation.EmployeeDAOImpl;
import ised.DAO.interfaces.EmployeeDAO;
import ised.model.Employee;
import ised.service.interfaces.EmployeeService;
import ised.tools.ExceptionHandler;

/**
 *
 * @author ABDUL
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDAO dao;

    public EmployeeServiceImpl() {
        dao = new EmployeeDAOImpl();
    }

    public Employee getEmployee(int empId) throws ExceptionHandler {
        return dao.getEmployee(empId);
    }

    public List<Employee> getEmployeeList() throws ExceptionHandler {
        return dao.getEmployeeList();
    }

    public List<Employee> getEmployeeList(String status) throws ExceptionHandler {
        return dao.getEmployeeList(status);
    }

    public List<Employee> getEmployeeList(String status, String search) throws ExceptionHandler {
        return dao.getEmployeeList(status, search);
    }

    public List<Employee> getFacultyList() throws ExceptionHandler {
        return dao.getFacultyList();
    }

    public Employee addEmployee(Employee employee) throws ExceptionHandler {
        return dao.addEmployee(employee);
    }

    public void editEmployee(Employee employee) throws ExceptionHandler {
        dao.editEmployee(employee);
    }

    public boolean checkEmployee(Employee employee) throws ExceptionHandler {
        return dao.checkEmployee(employee);
    }

    public void changeEmployeeStatus(Employee employee) throws ExceptionHandler {
        dao.changeEmployeeStatus(employee);
    }
}

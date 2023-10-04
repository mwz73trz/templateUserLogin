package com.techelevator.dao;


import com.techelevator.model.Employee;
import java.util.List;


public interface EmployeeDao {
    List<Employee> getAllEmployees();
    Employee getEmployeeByUser(String username);
//    void updateEmployeeInfo(Employee employee);
    Employee getEmployeeById(int employeeId);


}

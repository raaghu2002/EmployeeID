package com.wizzybox.employeIdCard.service;

import com.google.zxing.WriterException;
import com.wizzybox.employeIdCard.model.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee) throws WriterException, IOException;
    List<Employee> findAllEmployees();
    Employee getEmployeeById(int id);
    Employee updateEmployee(Employee employee , int id)throws WriterException, IOException;
    void deleteEmployee(int id);
}



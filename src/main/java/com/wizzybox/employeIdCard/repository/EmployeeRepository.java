package com.wizzybox.employeIdCard.repository;

import com.wizzybox.employeIdCard.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}

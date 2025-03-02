package com.wizzybox.employeIdCard.service.impl;

import com.google.zxing.WriterException;
import com.wizzybox.employeIdCard.exception.ResourceNotFoundException;
import com.wizzybox.employeIdCard.model.Employee;
import com.wizzybox.employeIdCard.repository.EmployeeRepository;
import com.wizzybox.employeIdCard.service.EmployeeService;
import com.wizzybox.employeIdCard.util.QRCodeGenerator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        super();
        this.employeeRepository = employeeRepository;

    }

    @Override
    public Employee saveEmployee(Employee employee) throws WriterException, IOException {

        int uniqueId;
        Random random = new Random();

        do {
            uniqueId = random.nextInt(900000) + 100000;

        } while (employeeRepository.existsById(uniqueId));

        employee.setId(uniqueId);
        String qrData = "ID : "+ uniqueId;
        String qrCodePath = QRCodeGenerator.generateQRCode(qrData, employee.getFirstName() + "." + employee.getLastName());
        employee.setQrCodePath(qrCodePath);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee employee, int id) throws WriterException, IOException {
        Employee existingEmployee = getEmployeeById(id);
//        String existingQrCodePath = existingEmployee.getQrCodePath();
//        File existingQrCodeFile = new File(existingQrCodePath);
//        if(existingQrCodeFile.exists()){
//            existingQrCodeFile.delete();
//        }

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhoneNumber(employee.getPhoneNumber());

        String qrData ="ID : "+ getEmployeeById(id) ;
        String qrCodePath = QRCodeGenerator.generateQRCode(qrData, employee.getFirstName() + "." + employee.getLastName());
        existingEmployee.setQrCodePath(qrCodePath);

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "Id", String.valueOf(id))
        );

        employeeRepository.deleteById(id);

        String qrCodePath = employee.getQrCodePath();
        File file = new File(qrCodePath);
        if(file.exists()){
            file.delete();
        }

    }


    @Override
    public Employee getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return employee.get();
        }else {
            throw new ResourceNotFoundException("Employee", "Id", "id");
        }
        //Lambda Expression
        //return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));



    }
}

package com.example.Bai02.service;

import com.example.Bai02.entity.Employee;

import java.util.List;

public interface EmployeeService {

    // Lấy tất cả nhân viên
    List<Employee> getAllEmployees();

    // Lấy nhân viên theo ID
    Employee getEmployeeById(Long id);

    // Lưu nhân viên
    Employee saveEmployee(Employee employee);

    // Xóa nhân viên theo ID
    void deleteEmployeeById(Long id);
}

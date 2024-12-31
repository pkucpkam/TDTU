package com.example.Bai02.service;

import com.example.Bai02.entity.Employee;
import com.example.Bai02.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Lấy tất cả nhân viên
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Lấy một nhân viên theo ID
    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);  // Trả về null nếu không tìm thấy
    }

    // Lưu nhân viên
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Xóa nhân viên theo ID
    @Override
    public void deleteEmployeeById(Long id) {
        // Trước khi xóa, kiểm tra xem nhân viên có tồn tại hay không
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        }
    }
}

package com.example.Bai02.repository;

import com.example.Bai02.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // JpaRepository cung cấp các phương thức cơ bản như findAll(), findById(), save(), deleteById()...
}

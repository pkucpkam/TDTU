package com.example.Bai02.controller;

import com.example.Bai02.entity.Employee;
import com.example.Bai02.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Hiển thị danh sách nhân viên
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "index"; // Trả về file index.html từ thư mục templates
    }

    // Hiển thị form thêm nhân viên
    @GetMapping("/add")
    public String addEmployeeForm() {
        return "add"; // Trả về file add.html từ thư mục templates
    }

    // Xử lý thêm nhân viên
    @PostMapping("/add")
    public String addEmployee(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String address,
                              @RequestParam String phone) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employee.setAddress(address);
        employee.setPhone(phone);
        employeeService.saveEmployee(employee); // Lưu nhân viên vào cơ sở dữ liệu
        return "redirect:/employees"; // Quay về trang danh sách nhân viên
    }

    // Hiển thị form chỉnh sửa nhân viên
    @GetMapping("/edit/{id}")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return "redirect:/employees"; // Nếu không tìm thấy nhân viên, quay lại trang danh sách
        }
        model.addAttribute("employee", employee); // Truyền thông tin nhân viên vào model
        return "edit"; // Trả về file edit.html từ thư mục templates
    }

    // Xử lý cập nhật thông tin nhân viên
    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String email,
                                 @RequestParam String address,
                                 @RequestParam String phone) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            employee.setName(name);
            employee.setEmail(email);
            employee.setAddress(address);
            employee.setPhone(phone);
            employeeService.saveEmployee(employee); // Cập nhật thông tin nhân viên
        }
        return "redirect:/employees"; // Quay lại trang danh sách nhân viên
    }

    // Xóa nhân viên theo ID
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id); // Xóa nhân viên
        return "redirect:/employees"; // Quay về trang danh sách nhân viên
    }
}

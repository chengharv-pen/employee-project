package com.example.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/get-employees")
    public String getEmployees() {
        return "get-employees";
    }
    @RequestMapping("/add-employees")
    public String addEmployees() {
        return "add-employees";
    }
    @RequestMapping("/update-employees")
    public String updateEmployees() {
        return "update-employees";
    }
    @RequestMapping("/delete-employees")
    public String deleteEmployees() {
        return "delete-employees";
    }
}

package com.example.employee.controller;

import java.util.List;

import com.example.employee.exceptions.EmployeeNotFoundException;
import com.example.employee.exceptions.InvalidEmployeeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    EmployeeService empService;

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    /**
     *
     *  Methods for the four REST APIs
     *
     */
    @RequestMapping(value="/employees", method=RequestMethod.POST)
    public Employee createEmployee(@RequestBody Employee emp) {
        //Checking if emp is valid (no blank entries)
        if(emp.getFirstName().compareTo("") == 0 || emp.getLastName().compareTo("") == 0 || emp.getEmail().compareTo("") == 0) {
            logger.warn("Failed to CREATE Employee. Invalid Employee.");
            throw new InvalidEmployeeException();
        }

        logger.info("Able to CREATE Employees. Valid Employee.");
        return empService.createEmployee(emp);
    }

    @RequestMapping(value="/employees", method=RequestMethod.GET)
    public List<Employee> getEmployees() {
        //Checking if the List of Employees is not empty
        if (empService.getEmployees().isEmpty()) { //isEmpty() is a method of List<T>
            logger.warn("Failed to GET Employees. There are no Employees in the database.");
            throw new EmployeeNotFoundException();
        }

        logger.info("Able to GET Employees. There are Employees in the database.");
        return empService.getEmployees();
    }

    @RequestMapping(value="/employees/{empId}", method=RequestMethod.PUT)
    public Employee updateEmployees(@PathVariable(value = "empId") Long id, @RequestBody Employee empDetails) {
        //This checks if empDetails is null
        if (empDetails.getFirstName().compareTo("") == 0 || empDetails.getLastName().compareTo("") == 0 || empDetails.getEmail().compareTo("") == 0) {
            logger.warn("Failed to UPDATE Employee. Invalid Employee.");
            throw new InvalidEmployeeException();
        }
        //This checks if the service request is valid - is there a better way to do this?
        Employee updatedEmployee = null;
        try {
            updatedEmployee = empService.updateEmployee(id, empDetails);
        } catch (Exception e) {
            logger.warn("Failed to UPDATE Employee. Employee at that ID does not exist.");
            throw new EmployeeNotFoundException();
        }

        logger.info("Able to UPDATE Employee. ID does exist and valid Employee.");
        return updatedEmployee;
    }

    @RequestMapping(value="/employees/{empId}", method=RequestMethod.DELETE)
    public void deleteEmployees(@PathVariable(value = "empId") Long id) {
        //For this one, should we even check if the id is valid?
        try {
            Employee dummy = new Employee();
            empService.updateEmployee(id, dummy);
        } catch (Exception e) {
            logger.warn("Failed to DELETE Employee. Employee at that ID does not exist.");
            throw new EmployeeNotFoundException();
        }

        logger.info("Able to DELETE Employee. Employee with associated ID was not NULL.");
        empService.deleteEmployee(id);
    }

}

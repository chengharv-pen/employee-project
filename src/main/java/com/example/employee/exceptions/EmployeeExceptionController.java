package com.example.employee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionController {
    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public ResponseEntity<Object> exception(EmployeeNotFoundException e) {
        return new ResponseEntity<>("Employee not found!", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = InvalidEmployeeException.class)
    public ResponseEntity<Object> exception(InvalidEmployeeException e) {
        return new ResponseEntity<>("Invalid employee!", HttpStatus.NOT_FOUND);
    }

}

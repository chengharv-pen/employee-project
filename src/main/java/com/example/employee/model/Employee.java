package com.example.employee.model;

/**
 * It was javax.persistence before, but in Spring Boot 3, they say
 * that the new import is jakarta
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "employee_table")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emp_id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    public Employee(String f, String l, String e) {
        this.firstName = f;
        this.lastName = l;
        this.email = e;
    }

    public Employee() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
    }

//
//    @Override
//    public String toString() {
//        return "firstName:" + this.getFirstName() +
//                "\nlastName:" + this.getLastName() +
//                "\nemail:" + this.getEmail();
//    }

}

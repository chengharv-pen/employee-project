package com.example.employee;

import com.example.employee.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data.sql") // We want to populate the H2 database with some dummy data before trying to test... hence why all this H2 database stuff
public class EmployeeControllerIT {

    @Autowired
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public EmployeeControllerIT(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void rootWhenUnauthenticatedThen401() throws Exception {
        mockMvc.perform(get("/employees"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rootWhenAuthenticatedThenSaysHelloUser() throws Exception {
        mockMvc.perform(post("/token")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    void shouldGetEmployees() throws Exception {
        //There is no assert test needed, since we are just checking for the Body of the MockHttpServletResponse
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/employees")
                        .with(httpBasic("user", "password")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldCreateEmployees() throws Exception {
        // Given
        Employee chengEmployee = new Employee("Chengharv", "Pen", "test@test.com");

        // When
        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/employees")
                                .with(httpBasic("user", "password"))
                                .content(objectMapper.writeValueAsString(chengEmployee))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Then
        assertEquals(chengEmployee.getFirstName(), JsonPath.read(mvcResult.getResponse().getContentAsString(),"firstName"));
    }

    @Test
    void shouldUpdateEmployees() throws Exception {
        // Given
        Employee chengEmployee = new Employee("Chengharv", "Pen", "test@test.com");

        // When
        //Requesting put on ID = 1, then making a JSON that contains chengEmployee, then checking the result
        //I think I need to return the result, so I can use it to check its firstName for the assert
        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/employees/1")
                                .with(httpBasic("user", "password"))
                                .content(objectMapper.writeValueAsString(chengEmployee))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Then
        //Assert for "Chengharv" is equal to the returned ID's first name
        assertEquals(chengEmployee.getFirstName(), JsonPath.read(mvcResult.getResponse().getContentAsString(), "firstName"));
    }

    @Test
    void shouldDeleteEmployees() throws Exception {
        // Given - The stuff in the H2 database
        // When
        //Requesting DELETE on ID = 1. There is no return value so no assert test is needed.
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/employees/1")
                        .with(httpBasic("user", "password")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        //Here, we are requesting GET to really make sure that there's nothing in the mock DB at a certain ID
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/employees")
                        .with(httpBasic("user", "password")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

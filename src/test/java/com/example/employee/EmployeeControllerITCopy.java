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

/**
 *
 * "Application.properties is the standard properties file that Spring Boot automatically takes up when executing an application and is located in the src/main/resources folder.
 *  If we wish to utilise alternative properties for testing, we may override the properties file in the main folder by adding a similar file in src/test/resources."
 *
 */

@SpringBootTest //(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data.sql") // We want to populate the H2 database with some dummy data before trying to test... hence why all this H2 database stuff
//@ExtendWith(SpringExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) //This annotation will make it so that @BeforeAll does not need a static method.
public class EmployeeControllerITCopy {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public EmployeeControllerITCopy(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

//    @BeforeAll
//    void setup() throws Exception {
//        Employee danielEmployee = new Employee("Daniel", "Pen", "test@test.com");
//        //We want to request POST for danielEmployee
//        mockMvc
//                .perform(
//                        MockMvcRequestBuilders.post("/api/employees")
//                                .content(objectMapper.writeValueAsString(danielEmployee))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                //.andDo(MockMvcResultHandlers.print()) // Uncomment this line and run the class to really see what's going on
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//    }

    /**
     *
     * We want to populate the H2 database with some dummy data before trying to test... hence why all this H2 database stuff
     *
     */

    @Test
    void shouldGetEmployees() throws Exception {
        //There is no assert test needed, since we are just checking for the Body of the MockHttpServletResponse
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/employees"))
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
//        Employee danielEmployee = new Employee("Daniel", "Pen", "test@test.com");
//        MvcResult mvcResultBefore = mockMvc
//                .perform(
//                        MockMvcRequestBuilders.post("/api/employees")
//                                .content(objectMapper.writeValueAsString(danielEmployee))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        int ID = JsonPath.read(mvcResultBefore.getResponse().getContentAsString(), "id");
        Employee chengEmployee = new Employee("Chengharv", "Pen", "test@test.com");

        // When
        //Requesting put on ID, then making a JSON that contains chengEmployee, then checking the result
        //I think I need to return the result, so I can use it to check its firstName for the assert
        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/employees/1")
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
//        // Given
//        Employee danielEmployee = new Employee("Daniel", "Pen", "test@test.com");
//        MvcResult mvcResultBefore = mockMvc
//                .perform(
//                        MockMvcRequestBuilders.post("/api/employees")
//                                .content(objectMapper.writeValueAsString(danielEmployee))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        int ID = JsonPath.read(mvcResultBefore.getResponse().getContentAsString(), "id");

        // When
        //Requesting DELETE. There is no return value so no assert test is needed.
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/employees/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        //Here, we are requesting GET to really make sure that there's nothing in the mock DB at a certain ID
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/employees"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}


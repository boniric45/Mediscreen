package fr.mediscreen.demographique_patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.mediscreen.demographique_patient.model.Patient;
import fr.mediscreen.demographique_patient.service.PatientService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPatient {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void getAllPatientIsEmptyTest() throws Exception {

        // Given
        List<Patient> patientList = patientService.findAll();
        for (Patient patientIt : patientList) {
            patientService.deleteById(patientIt.getId());
        }

        // When
        mockMvc.perform(get("/patient/all"))
                .andExpect(status().isNoContent());

        // Then
        assertEquals(0, patientList.size());
    }

    @Test
    @Order(2)
    public void getPatientByIdIsNotFoundTest() throws Exception {

        // Given
        int mongoId = 0;
        List<Patient> patientList = patientService.findAll();

        // When
        for (Patient patientObj : patientList) {
            mongoId = patientObj.getId();
        }

        // Then
        mockMvc.perform(get("/patient/" + mongoId + 1))
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(3)
    public void createNewPatientTest() throws Exception {

        String url = "http://localhost:8081/patient/add?family=TestNone&given=Test&dob=1966-12-31&sex=F&address=1 Brookside St&phone=100-222-3333";

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Patient Inserted"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void getAllPatientTest() throws Exception {

        // Given
        List<Patient> patientList = patientService.findAll();

        // When
        mockMvc.perform(get("/patient/all"))
                .andExpect(status().isOk());

        // Then
        assertTrue(patientList.size() > 0);
    }

    @Test
    @Order(5)
    public void getPatientByIdTest() throws Exception {

        // Given
        int id = 0;
        List<Patient> patientList = patientService.findAll();

        for (Patient patientObj : patientList) {
            id = patientObj.getId();
        }
        // When
        mockMvc.perform(get("/patient/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test")));

        // Then
        assertTrue(patientList.size() > 0);
    }

    @Test
    @Order(6)
    public void getUpdatePatientByIdTest() throws Exception {

        // Given
        LocalDate date = LocalDate.parse("1975-01-03");
        Patient patient = new Patient();
        patient.setFamily("TestFamily");
        patient.setGiven("TestGiven");
        patient.setDob(date);
        patient.setSex("F");
        patient.setAddress("7 boulevard Martin 92 Nanterre");
        patient.setPhone("0101010101");

        // When
        Patient result = patientService.save(patient);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/patient/update/{id}", result.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("family", "family", "given", "given", "dob", "2002-01-10", "sex", "F", "address", "Wall Street", "phone", "1111-2255-45")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.family").value("family"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.given").value("given"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dob").value("2002-01-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sex").value("F"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Wall Street"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("1111-2255-45"));

        patientService.deleteById(result.getId());

    }

    @Test
    @Order(7)
    public void deletePatientByIdTest() throws Exception {

        // Given
        LocalDate date = LocalDate.parse("1975-01-03");
        Patient patient = new Patient(1, "TestGiven", "TestFamily", date, "F", "7 boulevard Martin 92 Nanterre", "0101010101");

        // When
        Patient patient1 = patientService.save(patient);
        System.out.println(patient1.getId());

        // Then
        mockMvc.perform(delete("/patient/" + patient1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    public void deletePatientByIdExceptionTest() throws Exception {

        mockMvc.perform(delete("/patient/" + 0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }

    private String buildUrlEncodedFormEntity(String... params) {
        if ((params.length % 2) > 0) {
            throw new IllegalArgumentException("Need to give an even number of parameters");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < params.length; i += 2) {
            if (i > 0) {
                result.append('&');
            }
            try {
                result.
                        append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).
                        append('=').
                        append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8.name()));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }

}

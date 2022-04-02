package fr.mediscreen.rapport.controller;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import fr.mediscreen.rapport.beans.NoteBean;
import fr.mediscreen.rapport.beans.PatientBean;
import fr.mediscreen.rapport.interfaces.RapportService;
import fr.mediscreen.rapport.proxies.NoteProxy;
import fr.mediscreen.rapport.proxies.PatientProxy;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RapportControllerTest {

    @Autowired
    PatientProxy patientProxy;

    @Autowired
    NoteProxy noteProxy;


    private int testNoneId;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        // Add Patient
        patientProxy.addPatient("TestNone", "Test", "1966-12-31", "F", "1 Brookside St", "100-222-3333");
        // Get Patient Id
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        for (PatientBean patientBean : patientBeanList) {
            if (patientBean.getFamily().equals("TestNone")) {
                testNoneId = patientBean.getId();
            }
        }
        // Add Note
        noteProxy.addNote(testNoneId, "Patient: TestNone Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level");
    }

    @After
    public void after() {
        patientProxy.deletePatient(testNoneId);
        List<NoteBean> noteList = noteProxy.listNote(testNoneId);
        for (NoteBean note : noteList) {
            noteProxy.deleteNote(note.getId());
        }
    }

    @Test
    public void testRiskById() throws Exception {
        init();
        mockMvc.perform(get("/risk/" + testNoneId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("NONE"));
        after();
    }

    @Test
    public void testPatientById() throws Exception {
        init();
        mockMvc.perform(get("/patient/" + testNoneId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        after();
    }

    @Test
    public void testAgeByPatientId() throws Exception {
        init();
        mockMvc.perform(get("/rapport/age/" + testNoneId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("55"))
                .andExpect(status().isOk());

        after();
    }

    @Test
    public void testGetTriggerOfNoteToPatient() throws Exception {

        init();

        mockMvc.perform(get("/rapport/trigger/" + testNoneId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("1"))
                .andExpect(status().isOk());

        after();
    }

}

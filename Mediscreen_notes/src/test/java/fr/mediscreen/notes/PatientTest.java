package fr.mediscreen.notes;

import fr.mediscreen.notes.beans.PatientBean;
import fr.mediscreen.notes.controller.NotesController;
import fr.mediscreen.notes.service.PatientService;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PatientTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private MockMvc mockMvc;

    @Test // Microservice Patient and PostGres must be up
    public void getPatientById() {

        // Given
        PatientBean patient = new PatientBean();

        // When
        PatientBean patientBean = patientService.findPatientById(1);
        patient.setId(patientBean.getId());
        patient.setNom(patientBean.getNom());
        patient.setPrenom(patientBean.getPrenom());
        patient.setAdresse(patientBean.getAdresse());
        patient.setGenre(patientBean.getGenre());
        patient.setNaissance(patientBean.getNaissance());
        patient.setTelephone(patientBean.getTelephone());

        // Then
        Assertions.assertEquals(patient.getId(), patientBean.getId());
        Assertions.assertEquals(patient.getNom(), patientBean.getNom());
        Assertions.assertEquals(patient.getAdresse(), patientBean.getAdresse());
        Assertions.assertEquals(patient.getGenre(), patientBean.getGenre());
        Assertions.assertEquals(patient.getNaissance(), patientBean.getNaissance());
        Assertions.assertEquals(patient.getTelephone(), patientBean.getTelephone());

    }

    @Test
    public void testPatientToString(){

        PatientBean patient = patientService.findPatientById(1);
        String expected = patient.toString();
        Assertions.assertEquals(expected,patient.toString());
    }

}

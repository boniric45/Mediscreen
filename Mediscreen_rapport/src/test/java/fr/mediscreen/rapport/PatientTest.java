package fr.mediscreen.rapport;

import fr.mediscreen.rapport.beans.PatientBean;
import fr.mediscreen.rapport.proxies.PatientProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class PatientTest {

    @Test
    public void testPatient() {

        // Given
        PatientBean patient = new PatientBean();

        // When
        patient.setId(1);
        patient.setFamily("Family");
        patient.setGiven("Given");
        patient.setAddress("10 Street Manhattan");
        patient.setDob(LocalDate.parse("1965-01-01"));
        patient.setSex("F");
        patient.setPhone("466-54-12");

        // Then
        Assertions.assertEquals(1, patient.getId());
        Assertions.assertEquals("Family", patient.getFamily());
        Assertions.assertEquals("Given", patient.getGiven());
        Assertions.assertEquals("10 Street Manhattan", patient.getAddress());
        Assertions.assertEquals(LocalDate.parse("1965-01-01"), patient.getDob());
        Assertions.assertEquals("F", patient.getSex());
        Assertions.assertEquals("466-54-12", patient.getPhone());
    }


}

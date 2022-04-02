package fr.mediscreen.demographique_patient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"application-test.properties"})
public class TestApplicationPatient {

    @Test
    void contextLoads() {
    }

    @Test
    public void main() {
        DemographiquePatientApplication.main(new String[]{});
    }
}

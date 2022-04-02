package fr.mediscreen.rapport.services;

import fr.mediscreen.rapport.beans.PatientBean;
import fr.mediscreen.rapport.service.AgeCalculatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AgeCalculatorImplTest {

    @Autowired
    AgeCalculatorImpl ageCalculator;

    @Test
    public void testAgeCalculator() {

        // Given
        LocalDate birthDate = LocalDate.ofEpochDay(1965 - 5 - 2);
        int age = 0;

        // When
        age = ageCalculator.ageCalculate(birthDate);

        // Then
        Assertions.assertEquals(age, 46);

    }

    @Test
    public void testAgeCalculatorIsNotValid() {

        // Given
        LocalDate birthDate = LocalDate.ofEpochDay(20723 - 5 - 12);
        PatientBean patient = new PatientBean();

        // When
        patient.setDob(birthDate);
        patient.setId(1);

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            ageCalculator.ageCalculate(birthDate);
            ageCalculator.getAge(patient.getId());
        });
    }


}

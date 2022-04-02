package fr.mediscreen.rapport.service;

import fr.mediscreen.rapport.beans.PatientBean;
import fr.mediscreen.rapport.interfaces.AgeCalculator;
import fr.mediscreen.rapport.proxies.PatientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AgeCalculatorImpl implements AgeCalculator {

    private static final Logger logger = LoggerFactory.getLogger(AgeCalculatorImpl.class);

    @Autowired
    PatientProxy patientProxy;

    @Override
    public int ageCalculate(LocalDate birthDate) {

        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age <= 0) {
            logger.error("The age of patient is not valid");
            throw new IllegalArgumentException("Person's birthdate isn't valid");
        }
        logger.info("The age of patient is: " + age);
        return age;
    }

    @Override
    public int getAge(Integer id) {

        PatientBean patient = patientProxy.getPatientById(id);
        LocalDate birthDate = patient.getDob();
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age <= 0) {
            logger.error("The age of patient is not valid");
            throw new IllegalArgumentException("Person's birthdate isn't valid");
        }
        logger.info("The age of patient is: " + age);
        return age;

    }

}

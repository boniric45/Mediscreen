package fr.mediscreen.rapport.service;

import fr.mediscreen.rapport.beans.PatientBean;
import fr.mediscreen.rapport.interfaces.AgeCalculator;
import fr.mediscreen.rapport.interfaces.RapportService;
import fr.mediscreen.rapport.interfaces.TriggerCalculator;
import fr.mediscreen.rapport.model.Risk;
import fr.mediscreen.rapport.proxies.NoteProxy;
import fr.mediscreen.rapport.proxies.PatientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RapportServiceImpl implements RapportService {

    private static final Logger logger = LoggerFactory.getLogger(RapportServiceImpl.class);

    @Autowired
    private PatientProxy patientProxy;

    @Autowired
    private NoteProxy noteProxy;

    @Autowired
    private AgeCalculator ageCalculator;

    @Autowired
    private TriggerCalculator triggerCalculator;

    @Override
    public Risk getDiabeteLevelRisk(int id) {

        logger.info("Get diabetes level risk");
        PatientBean patient = patientProxy.getPatientById(id);
        int countTriggerInNote = triggerCalculator.calculateTriggerInNotes(id);
        int age = ageCalculator.ageCalculate(patient.getDob());
        String sex = patient.getSex();
        Risk resultRisk = null;

        // The diabete risk is: None
        if ((sex.equals("M") || sex.equals("F")) && age > 30 && countTriggerInNote <= 1) {
            logger.info("The genre is: " + sex);
            logger.info("The diabete risk is: " + Risk.NONE);
            resultRisk = Risk.NONE;
        }

        // The diabete risk is:  Borderline
        else if ((sex.equals("M") || sex.equals("F")) && age > 30 && countTriggerInNote == 2) {
            logger.info("The genre is: " + sex);
            logger.info("The diabete risk is: " + Risk.BORDERLINE);
            resultRisk = Risk.BORDERLINE;
        }

        // The diabete risk is:  Danger
        else if (sex.equals("M") && age <= 30 && countTriggerInNote >= 3 && countTriggerInNote < 5) {
            logger.info("The genre is: " + sex);
            logger.info("The diabete risk is: " + Risk.IN_DANGER);
            resultRisk = Risk.IN_DANGER;
        } else if (sex.equals("F") && age <= 30 && countTriggerInNote >= 4 && countTriggerInNote < 7) {
            logger.info("The genre is: " + sex);
            logger.info("The diabete risk is: " + Risk.IN_DANGER);
            resultRisk = Risk.IN_DANGER;
        } else if (age > 30 && countTriggerInNote >= 6 && countTriggerInNote < 8) {
            logger.info("The genre is: " + sex);
            logger.info("The diabete risk is: " + Risk.IN_DANGER);
            resultRisk = Risk.IN_DANGER;
        }

        // The diabete risk is:  Early onset
        else if (sex.equals("M") && age <= 30 && countTriggerInNote >= 5 && countTriggerInNote < 8) {
            logger.info("The genre is: " + sex);
            logger.info("The diabete risk is: " + Risk.EARLY_ONSET);
            resultRisk = Risk.EARLY_ONSET;
        } else if (sex.equals("F") && age <= 30 && countTriggerInNote >= 7) {
            logger.info("The genre is: " + sex);
            logger.info("The diabete risk is: " + Risk.EARLY_ONSET);
            resultRisk = Risk.EARLY_ONSET;
        } else if ((sex.equals("M") || sex.equals("F")) && age > 30 && countTriggerInNote >= 8) {
            logger.info("The genre is: " + sex);
            logger.info("The diabete risk is: " + Risk.EARLY_ONSET);
            return Risk.EARLY_ONSET;
        }

        return resultRisk;
    }

    public PatientBean findPatientById(int id) {
        return patientProxy.getPatientById(id);
    }

    @Override
    public String findRapportById(Integer id) {
        PatientBean patientBean = patientProxy.getPatientById(id);
        String risk = String.valueOf(getDiabeteLevelRisk(id));
        int age = ageCalculator.getAge(id);
        return "Patient: " + patientBean.getGiven() + " " + patientBean.getFamily() + " (age " + age + ") diabetes assessment is: " + risk;
    }

    @Override
    public String findRapportByFamilyName(String familyName) {
        List<PatientBean> patientList = patientProxy.getAllPatient();
        PatientBean patient = new PatientBean();
        for (PatientBean patientIt : patientList) {
            if (patientIt.getFamily().equals(familyName)) {
                patient = patientIt;
            }
        }

        String risk = String.valueOf(getDiabeteLevelRisk(patient.getId()));
        int age = ageCalculator.getAge(patient.getId());
        return "Patient: " + patient.getGiven() + " " + patient.getFamily() + " (age " + age + ") diabetes assessment is: " + risk;
    }

}

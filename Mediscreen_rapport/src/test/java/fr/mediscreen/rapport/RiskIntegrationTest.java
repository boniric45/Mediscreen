package fr.mediscreen.rapport;

import fr.mediscreen.rapport.beans.NoteBean;
import fr.mediscreen.rapport.beans.PatientBean;
import fr.mediscreen.rapport.interfaces.AgeCalculator;
import fr.mediscreen.rapport.interfaces.RapportService;
import fr.mediscreen.rapport.interfaces.TriggerCalculator;
import fr.mediscreen.rapport.model.Risk;
import fr.mediscreen.rapport.proxies.NoteProxy;
import fr.mediscreen.rapport.proxies.PatientProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Locale;

import static fr.mediscreen.rapport.model.Risk.NONE;

@SpringBootTest // Must have notes and patient up
public class RiskIntegrationTest {

    @Autowired
    RapportService rapportService;

    @Autowired
    PatientProxy patientProxy;

    @Autowired
    NoteProxy noteProxy;

    @Autowired
    AgeCalculator ageCalculator;

    @Autowired
    TriggerCalculator triggerCalculator;

    @Test
    public void testGetRapportByPatientId() {

        // Given
        PatientBean patientBeanResult = new PatientBean();
        String family = "TestNone";
        String given = "Test";
        String dob = "1975-01-03";
        String phone = "4654654";
        String sex = "F";
        String address = "10 Th Broadway Street";
        String notes = "Patient: TestNone Practitioner's notes/recommendations: Patient states";

        // Create Patient
        patientProxy.addPatient(family, given, dob, sex, address, phone);

        // Get Patient Id
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        for (PatientBean patientBean : patientBeanList) {
            if (patientBean.getGiven().equals(given) && patientBean.getFamily().equals(family)) {
                patientBeanResult = patientBean;
            }
        }

        // Create Note
        noteProxy.addNote(patientBeanResult.getId(), notes);

        // When
        Risk risk = rapportService.getDiabeteLevelRisk(patientBeanResult.getId());  // Générate Rapport
        String result = risk.getRapport();

        // Then
        Assertions.assertEquals(NONE, risk);
        Assertions.assertEquals(risk.toString(), result.toUpperCase(Locale.ROOT));

        // Delete Note
        List<NoteBean> noteList = noteProxy.listNote(patientBeanResult.getId());
        for (NoteBean noteBean : noteList) {
            if (noteBean.getPatId() == patientBeanResult.getId()) {
                noteProxy.deleteNote(noteBean.getId());
            }
        }

        // Delete Patient
        patientProxy.deletePatient(patientBeanResult.getId());

    }

    @Test
    public void testGetAge() {

        // Given
        PatientBean patientBeanResult = new PatientBean();
        String family = "TestNonez";
        String given = "Test";
        String dob = "1971-08-14";
        String phone = "4654654";
        String sex = "F";
        String address = "10THBroadway";
        int age = 0;
        String notes = "Patient: TestNone Practitioner's notes/recommendations: Patient states";

        // Create Patient
        patientProxy.addPatient(family, given, dob, sex, address, phone);

        // Get Patient Id
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        for (PatientBean patientBean : patientBeanList) {
            if (patientBean.getGiven().equals(given) && patientBean.getFamily().equals(family)) {
                patientBeanResult = patientBean;
            }
        }

        // When
        age = ageCalculator.ageCalculate(patientBeanResult.getDob());

        // Then
        Assertions.assertEquals(50, age);

        // Delete Patient
        patientProxy.deletePatient(patientBeanResult.getId());

    }

    @Test
    public void testGetTriggerOfNoteToPatient() {

        // Given
        PatientBean patientBeanResult = new PatientBean();
        String family = "TestNonez";
        String given = "Test";
        String dob = "1975-01-03";
        String phone = "4654654";
        String sex = "F";
        String address = "10THBroadway";

        String notes = "Patient: TestInDanger Practitioner's notes/recommendations: Patient states Abnormal Cholesterol";

        // Create Patient
        patientProxy.addPatient(family, given, dob, sex, address, phone);

        // Get Patient Id
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        for (PatientBean patientBean : patientBeanList) {
            if (patientBean.getGiven().equals(given) && patientBean.getFamily().equals(family)) {
                patientBeanResult = patientBean;
            }
        }

        // Create Note
        noteProxy.addNote(patientBeanResult.getId(), notes);

        // When
        int triggerCount = triggerCalculator.calculateTriggerInNotes(patientBeanResult.getId());

        // Then
        Assertions.assertEquals(2, triggerCount);

        // Delete Note
        List<NoteBean> noteList = noteProxy.listNote(patientBeanResult.getId());
        for (NoteBean noteBean : noteList) {
            if (noteBean.getPatId() == patientBeanResult.getId()) {
                noteProxy.deleteNote(noteBean.getId());
            }
        }

        // Delete Patient
        patientProxy.deletePatient(patientBeanResult.getId());
    }

}

package fr.mediscreen.rapport;

import fr.mediscreen.rapport.beans.NoteBean;
import fr.mediscreen.rapport.beans.PatientBean;
import fr.mediscreen.rapport.interfaces.RapportService;
import fr.mediscreen.rapport.model.Rapport;
import fr.mediscreen.rapport.proxies.NoteProxy;
import fr.mediscreen.rapport.proxies.PatientProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class RapportTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientProxy patientProxy;

    @Autowired
    private NoteProxy noteProxy;

    @Autowired
    RapportService rapportService;

    @Test
    public void testRapportModel() {

        // Given
        Rapport rapport = new Rapport();

        // When
        rapport.setFamily("Family");
        rapport.setGiven("Given");
        rapport.setAge(18);
        rapport.setRisk("None");

        // Then
        Assertions.assertEquals("Family", rapport.getFamily());
        Assertions.assertEquals("Given", rapport.getGiven());
        Assertions.assertEquals(18, rapport.getAge());
        Assertions.assertEquals("None", rapport.getRisk());
    }

    @Test
    public void testGetRapportById() throws Exception {

        int testNoneId = 0;
        int testBorderlineId = 0;
        int testInDangerId = 0;
        int testEarlyOnsetId = 0;

        // Add Patients
        patientProxy.addPatient("TestNone", "Test", "1966-12-31", "F", "1 Brookside St", "100-222-3333");
        patientProxy.addPatient("TestBorderline", "Test", "1945-06-24", "M", "2 High St", "200-333-4444");
        patientProxy.addPatient("TestInDanger", "Test", "2004-06-18", "M", "3 Club Road", "300-444-5555");
        patientProxy.addPatient("TestEarlyOnset", "Test", "2002-06-28", "F", "4 Valley Dr", "400-555-6666");

        // Get Patient Id
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        for (PatientBean patientBean : patientBeanList) {

            if (patientBean.getFamily().equals("TestNone")) {
                testNoneId = patientBean.getId();
            } else if (patientBean.getFamily().equals("TestBorderline")) {
                testBorderlineId = patientBean.getId();
            } else if (patientBean.getFamily().equals("TestInDanger")) {
                testInDangerId = patientBean.getId();
            } else if (patientBean.getFamily().equals("TestEarlyOnset")) {
                testEarlyOnsetId = patientBean.getId();
            }
        }

        // Add Notes
        noteProxy.addNote(testNoneId, "Patient: TestNone Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level");
        noteProxy.addNote(testBorderlineId, "Patient: TestBorderline Practitioner's notes/recommendations: Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late");
        noteProxy.addNote(testBorderlineId, "Patient: TestBorderline Practitioner's notes/recommendations: Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic");
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they are short term Smoker");
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high");
        noteProxy.addNote(testEarlyOnsetId, "TestEarlyOnset Practitioner's notes/recommendations: Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication");
        noteProxy.addNote(testEarlyOnsetId, "Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that they are experiencing back pain when seated for a long time");
        noteProxy.addNote(testEarlyOnsetId, "Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        noteProxy.addNote(testEarlyOnsetId, "Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction");

        // Get Rapport
        mockMvc.perform(post("/assess/id?patId=" + testNoneId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestNone"));

        mockMvc.perform(post("/assess/id?patId=" + testBorderlineId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestBorderline"));

        mockMvc.perform(post("/assess/id?patId=" + testEarlyOnsetId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestInDanger"));

        mockMvc.perform(post("/assess/id?patId=" + testInDangerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestEarlyOnset"));


        // Delete Patient
        patientProxy.deletePatient(testNoneId);
        patientProxy.deletePatient(testBorderlineId);
        patientProxy.deletePatient(testInDangerId);
        patientProxy.deletePatient(testEarlyOnsetId);

        // Delete Note
        List<NoteBean> noteListTestNone = noteProxy.listNote(testNoneId);
        for (NoteBean noteBean1 : noteListTestNone) {
            noteProxy.deleteNote(noteBean1.getId());
        }

        List<NoteBean> noteListTestBorderline = noteProxy.listNote(testBorderlineId);
        for (NoteBean noteBean2 : noteListTestBorderline) {
            noteProxy.deleteNote(noteBean2.getId());
        }

        List<NoteBean> noteListTestDanger = noteProxy.listNote(testInDangerId);
        for (NoteBean noteBean3 : noteListTestDanger) {
            noteProxy.deleteNote(noteBean3.getId());
        }

        List<NoteBean> noteListTestEarly = noteProxy.listNote(testEarlyOnsetId);
        for (NoteBean noteBean4 : noteListTestEarly) {
            noteProxy.deleteNote(noteBean4.getId());
        }
    }

    @Test
    public void testGetRapportByFamilyName() throws Exception {

        int testNoneId = 0;
        int testBorderlineId = 0;
        int testInDangerId = 0;
        int testEarlyOnsetId = 0;

        // Add Patients
        patientProxy.addPatient("TestNone", "Test", "1966-12-31", "F", "1 Brookside St", "100-222-3333");
        patientProxy.addPatient("TestBorderline", "Test", "1945-06-24", "M", "2 High St", "200-333-4444");
        patientProxy.addPatient("TestInDanger", "Test", "2004-06-18", "M", "3 Club Road", "300-444-5555");
        patientProxy.addPatient("TestEarlyOnset", "Test", "2002-06-28", "F", "4 Valley Dr", "400-555-6666");

        // Get Patient Id
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        for (PatientBean patientBean : patientBeanList) {

            if (patientBean.getFamily().equals("TestNone")) {
                testNoneId = patientBean.getId();
            } else if (patientBean.getFamily().equals("TestBorderline")) {
                testBorderlineId = patientBean.getId();
            } else if (patientBean.getFamily().equals("TestInDanger")) {
                testInDangerId = patientBean.getId();
            } else if (patientBean.getFamily().equals("TestEarlyOnset")) {
                testEarlyOnsetId = patientBean.getId();
            }
        }
        // Add Notes
        noteProxy.addNote(testNoneId, "Patient: TestNone Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level");
        noteProxy.addNote(testBorderlineId, "Patient: TestBorderline Practitioner's notes/recommendations: Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late");
        noteProxy.addNote(testBorderlineId, "Patient: TestBorderline Practitioner's notes/recommendations: Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic");
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they are short term Smoker");
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high");
        noteProxy.addNote(testEarlyOnsetId, "TestEarlyOnset Practitioner's notes/recommendations: Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication");
        noteProxy.addNote(testEarlyOnsetId, "Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that they are experiencing back pain when seated for a long time");
        noteProxy.addNote(testEarlyOnsetId, "Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        noteProxy.addNote(testEarlyOnsetId, "Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction");

        // Get Rapport
        mockMvc.perform(post("/assess/familyName?familyName=TestNone")
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestNone"));

        mockMvc.perform(post("/assess/familyName?familyName=TestBorderline")
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestBorderline"));

        mockMvc.perform(post("/assess/familyName?familyName=TestInDanger")
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestInDanger"));

        mockMvc.perform(post("/assess/familyName?familyName=TestEarlyOnset")
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestEarlyOnset"));

        // Delete Patient
        patientProxy.deletePatient(testNoneId);
        patientProxy.deletePatient(testBorderlineId);
        patientProxy.deletePatient(testInDangerId);
        patientProxy.deletePatient(testEarlyOnsetId);

        // Delete Note
        List<NoteBean> noteListTestNone = noteProxy.listNote(testNoneId);
        for (NoteBean noteBean1 : noteListTestNone) {
            noteProxy.deleteNote(noteBean1.getId());
        }

        List<NoteBean> noteListTestBorderline = noteProxy.listNote(testBorderlineId);
        for (NoteBean noteBean2 : noteListTestBorderline) {
            noteProxy.deleteNote(noteBean2.getId());
        }

        List<NoteBean> noteListTestDanger = noteProxy.listNote(testInDangerId);
        for (NoteBean noteBean3 : noteListTestDanger) {
            noteProxy.deleteNote(noteBean3.getId());
        }

        List<NoteBean> noteListTestEarly = noteProxy.listNote(testEarlyOnsetId);
        for (NoteBean noteBean4 : noteListTestEarly) {
            noteProxy.deleteNote(noteBean4.getId());
        }


    }

    @Test
    public void testGetDiabeteRiskInDangerWithSixTriggers() throws Exception {

        int testInDangerId = 0;

        // Add Patients
        patientProxy.addPatient("TestInDanger", "Test", "2004-06-18", "F", "3 Club Road", "300-444-5555");

        // Get Patient Id
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        for (PatientBean patientBean : patientBeanList) {
            if (patientBean.getFamily().equals("TestInDanger")) {
                testInDangerId = patientBean.getId();
            }
        }
        // Add Notes
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they are short term Smoker, Weight");
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high, Weight, Height");

        // Get Rapport
        mockMvc.perform(post("/assess/familyName?familyName=TestInDanger")
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestInDanger"));

        // Delete Patient
        patientProxy.deletePatient(testInDangerId);

        // Delete Note
        List<NoteBean> noteListTestDanger = noteProxy.listNote(testInDangerId);
        for (NoteBean noteBean3 : noteListTestDanger) {
            noteProxy.deleteNote(noteBean3.getId());
        }
    }

    @Test
    public void testGetDiabeteRiskInDangerWithSevenTriggers() throws Exception {

        int testInDangerId = 0;

        // Add Patients
        patientProxy.addPatient("TestInDanger", "Test", "1984-06-18", "F", "3 Club Road", "300-444-5555");

        // Get Patient Id
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        for (PatientBean patientBean : patientBeanList) {
            if (patientBean.getFamily().equals("TestInDanger")) {
                testInDangerId = patientBean.getId();
            }
        }
        // Add Notes
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they are short term Smoker, Weight");
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high, Weight, Height");

        // Get Rapport
        mockMvc.perform(post("/assess/familyName?familyName=TestInDanger")
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestInDanger"));

        // Delete Patient
        patientProxy.deletePatient(testInDangerId);

        // Delete Note
        List<NoteBean> noteListTestDanger = noteProxy.listNote(testInDangerId);
        for (NoteBean noteBean3 : noteListTestDanger) {
            noteProxy.deleteNote(noteBean3.getId());
        }
    }

    @Test
    public void testGetDiabeteRiskEarlyOnSetWithSixTriggers() throws Exception {

        int testInDangerId = 0;

        // Add Patients
        patientProxy.addPatient("TestInDanger", "Test", "2000-06-18", "M", "3 Club Road", "300-444-5555");

        // Get Patient Id
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        for (PatientBean patientBean : patientBeanList) {
            if (patientBean.getFamily().equals("TestInDanger")) {
                testInDangerId = patientBean.getId();
            }
        }
        // Add Notes
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they are short term Smoker, Weight");
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high, Weight, Height");

        // Get Rapport
        mockMvc.perform(post("/assess/familyName?familyName=TestInDanger")
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestInDanger"));

        // Delete Patient
        patientProxy.deletePatient(testInDangerId);

        // Delete Note
        List<NoteBean> noteListTestDanger = noteProxy.listNote(testInDangerId);
        for (NoteBean noteBean3 : noteListTestDanger) {
            noteProxy.deleteNote(noteBean3.getId());
        }
    }

    @Test
    public void testGetDiabeteRiskEarlyOnSetWithEightTriggers() throws Exception {

        int testInDangerId = 0;

        // Add Patients
        patientProxy.addPatient("TestInDanger", "Test", "1971-06-18", "M", "3 Club Road", "300-444-5555");

        // Get Patient Id
        List<PatientBean> patientBeanList = patientProxy.getAllPatient();
        for (PatientBean patientBean : patientBeanList) {
            if (patientBean.getFamily().equals("TestInDanger")) {
                testInDangerId = patientBean.getId();
            }
        }
        // Add Notes
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they are short term Smoker, Weight, Height,Dizziness");
        noteProxy.addNote(testInDangerId, "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high, Weight, Height,Dizziness");

        // Get Rapport
        mockMvc.perform(post("/assess/familyName?familyName=TestInDanger")
                .contentType(MediaType.APPLICATION_JSON)
                .content("TestInDanger"));

        // Delete Patient
        patientProxy.deletePatient(testInDangerId);

        // Delete Note
        List<NoteBean> noteListTestDanger = noteProxy.listNote(testInDangerId);
        for (NoteBean noteBean3 : noteListTestDanger) {
            noteProxy.deleteNote(noteBean3.getId());
        }
    }

}

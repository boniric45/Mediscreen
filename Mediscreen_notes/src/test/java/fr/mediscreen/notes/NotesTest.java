package fr.mediscreen.notes;

import fr.mediscreen.notes.beans.PatientBean;
import fr.mediscreen.notes.model.NotesModel;
import fr.mediscreen.notes.service.NotesServices;
import fr.mediscreen.notes.service.PatientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotesServices notesServices;

    @Autowired
    private PatientService patientService;

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

    @Test // Patient, Database SQL and MongoDB must be up
    @Order(1)
    public void createNewNoteTest() throws Exception {

        // When
        patientService.addPatient("family", "given", "1952-01-01", "F", "10 Street Manhattan", "020-0523-11");

        // Given
        String urlNote = "http://localhost:8082/patHistory/add?patId=1&note=Patient:Test";

        // Then
        mockMvc.perform(post(urlNote)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Notes Inserted"))
                .andExpect(status().isCreated());
    }

    @Test
    public void createNewNoteIsInternalServerErrorTest() throws Exception {
        String urlNote = "http://localhost:8082/patHistory/add?patId=10000000&note=Patient:Test";

        mockMvc.perform(post(urlNote)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(2)
    public void getAllNotesTest() throws Exception {

        // Given
        List<NotesModel> notesModelList = notesServices.findAll();

        // When
        mockMvc.perform(get("/patHistory/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test")));

        // Then
        assertTrue(notesModelList.size() > 0);
    }

    @Test
    @Order(3)
    public void getNotesByIdPatientTest() throws Exception {

        // Given
        List<NotesModel> notesModelList = notesServices.findNotesByIdPatient(1);

        // When
        mockMvc.perform(get("/patHistory/patient/" + 1))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test")));

        // Then
        assertTrue(notesModelList.size() > 0);
    }

    @Test
    public void getNotesByIdPatientIsNoContentTest() throws Exception {
        mockMvc.perform(get("/patHistory/patient/" + 100000))
                .andExpect(status().isNoContent());
    }

    @Test  // Demographique Patient must be up
    @Order(4)
    public void getNotesByIdNoteTest() throws Exception {

        // Given
        LocalDate date = LocalDate.now();
        NotesModel notesModel = new NotesModel(1, date, "Test note patient 1");

        // When
        NotesModel _notesModel = notesServices.save(notesModel);
        String idNote = _notesModel.getId();

        // Then
        mockMvc.perform(get("/patHistory/note/" + idNote))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test note patient 1")));

        notesServices.deleteById(idNote);
    }

    @Test  // Demographique Patient must be up
    public void getNotesByIdNoteIsNoContentTest() throws Exception {

        mockMvc.perform(get("/patHistory/note/" + 16354564))
                .andExpect(status().isNoContent());
    }

    @Test  // Demographique Patient must be up
    @Order(5)
    public void getUpdateNoteByIdTest() throws Exception {

        // Given
        LocalDate date = LocalDate.now();
        NotesModel notesModel = new NotesModel(1, date, "Test note patient 1");

        // When
        NotesModel noteSaved = notesServices.save(notesModel);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/patHistory/update?id=" + noteSaved.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity("note", "Test note 2 patient 1")))
                .andExpect(status().isOk());

        notesServices.deleteById(noteSaved.getId());
    }

    @Test  // Demographique Patient must be up
    public void getUpdateNoteByIdNoContentTest() throws Exception {

        // Given
        LocalDate date = LocalDate.now();
        NotesModel notesModel = new NotesModel(1, date, "Test note patient 1");

        // When
        NotesModel noteSaved = notesServices.save(notesModel);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/patHistory/update?id=" + 100000)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildUrlEncodedFormEntity("note", "Test note 2 patient 1")))
                .andExpect(status().isNoContent());

        notesServices.deleteById(noteSaved.getId());
    }

    @Test// Demographique Patient must be up
    @Order(6)
    public void deleteNoteByIdTest() throws Exception {

        // Given
        LocalDate date = LocalDate.now();
        NotesModel notesModel = new NotesModel(1, date, "Test delete note patient 1");

        // When
        NotesModel _notesModel = notesServices.save(notesModel);
        String idNote = _notesModel.getId();

        // Then
        mockMvc.perform(delete("/patHistory/delete/" + idNote)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test// Demographique Patient must be up
    public void deleteNoteByIdIsNotFoundTest() throws Exception {

        // Given
        LocalDate date = LocalDate.now();
        NotesModel notesModel = new NotesModel(1, date, "Test delete note patient 1");

        // When
        NotesModel _notesModel = notesServices.save(notesModel);

        // Then
        mockMvc.perform(delete("/patHistory/delete/" + 1000000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test // Microservice Patient and PostGres must be up
    public void getNoteModelTest() {

        // Given
        NotesModel expected = new NotesModel();

        // When
        expected.setId("Id");
        expected.setPatId(1);
        expected.setDateNote(LocalDate.now());
        expected.setNote("Test Note");

        // Then
        Assertions.assertEquals(expected.getId(), "Id");
        Assertions.assertEquals(expected.getPatId(), 1);
        Assertions.assertEquals(expected.getDateNote(), LocalDate.now());
        Assertions.assertEquals(expected.getNote(), "Test Note");

    }

    @Test
    public void testPatientToString() {

        PatientBean patient = patientService.findPatientById(1);
        String expected = patient.toString();
        Assertions.assertEquals(expected, patient.toString());
    }


}

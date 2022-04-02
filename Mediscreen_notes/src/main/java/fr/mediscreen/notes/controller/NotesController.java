package fr.mediscreen.notes.controller;

import fr.mediscreen.notes.beans.PatientBean;
import fr.mediscreen.notes.model.NotesModel;
import fr.mediscreen.notes.service.NotesServices;
import fr.mediscreen.notes.service.PatientService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class NotesController {
    Logger logger = LoggerFactory.getLogger(NotesController.class); // Logger

    @Autowired
    NotesServices notesServices;

    @Autowired
    PatientService patientService;

    /**
     * Create note
     *
     * @return String
     */
    @ApiOperation(value = "Add a new note")
    @PostMapping(value = "/patHistory/add")
    public @ResponseBody
    @Valid ResponseEntity<String> addNewNote(@RequestParam(defaultValue = "Id patient", required = true) @NotEmpty int patId, @RequestParam(defaultValue = "Note", required = true) @NotEmpty String note) {
        try {
            PatientBean patientBean = patientService.findPatientById(patId);
            if (patientBean != null) {
                LocalDate date = LocalDate.now();
                NotesModel notesModel = new NotesModel(patId, date, note);
                NotesModel _notesModel = notesServices.save(notesModel);
                logger.info("Notes Inserted : " + _notesModel);
                return new ResponseEntity<>("Notes Inserted ", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Patient Not Found ", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error Inserted Notes: " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read All Notes
     *
     * @return List<Notes>
     */
    @ApiOperation(value = "Get all notes")
    @GetMapping("/patHistory/all")
    public ResponseEntity<List<NotesModel>> getAllNotes() {
        try {
            List<NotesModel> notesModelList = new ArrayList<>(notesServices.findAll());
            if (notesModelList.isEmpty()) {
                logger.info("/note/all : " + HttpStatus.NO_CONTENT);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                logger.info("/note/all : " + notesModelList);
                return new ResponseEntity<>(notesModelList, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("/note/all : Error " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read notes by id patient
     *
     * @param idPatient
     * @return
     */
    @ApiOperation(value = "Read note by id patient")
    @GetMapping("/patHistory/patient/{id}")
    public ResponseEntity<List<NotesModel>> getNotesByPatientId(@PathVariable("id") int idPatient) {
        try {
            List<NotesModel> notesModelList = notesServices.findNotesByIdPatient(idPatient);
            if (!notesModelList.isEmpty()) {
                logger.info("/patHistory : Patient " + idPatient + "  > " + notesModelList);
                return new ResponseEntity<>(notesModelList, HttpStatus.OK);
            } else {
                logger.info("/note/all : " + HttpStatus.NO_CONTENT);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            logger.error("/note/all : Error " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Read note with id note
     *
     * @return String
     */
    @ApiOperation(value = "Read note by id note")
    @GetMapping("/patHistory/note/{id}")
    public ResponseEntity<NotesModel> getNoteById(@PathVariable("id") String noteId) {
        try {
            Optional<NotesModel> notes = notesServices.findByIdNote(noteId);
            if (notes.isPresent()) {
                logger.info("Notes : " + notes.get());
                return new ResponseEntity<>(notes.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update note with id note
     *
     * @return String
     */
    @ApiOperation(value = "Update note by id")
    @PutMapping("/patHistory/update")
    public @ResponseBody
    @Valid ResponseEntity<NotesModel> updateNote(@RequestParam(defaultValue = "Id Note", required = true) @NotEmpty String id, @RequestParam(defaultValue = "Note", required = true) @NotEmpty String note) {

        Optional<NotesModel> optionalNotes = notesServices.findByIdNote(id);
        if (optionalNotes.isPresent()) {
            LocalDate date = LocalDate.now();
            NotesModel updateNote = optionalNotes.get();
            updateNote.setDateNote(date);
            updateNote.setNote(note);
            notesServices.save(updateNote);
            logger.info("Notes Updated : " + updateNote);
            return new ResponseEntity<>(updateNote, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Delete Note by Id
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Delete note by id note")
    @RequestMapping(value = "/patHistory/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deletePatientById(@PathVariable("id") String id) {

        try {
            Optional<NotesModel> optionalNotes = notesServices.findByIdNote(id);
            if (optionalNotes.isPresent()) {
                notesServices.deleteById(id);
                logger.info("/patHistory/delete ID: " + id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                logger.info("/patHistory/delete ID: " + HttpStatus.NOT_FOUND);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("/patHistroy/delete Error : " + HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

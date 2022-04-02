package fr.mediscreen.front.controller;

import fr.mediscreen.front.beans.Notes;
import fr.mediscreen.front.interfaces.NotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class NotesController {

    Logger logger = LoggerFactory.getLogger(PatientController.class); // Logger

    @Autowired
    NotesService notesService;

    /**
     * Read All
     * Get Notes
     *
     * @return Note Page
     */
    @GetMapping(value = "/notes")
    public String getAllNotes(Model model) {
        model.addAttribute("list", notesService.getAllNotes());
        return "notes";
    }

    /**
     * Add Notes
     *
     * @return Page add note
     */
    @GetMapping(value = "/insertNewNote")
    public String showFormNote(Notes notes) {
        return "insertNewNote";
    }

    /**
     * Validate Add Note
     */
    @PostMapping(value = "/insertNewNote",
            produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addNote(Notes notes, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            notesService.addNewNote(notes.getPatId(), notes.getNote());
            logger.info(" SUCCESS POST /InsertNewNote");
            model.addAttribute("noteList", notesService.getAllNotes());
            return "redirect:/notes";
        } else {
            logger.error(" ERROR POST /InsertNewNote");
            return "insertNewNote";
        }

    }

    /**
     * Show UpdateNote.html
     */
    @GetMapping("/updateNote/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Notes note, Model model) {
        model.addAttribute("note", notesService.getNoteById(id));
        return "updateNote";
    }

    /**
     * Update validate Note
     */
    @RequestMapping(value = "/updateNote/validate/{id}", method = RequestMethod.POST)
    public String updateNote(@PathVariable("id") String id, @RequestParam("note") String note) {
        if (note != null) {
            logger.info(" SUCCESS POST / Update Note Validate");
            notesService.updateNote(id, note);
        } else {
            logger.error(" ERROR POST / Update Note Validate");
        }
        return "redirect:/notes";
    }

    /**
     * Delete Note By Id Note
     *
     * @param id
     * @return redirect page Notes
     */
    @RequestMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable("id") String id) {
        if (id != null) {
            logger.info(" SUCCESS DELETE/ Delete Note ");
            notesService.deleteNoteById(id);
        } else {
            logger.error(" ERROR DELETE/ Delete Note ");
        }
        return "redirect:/notes";
    }

}

package fr.mediscreen.front.proxy;

import fr.mediscreen.front.beans.Notes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "notes", url = "host.docker.internal:8082")
public interface NotesProxy {

    @GetMapping("/patHistory/all")
    List<Notes> getAllNotes();

    @PostMapping("/patHistory/add")
    void addNewNote(
            @RequestParam("patId") int patId,
            @RequestParam("note") String notes);

    @GetMapping("/patHistory/note/{id}")
    Notes getNoteById(
            @PathVariable("id") String id);

    @PutMapping("/patHistory/update")
    void updateNote(
            @RequestParam("id") String id,
            @RequestParam("note") String note);

    @DeleteMapping(value = "/patHistory/delete/{id}")
    void deleteNoteById(
            @PathVariable("id") String id);
}

package fr.mediscreen.front.interfaces;

import fr.mediscreen.front.beans.Notes;

import java.util.List;

public interface NotesService {

    void addNewNote(int patId, String note);

    List<Notes> getAllNotes();

    Notes getNoteById(String id);

    void updateNote(String id, String notes);

    void deleteNoteById(String id);
}

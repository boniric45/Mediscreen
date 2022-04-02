package fr.mediscreen.front.services;

import fr.mediscreen.front.beans.Notes;
import fr.mediscreen.front.interfaces.NotesService;
import fr.mediscreen.front.proxy.NotesProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesProxy notesProxy;

    @Override
    public void addNewNote(int patId, String note) {
        notesProxy.addNewNote(patId, note);
    }

    @Override
    public List<Notes> getAllNotes() {
        return notesProxy.getAllNotes();
    }

    @Override
    public Notes getNoteById(String id) {
        return notesProxy.getNoteById(id);
    }

    @Override
    public void updateNote(String id, String notes) {
        notesProxy.updateNote(id, notes);
    }

    @Override
    public void deleteNoteById(String id) {
        notesProxy.deleteNoteById(id);
    }


}

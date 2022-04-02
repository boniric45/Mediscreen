package fr.mediscreen.notes.service;

import fr.mediscreen.notes.model.NotesModel;
import fr.mediscreen.notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesServices {

    @Autowired
    NotesRepository notesRepository;

    public NotesModel save(NotesModel notesModel) {
        return notesRepository.save(notesModel);
    }

    public List<NotesModel> findAll() {
        return notesRepository.findAll();
    }

    public List<NotesModel> findNotesByIdPatient(int patId) {
        return notesRepository.findNotesByIdPatient(patId);
    }

    public Optional<NotesModel> findByIdNote(String noteId) {
        return notesRepository.findById(noteId);
    }

    public void deleteById(String id) {
        notesRepository.deleteById(id);
    }

}

package fr.mediscreen.notes.repository;

import fr.mediscreen.notes.model.NotesModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends MongoRepository<NotesModel, String> {

    @Query("{ 'patientid' : ?0 }")
    List<NotesModel> findNotesByIdPatient(int patId);

}

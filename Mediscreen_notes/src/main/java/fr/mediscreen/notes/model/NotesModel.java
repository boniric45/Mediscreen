package fr.mediscreen.notes.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "notes")
public class NotesModel {


    @Id
    private String id;

    @Field(value = "patientid")
    private int patId;

    @Field(value = "datenote")
    @DateTimeFormat(pattern = "YYYY-mm-dd")
    private LocalDate dateNote;

    @Field(value = "notes")
    private String note;

    public NotesModel(int patId, LocalDate dateNote, String note) {
        this.patId = patId;
        this.dateNote = dateNote;
        this.note = note;
    }

    public NotesModel() {
    }
}

package fr.mediscreen.rapport.beans;

import lombok.*;

import java.time.LocalDate;

//@Data

@Getter
@Setter
public class NoteBean {

    private String id;
    private int patId;
    private LocalDate dateNote;
    private String note;
}

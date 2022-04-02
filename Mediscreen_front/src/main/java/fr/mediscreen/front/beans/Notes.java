package fr.mediscreen.front.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class Notes {

    private String id;
    private int patId;
    @DateTimeFormat(pattern = "YYYY-mm-dd")
    private LocalDate dateNote;
    private String note;

}

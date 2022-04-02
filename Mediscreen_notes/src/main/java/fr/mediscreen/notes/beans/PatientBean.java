package fr.mediscreen.notes.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString
public class PatientBean {

    private int id;
    private String prenom;
    private String nom;
    private LocalDate naissance;
    private String genre;
    private String adresse;
    private String telephone;


}

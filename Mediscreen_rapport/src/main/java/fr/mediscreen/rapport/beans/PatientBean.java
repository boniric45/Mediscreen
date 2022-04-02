package fr.mediscreen.rapport.beans;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

//@Data
@Getter
@Setter
public class PatientBean {

    private int id;
    private String given;
    private String family;
    private LocalDate dob;
    private String sex;
    private String address;
    private String phone;

}

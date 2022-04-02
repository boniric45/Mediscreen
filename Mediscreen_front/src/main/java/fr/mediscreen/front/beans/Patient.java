package fr.mediscreen.front.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class Patient {

    private int id;
    private String family;
    private String given;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String sex;
    private String address;
    private String phone;

    public Patient(String family, String given, LocalDate dob, String sex, String address, String phone) {
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }
}
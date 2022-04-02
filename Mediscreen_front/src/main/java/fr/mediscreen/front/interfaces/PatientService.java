package fr.mediscreen.front.interfaces;

import fr.mediscreen.front.beans.Patient;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {

    void save(Patient patient);

    List<Patient> getPatientAll();

    Patient getPatientById(int id);

    void updatePatient(int id, String family, String given, LocalDate dob, String sex, String address, String phone);

    void deletePatient(int id);

}

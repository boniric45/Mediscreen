package fr.mediscreen.demographique_patient.service;

import fr.mediscreen.demographique_patient.model.Patient;
import fr.mediscreen.demographique_patient.repository.PatientRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//@Data
@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public List<Patient> findAll() {
        return (List<Patient>) patientRepository.findAll();
    }

    public Patient save(Patient patient) {
        patientRepository.save(patient);
        return patient;
    }

    public Optional<Patient> findById(int id) {
        return patientRepository.findById(id);
    }

    public void deleteById(int id) {
        patientRepository.deleteById(id);
    }

    public void updatePatient(Patient patient) {
        patientRepository.save(patient);
    }
}

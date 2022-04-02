package fr.mediscreen.front.services;

import fr.mediscreen.front.beans.Patient;
import fr.mediscreen.front.interfaces.PatientService;
import fr.mediscreen.front.proxy.PatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientProxy patientProxy;

    @Override
    public void save(Patient patient) {
        patientProxy.addNewPatient(patient.getFamily(), patient.getGiven(), patient.getDob(), patient.getSex(), patient.getAddress(), patient.getPhone());
    }

    @Override
    public List<Patient> getPatientAll() {
        return patientProxy.getAllPatient();
    }

    @Override
    public Patient getPatientById(int id) {
        return patientProxy.getPatientById(id);
    }

    @Override
    public void updatePatient(int id, String family, String given, LocalDate dob, String sex, String address, String phone) {
        patientProxy.updatePatient(id, family, given, dob, sex, address, phone);
    }

    @Override
    public void deletePatient(int id) {
        patientProxy.deletePatient(id);
    }

}

package fr.mediscreen.notes.service;

import fr.mediscreen.notes.beans.PatientBean;
import fr.mediscreen.notes.proxies.PatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    PatientProxy patientProxy;

    public PatientBean findPatientById(int id) {
        return patientProxy.getPatientById(id);
    }

    public void addPatient(String family, String given, String dob, String sex, String address, String phone) {
          patientProxy.addPatient(family,given,dob,sex,address,phone);

    }
}

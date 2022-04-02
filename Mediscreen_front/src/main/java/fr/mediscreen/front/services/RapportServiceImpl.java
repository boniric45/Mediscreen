package fr.mediscreen.front.services;

import fr.mediscreen.front.beans.Patient;
import fr.mediscreen.front.interfaces.RapportService;
import fr.mediscreen.front.proxy.PatientProxy;
import fr.mediscreen.front.proxy.RapportProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RapportServiceImpl implements RapportService {

    @Autowired
    private PatientProxy patientProxy;

    @Autowired
    private RapportProxy rapportProxy;

    @Override
    public String getRapport(Integer idPatient) {

        // Get Patient with id Patient
        Patient patient = patientProxy.getPatientById(idPatient);

        // Get Age of Patient
        int age = rapportProxy.getAge(idPatient);
        String risque = rapportProxy.getRisk(idPatient);

        return "Patient: " + patient.getFamily() + " " + patient.getGiven() + "(age " + age + ") diabetes assessment is: " + risque;
    }

    @Override
    public String findRapportById(int patId) {
        return rapportProxy.getRapportById(patId);
    }

    @Override
    public String findRapportByFamilyName(String familyName) {
        return rapportProxy.getRapportByFamilyName(familyName);
    }
}

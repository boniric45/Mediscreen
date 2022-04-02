package fr.mediscreen.rapport.interfaces;

import fr.mediscreen.rapport.beans.PatientBean;
import fr.mediscreen.rapport.model.Rapport;
import fr.mediscreen.rapport.model.Risk;

public interface RapportService {

    Risk getDiabeteLevelRisk(int id);

    PatientBean findPatientById(int id);

    String findRapportById(Integer id);

    String findRapportByFamilyName(String familyName);
}

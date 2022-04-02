package fr.mediscreen.front.interfaces;

public interface RapportService {

    String getRapport(Integer idPatient);

    String findRapportById(int patId);

    String findRapportByFamilyName(String familyName);


}

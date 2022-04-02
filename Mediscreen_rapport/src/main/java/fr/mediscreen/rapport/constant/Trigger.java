package fr.mediscreen.rapport.constant;

import java.util.List;

public class Trigger {

    /**
     * List of Trigger that can be used to calculate the risk of diabete
     */
    protected static final List<String> triggerList = List.of("Hemoglobin A1C", "Hémoglobine A1C", "MicroAlbumin", "Microalbumine", "Height", "Poids", "Taille", "Weight", "Smoker", "Fumeur", "Abnormal", "Anormal", "Cholesterol", "Cholestérol", "Dizziness", "Vertige", "Relapse", "Rechute", "Reaction", "Réaction", "Antibodies", "Anticorps");

    public static List<String> getTriggerList() {
        return triggerList;
    }

}

package fr.mediscreen.rapport.model;

public enum Risk {

    NONE ("None"),
    BORDERLINE ("Borderline"),
    IN_DANGER ("In-Danger"),
    EARLY_ONSET ("Early-onset");

    private final String rapport;

    Risk(String rapport) {
        this.rapport = rapport;
    }

    public String getRapport() {
        return rapport;
    }
}

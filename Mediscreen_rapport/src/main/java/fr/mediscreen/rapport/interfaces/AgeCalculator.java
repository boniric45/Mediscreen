package fr.mediscreen.rapport.interfaces;

import java.time.LocalDate;

public interface AgeCalculator {

    int ageCalculate(LocalDate birthDate);

    int getAge(Integer id);

}

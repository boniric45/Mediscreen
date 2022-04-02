package fr.mediscreen.front.controller;

import fr.mediscreen.front.beans.Patient;
import fr.mediscreen.front.interfaces.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Controller
public class PatientController {
    Logger logger = LoggerFactory.getLogger(PatientController.class); // Logger

    @Autowired
    PatientService patientService;

    /**
     * Create a new patient
     *
     * @param patient
     * @return patient inserted
     */
    @GetMapping(value = "/insertNewPatient")
    public String showFormAddPatient(Patient patient) {
        return "insertNewPatient";
    }

    /**
     * Add Patient Validate
     *
     * @return list of patient
     */
    @RequestMapping(value = "/insertNewPatient", method = RequestMethod.POST)
    public String addPatient(@RequestParam(defaultValue = "family must be between min 2 and max 60 characters long", required = true)
                             @Size(min = 2, max = 60, message = "family must be between {min} and {max} characters long") @NotEmpty String family,
                             @RequestParam(defaultValue = "given must be between min 2 and max 60 characters long", required = true)
                             @Size(min = 2, max = 60, message = "given must be between {min} and {max} characters long") @NotEmpty String given, @RequestParam(defaultValue = "Pattern dob : yyyy-MM-dd", required = true) @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob, @RequestParam(defaultValue = " sex must be 1 character long") @Size(min = 1, max = 1, message = "gender must be " +
                              "between {min} and {max} characters long") @NotEmpty String sex, @RequestParam(defaultValue = " address must be between min 2 and max 100 characters long", required = false)
                             @Size(min = 2, max = 100, message = "address must be between {min} and {max} characters long") String address, @RequestParam(defaultValue = " phone must be between min 2 and max 20 characters long", required = false)
                             @Size(min = 2, max = 20, message = "phone must be between {min} and {max} characters long") String phone) {
        if (family != null) {
            Patient patient = new Patient(family, given, dob, sex, address, phone);
            patientService.save(patient);
            logger.info(" SUCCESS POST /InsertNewPatient");
        } else {
            logger.error(" ERROR POST /InsertNewPatient");
        }
        return "redirect:/patients";
    }

    /**
     * Read All
     * Get Home
     *
     * @return Home Page
     */
    @GetMapping(value = "/patients")
    public String home(Model model) {
        if (patientService.getPatientAll() != null) {
            logger.info(" SUCCESS GET /patients");
            model.addAttribute("patientList", patientService.getPatientAll());
        } else {
            logger.error(" ERROR GET /patients");
        }
        return "patients";
    }

    /**
     * Update Patient
     */
    @GetMapping(value = "/updatePatient/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "updatePatient";
    }

    /**
     * Update Patient
     *
     * @return Patient updated
     */
    @RequestMapping(value = "/updatePatient/validate/{id}", method = RequestMethod.POST)
    public String updatePatient(@PathVariable("id") int id,
                                @RequestParam(defaultValue = "family must be between min 2 and max 60 characters long", required = true)
                                @Size(min = 2, max = 60, message = "family must be between {min} and {max} characters long") @NotEmpty String family,
                                @RequestParam(defaultValue = "given must be between min 2 and max 60 characters long", required = true)
                                @Size(min = 2, max = 60, message = "given must be between {min} and {max} characters long") @NotEmpty String given,
                                @RequestParam(defaultValue = "Pattern dob : yyyy-MM-dd", required = true) @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
                                @RequestParam(defaultValue = " sex must be 1 character long") @Size(min = 1, max = 1, message = "gender must be " +
                                        "between {min} and {max} characters long") @NotEmpty String sex,
                                @RequestParam(defaultValue = " address must be between min 2 and max 100 characters long", required = false)
                                @Size(min = 2, max = 100, message = "address must be between {min} and {max} characters long") String address,
                                @RequestParam(defaultValue = " phone must be between min 2 and max 20 characters long", required = false)
                                @Size(min = 2, max = 20, message = "phone must be between {min} and {max} characters long") String phone, Model model) {
        if (family != null) {
            patientService.updatePatient(id, family, given, dob, sex, address, phone);
            model.addAttribute("patientList", patientService.getPatientAll());
            logger.info(" SUCCESS POST /updatePatient/validate/" + id);
        } else {
            logger.error(" ERROR POST /updatePatient/validate/" + id);
        }
        return "redirect:/patients";
    }


    /**
     * Delete Patient By Id
     *
     * @param id
     * @return Response
     */
    @RequestMapping("/deletePatient/{id}")
    public String getTutorialById(@PathVariable("id") int id) {
        if (id > 0) {
            patientService.deletePatient(id);
            logger.info(" SUCCESS DELETE /deletePatient/" + id);
        } else {
            logger.error(" ERROR DELETE /deletePatient/" + id);
        }

        return "redirect:/patients";
    }

}

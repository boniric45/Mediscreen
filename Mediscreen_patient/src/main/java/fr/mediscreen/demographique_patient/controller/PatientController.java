package fr.mediscreen.demographique_patient.controller;

import fr.mediscreen.demographique_patient.model.Patient;
import fr.mediscreen.demographique_patient.service.PatientService;
import io.swagger.annotations.ApiOperation;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class PatientController {

    Logger logger = LoggerFactory.getLogger(PatientController.class); // Logger

    @Autowired
    PatientService patientService;

    /**
     * Add Patient
     *
     * @param family
     * @param given
     * @param dob
     * @param sex
     * @param address
     * @param phone
     * @param response
     */
    @ApiOperation(value = "Add Patient")
    @PostMapping("patient/add")
    public void addPatient(@RequestParam @Size(min = 2, max = 60, message = "family must be " +
            "between {min} and {max} characters long") String family,
                           @RequestParam @Valid @Size(min = 2, max = 60,
                                   message = "given must be between {min} and {max} characters " +
                                           "long") String given,
                           @RequestParam @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
                           @RequestParam String sex,
                           @RequestParam(defaultValue = "not specified", required = false) @Size(min = 2, max =
                                   100,
                                   message = "address must be " +
                                           "between" +
                                           " {min} and {max} characters long") String address,
                           @RequestParam(defaultValue = "not specified", required = false) @Size(min = 2, max =
                                   20,
                                   message = "phone must be between " +
                                           "{min} and {max} characters long") String phone,
                           HttpServletResponse response) {
        logger.info("Enter addPatient in patient microservice");
        Patient patient = new Patient(family, given, dob, sex, address, phone);
        patientService.save(patient);
    }

    /**
     * Read Patient by id
     *
     * @return
     */
    @ApiOperation(value = "Get Patient By Id")
    @GetMapping("/patient/{id}")
    public @ResponseBody
    ResponseEntity<Patient> getPatientById(@PathVariable("id") int id) {

        Optional<Patient> patientOptional = patientService.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            logger.info("/patient/" + id);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } else {
            logger.info("/patient/" + id + " " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Read All Patient
     *
     * @return List of Patient
     */
    @ApiOperation(value = "Get All Patients")
    @GetMapping("/patient/all")
    public ResponseEntity<List<Patient>> getAllPatient() {
        try {
            List<Patient> patientList = new ArrayList<>(patientService.findAll());
            if (patientList.isEmpty()) {
                logger.info("/patient/all : " + HttpStatus.NO_CONTENT);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                logger.info("/patient/all : " + patientList);
                return new ResponseEntity<>(patientList, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("/patient/all : Error " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update Patient
     *
     * @param family
     * @param given
     * @param dob
     * @param sex
     * @param address
     * @param phone
     * @return Patient
     */
    @ApiOperation(value = "Update Patient by Id")
    @PutMapping("/patient/update/{id}")
    public @ResponseBody
    @Valid ResponseEntity<Patient> updatePatient(@PathVariable("id") int id, @RequestParam(defaultValue = "family must be between min 2 and max 60 characters long", required = true)
    @Size(min = 2, max = 60, message = "family must be between {min} and {max} characters long") @NotEmpty String family, @RequestParam(defaultValue = "given must be between min 2 and max 60 characters long", required = true)
                                                 @Size(min = 2, max = 60, message = "given must be between {min} and {max} characters long") @NotEmpty String given, @RequestParam(defaultValue = "Pattern dob : yyyy-MM-dd", required = true) @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob, @RequestParam(defaultValue = " sex must be 1 character long") @Size(min = 1, max = 1, message = "gender must be " +
            "between {min} and {max} characters long") @NotEmpty String sex, @RequestParam(defaultValue = " address must be between min 2 and max 100 characters long", required = false)
                                                 @Size(min = 2, max = 100, message = "address must be between {min} and {max} characters long") String address, @RequestParam(defaultValue = " phone must be between min 2 and max 20 characters long", required = false)
                                                 @Size(min = 2, max = 20, message = "phone must be between {min} and {max} characters long") String phone) {
        Patient patient = new Patient(family, given, dob, sex, address, phone);
        Patient patientSearch = patientService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient is not found :: " + id));
        patientSearch.setFamily(patient.getFamily());
        patientSearch.setGiven(patient.getGiven());
        patientSearch.setDob(patient.getDob());
        patientSearch.setSex(patient.getSex());
        patientSearch.setAddress(patient.getAddress());
        patientSearch.setPhone(patient.getPhone());
        patientService.updatePatient(patientSearch);
        logger.info("/patient/update : " + patient.getId() + " is updated");
        return new ResponseEntity<>(patientSearch, HttpStatus.OK);
    }


    /**
     * Delete Patient by Id
     *
     * @param id
     * @return Response HttpStatus
     */
    @ApiOperation(value = "Delete Patient by Id")
    @RequestMapping(value = "/patient/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deletePatientById(@PathVariable("id") int id) {
        try {
            patientService.deleteById(id);
            logger.info("/patient/delete ID: " + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("/patient/delete Error : " + HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

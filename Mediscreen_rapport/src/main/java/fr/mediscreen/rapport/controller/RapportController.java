package fr.mediscreen.rapport.controller;

import fr.mediscreen.rapport.beans.PatientBean;
import fr.mediscreen.rapport.interfaces.AgeCalculator;
import fr.mediscreen.rapport.interfaces.RapportService;
import fr.mediscreen.rapport.interfaces.TriggerCalculator;
import fr.mediscreen.rapport.model.Risk;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RapportController {

    private static final Logger logger = LoggerFactory.getLogger(RapportController.class);

    @Autowired
    RapportService rapportService;

    @Autowired
    AgeCalculator ageCalculator;

    @Autowired
    TriggerCalculator triggerCalculator;

    @ApiOperation(value = "Get diabetes level risk")
    @GetMapping("/risk/{id}")
    public @ResponseBody
    ResponseEntity<Risk> getDiabetesLevelRisk(@PathVariable("id") Integer id) {
        Risk risk = rapportService.getDiabeteLevelRisk(id);
        if (risk != null) {
            logger.info("/risk/" + id);
            return new ResponseEntity<>(risk, HttpStatus.OK);
        } else {
            logger.info("/risk/" + id + " " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Get rapport by patient id")
    @RequestMapping(value = "/assess/id", method = RequestMethod.POST)
    public String getAssessById(@RequestParam int patId) {
        String result = "";
        if (patId != 0) {
            result = rapportService.findRapportById(patId);
        } else {
            result = "The id is not found ";
        }
        return result;
    }

    @ApiOperation(value = "Get rapport by family name")
    @RequestMapping(value = "/assess/familyName", method = RequestMethod.POST)
    public String getAssessByFamilyName(@RequestParam String familyName) {
        String result = "";
        if (familyName != null) {
            result = rapportService.findRapportByFamilyName(familyName);
        } else {
            result = "Family name is not found ";
        }
        return result;
    }

    @GetMapping("/patient/{id}")
    public @ResponseBody
    ResponseEntity<PatientBean> getPatient(@PathVariable("id") int id) {
        PatientBean patientBean = rapportService.findPatientById(id);
        return new ResponseEntity<>(patientBean, HttpStatus.OK);
    }

    @ApiOperation(value = "Get age of patient")
    @GetMapping("/rapport/age/{id}")
    public @ResponseBody
    ResponseEntity<String> getAge(@PathVariable("id") int id) {
        PatientBean patientBean = rapportService.findPatientById(id);
        int age = ageCalculator.getAge(patientBean.getId());
        if (age > 0) {
            logger.info("/age/" + patientBean.getId());
            return new ResponseEntity<String>(String.valueOf(age), HttpStatus.OK);
        } else {
            logger.info("/age/" + patientBean.getId() + " " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Get trigger of note to patient")
    @GetMapping("/rapport/trigger/{id}")
    public @ResponseBody
    ResponseEntity<Integer> getTrigger(@PathVariable("id") Integer id) {
        int trigger = triggerCalculator.calculateTriggerInNotes(id);
        if (trigger > 0) {
            logger.info("/trigger/" + id);
            return new ResponseEntity<>(trigger, HttpStatus.OK);
        } else {
            logger.info("/trigger/" + id + " " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}



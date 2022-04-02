package fr.mediscreen.front.proxy;

import fr.mediscreen.front.beans.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "patient", url = "host.docker.internal:8081")
public interface PatientProxy {

    @PostMapping(value = "/patient/add")
    void addNewPatient(
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone);

    @GetMapping(value = "/patient/{id}")
    Patient getPatientById(
            @PathVariable("id") int id);

    @GetMapping(value = "/patient/all")
    List<Patient> getAllPatient();

    @PutMapping(value = "/patient/update/{id}")
    void updatePatient(
            @PathVariable("id") int id,
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone);


    @DeleteMapping(value = "/patient/{id}")
    void deletePatient(
            @PathVariable("id") int patId);

}
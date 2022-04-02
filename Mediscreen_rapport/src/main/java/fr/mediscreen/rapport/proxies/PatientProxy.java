package fr.mediscreen.rapport.proxies;

import fr.mediscreen.rapport.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "patient", url = "host.docker.internal:8081")
public interface PatientProxy {

    @PostMapping(value = "/patient/add")
    void addPatient(
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone);

    @GetMapping(value = "/patient/{id}")
    PatientBean getPatientById(@PathVariable("id") int id);

    @PostMapping("/rapport/age/{id}")
    int getAge(@PathVariable("id") Integer id);

    @GetMapping(value = "/patient/all")
    List<PatientBean> getAllPatient();

    @DeleteMapping(value = "/patient/{id}")
    void deletePatient(@PathVariable("id") int patId);

}

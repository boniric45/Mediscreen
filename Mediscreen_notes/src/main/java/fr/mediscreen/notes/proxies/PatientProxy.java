package fr.mediscreen.notes.proxies;

import fr.mediscreen.notes.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "patient", url = "host.docker.internal:8081")
public interface PatientProxy {

    @GetMapping(value = "patient/{id}")
    PatientBean getPatientById(@PathVariable("id") int id);

    @PostMapping(value = "/patient/add")
     void addPatient(
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone);
}

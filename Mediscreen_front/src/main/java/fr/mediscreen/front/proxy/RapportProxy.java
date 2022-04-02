package fr.mediscreen.front.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "rapport", url = "host.docker.internal:8080")
public interface RapportProxy {

    @GetMapping(value = "/rapport/age/{id}")
    int getAge(@PathVariable("id") Integer id);

    @GetMapping(value = "/rapport/{id}")
    String getRisk(@PathVariable("id") Integer idPatient);

    @PostMapping(value = "/assess/{family}")
    String getRapportByFamilyName(@PathVariable("family") String family);

    @PostMapping(value = "/assess/id?patId={patId}")
    String getRapportById(@PathVariable("patId") int id);

}

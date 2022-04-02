package fr.mediscreen.rapport.proxies;

import fr.mediscreen.rapport.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "notes", url = "host.docker.internal:8082")
public interface NoteProxy {

    @PostMapping("/patHistory/add")
    void addNote(@RequestParam("patId") int patId, @RequestParam("note") String note);

    @GetMapping("/patHistory/patient/{patientId}")
    List<NoteBean> listNote(@PathVariable("patientId") Integer patientId);

    @DeleteMapping("/patHistory/delete/{id}")
    void deleteNote(@PathVariable("id") String id);


}

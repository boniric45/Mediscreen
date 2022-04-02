package fr.mediscreen.front.controller;

import fr.mediscreen.front.beans.Rapport;
import fr.mediscreen.front.interfaces.RapportService;
import fr.mediscreen.front.proxy.RapportProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RapportController {
    Logger logger = LoggerFactory.getLogger(PatientController.class); // Logger

    @Autowired
    RapportService rapportService;

    @Autowired
    RapportProxy rapportProxy;

    @GetMapping("/assess")
    public String showFormRapport(Rapport rapport) {
        return "assess";
    }

    @PostMapping(value = "/assess")
    public void getAssessById(@RequestParam int patId, Model model) {
        String result = rapportProxy.getRapportById(patId);
        if (patId > 0) {
            Rapport rapport = new Rapport();
            rapport.setPatId(patId);
            rapport.setAssess(result);
            model.addAttribute("rapport", rapport);
            logger.info(" SUCCESS POST /assess/" + result);
        } else {
            model.addAttribute("rapport", " Aucun patient trouvé");
            logger.error(" ERROR POST /assess/" + result);
        }
    }
}


package fr.mediscreen.rapport.service;

import fr.mediscreen.rapport.beans.NoteBean;
import fr.mediscreen.rapport.constant.Trigger;
import fr.mediscreen.rapport.interfaces.TriggerCalculator;
import fr.mediscreen.rapport.proxies.NoteProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class TriggerCalculatorImpl implements TriggerCalculator {

    private static final Logger logger = LoggerFactory.getLogger(TriggerCalculatorImpl.class);

    @Autowired
    NoteProxy noteProxy;

    @Override
    public int calculateTriggerInNotes(Integer id) {

        logger.info("Count trigger into Note of patient");
        List<NoteBean> noteList = noteProxy.listNote(id);
        List<String> triggersList = Trigger.getTriggerList();
        int countTriggers = 0;
        for (NoteBean note : noteList) {
            for (String trigger : triggersList) {
                if (note.getNote().contains(trigger)) {
                    countTriggers++;
                }
            }
        }
        logger.info("Found  " + countTriggers + " trigger to Note of patient");
        return countTriggers;
    }

}

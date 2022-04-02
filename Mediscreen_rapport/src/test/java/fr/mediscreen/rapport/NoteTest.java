package fr.mediscreen.rapport;

import fr.mediscreen.rapport.beans.NoteBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class NoteTest {

    @Test
    public void testNote() {

        // Given
        NoteBean note = new NoteBean();

        // When
        note.setId("id1");
        note.setDateNote(LocalDate.now());
        note.setPatId(1);
        note.setNote("Test Note");

        // Then
        Assertions.assertEquals("id1", note.getId());
        Assertions.assertEquals(LocalDate.now(), note.getDateNote());
        Assertions.assertEquals(1, note.getPatId());
        Assertions.assertEquals("Test Note", note.getNote());

    }


}

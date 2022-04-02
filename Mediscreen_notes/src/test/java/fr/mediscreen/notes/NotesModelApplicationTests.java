package fr.mediscreen.notes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class NotesModelApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void main() {
        NotesApplication.main(new String[]{});
    }

}

import model.connection.JDBC;
import model.model.PwdEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTest {
    private static String db_name = String.format("./testDatabase_%s", System.currentTimeMillis());
    private static String username = "testUser";
    private static String password = "12345678";
    private static PwdEntity entity = new PwdEntity(0,"TestTitle","username","1234","note");
    private static JDBC connection = new JDBC(db_name, username, password);

    @AfterAll
    static void end() {
        connection.closeConnection();
        try {
            Files.delete(Path.of(db_name + ".mv.db"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void init() {
        connection.removeAll();
        connection.addPwd(entity);
    }

    @Test
    void connectionIsReady() {
        assertTrue(connection.isReady());
        assertNull(connection.getError());
    }

    @Test
    void getRow() {
        PwdEntity e = connection.getPwd(entity.getId());

        assertEquals(e.getTitle(),entity.getTitle());
        assertEquals(e.getUsername(),entity.getUsername());
        assertEquals(e.getPassword(),entity.getPassword());
        assertEquals(e.getNote(),entity.getNote());
    }

    @Test
    void modifyRow() {
        String newTitle = "NewTestTitle";
        String newUser = "NewUser";
        String newNote = "Note2";

        entity.setTitle(newTitle);
        entity.setUsername(newUser);
        entity.setNote(newNote);

        connection.updateRow(entity);
        PwdEntity e = connection.getPwd(entity.getId());

        assertEquals(newTitle, e.getTitle());
        assertEquals(newUser, e.getUsername());
        assertEquals(newNote, e.getNote());
    }

    @Test
    void removeRow() {
        connection.addPwd(entity);
        connection.addPwd(entity);

        ArrayList<PwdEntity> pwds = connection.getAllPwd();
        int size = pwds.size();
        assertNotEquals(0,size);

        connection.removeRow(pwds.get(0));
        pwds = connection.getAllPwd();
        assertNotEquals(size, pwds.size());
    }

    @Test
    void removeAllRow() {
        connection.removeAll();
        ArrayList<PwdEntity> pwds = connection.getAllPwd();
        assertEquals(0, pwds.size());
    }
}

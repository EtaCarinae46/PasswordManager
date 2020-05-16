import controller.Props;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropTest {

    @BeforeAll
    static void init() {
        Props.createProp();
    }

    @Test
    void configFileSouldExist() {
        assertTrue(Props.exists());
    }

    @Test
    void setAndGetPropValues() {
        String setkey = "KEY2";

        String val = "value01";
        Props.setProp(setkey, val);
        assertEquals(Props.getProp(setkey),val);

        val = "value02";
        Props.setProp(setkey, val);
        assertEquals(Props.getProp(setkey),val);

        val = "value03";
        Props.setProp(setkey, val);
        assertEquals(Props.getProp(setkey),val);

        Props.removeProp(setkey);
        assertNull(Props.getProp(setkey));

    }
}

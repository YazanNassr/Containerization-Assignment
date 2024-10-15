import org.junit.jupiter.api.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class VolatileLoginManagerTest {
    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = VolatileLoginManager.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void testRegisterUser() {
        var loginManager = VolatileLoginManager.getInstance();
        String username = "user";
        String password = "password";

        assertTrue(loginManager.availableUsername(username));
        loginManager.registerUser(username, password);
        assertTrue(loginManager.validCredentials(username, password));
    }

    @Test
    void testAvailableUsername() {
        var loginManager = VolatileLoginManager.getInstance();
        String username = "user";
        String password = "password";

        assertTrue(loginManager.availableUsername(username));
        loginManager.registerUser(username, password);
        assertFalse(loginManager.availableUsername(username));
    }

    @Test
    void testValidCredentials() {
        var loginManager = VolatileLoginManager.getInstance();
        String username = "user";
        String correctPassword = "password";
        String erroneousPassword = "password1";

        loginManager.registerUser(username, correctPassword);

        assertFalse(loginManager.validCredentials(username, erroneousPassword));
    }
}

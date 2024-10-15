import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VolatileLoginManager implements LoginManager {
    private Map<String, String> map = new ConcurrentHashMap<>();

    private static volatile VolatileLoginManager instance;

    public static VolatileLoginManager getInstance() {
        if (instance == null) {
            synchronized (VolatileLoginManager.class) {
                if (instance == null) {
                    instance = new VolatileLoginManager();
                }
            }
        }
        return instance;
    }

    private VolatileLoginManager() { }

    @Override
    public boolean validCredentials(String username, String password) {
        if (availableUsername(username)) {
            return false;
        }

        return map.get(username).equals(password);
    }

    @Override
    public void registerUser(String username, String password) {
        map.put(username, password);
    }

    @Override
    public boolean availableUsername(String username) {
        return !map.containsKey(username);
    }
}

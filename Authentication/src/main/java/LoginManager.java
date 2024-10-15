public interface LoginManager {
    boolean validCredentials(String username, String password);
    void registerUser(String username, String password);
    boolean availableUsername(String username);
}

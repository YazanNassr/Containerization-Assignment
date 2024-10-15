import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthenticatorServlet extends HttpServlet {
    private final LoginManager loginManager = VolatileLoginManager.getInstance();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String type = request.getParameter("type");

        boolean valid = false;

        if (type.equals("login")) {
            if (loginManager.validCredentials(username, password)) {
                valid = true;
            }
        }

        if (type.equals("register")) {
            if (loginManager.availableUsername(username)) {
                loginManager.registerUser(username, password);
                valid = true;
            }
        }

        if (valid) {
            response.getWriter().println("valid");
        } else {
            response.getWriter().println("invalid");
        }
    }
}

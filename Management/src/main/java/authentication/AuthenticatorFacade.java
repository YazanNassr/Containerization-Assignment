package authentication;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class AuthenticatorFacade extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (authenticate(extractPostData(request))) {
            request.getSession().setAttribute("username", request.getParameter("username"));
            response.sendRedirect("/manager/home");
        } else {
            response.sendRedirect("/manager/login");
        }
    }

    private String extractPostData(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String type = request.getParameter("type");

        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("type", type);

        StringBuilder postData = new StringBuilder();
        for (var param : params.entrySet()) {
            if (!postData.isEmpty()) postData.append('&');
            postData.append(param.getKey());
            postData.append('=');
            postData.append(param.getValue());
        }

        return postData.toString();
    }

    private boolean authenticate(String postData) throws IOException {
        byte[] postDataBytes = postData.getBytes();
        int postDataLength = postDataBytes.length;

        URL url = new URL("http://auth:8080/authenticator/authenticate");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        connection.setUseCaches(false);

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(postDataBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int status = connection.getResponseCode();

        if (status != HttpURLConnection.HTTP_OK) {
            return false;
        }

        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (response.toString().equals("valid")) {
                return true;
            }
        }

        return false;
    }
}

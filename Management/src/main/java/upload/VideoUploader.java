package upload;

import database.VideoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.SocketException;

public class VideoUploader extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = (String) request.getSession().getAttribute("username");

        Part part = request.getPart("video-file");
        String filename = part.getSubmittedFileName();

        String videoName = request.getParameter("video-name");
        if (videoName == null) {
            videoName = filename;
        }

        String dir = "/home/videos/";
        String path = dir + username + "___" + videoName;

        boolean success = uploadFile(path, part.getInputStream());

        if (success) {
            VideoDAO.getInstance().addVideo(username, videoName, path);
            response.sendRedirect("/manager/home");
        } else {
            response.sendRedirect("/manager/upload");
        }

    }


    private boolean uploadFile(String filePath, InputStream inputStream) throws FileNotFoundException {
        String server = "fs"; int port = 21;
        String user = "webapp"; String password = "1234";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            try {
                return ftpClient.storeFile(filePath, inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return  false;
    }

}

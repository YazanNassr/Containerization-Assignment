package streaming;

import database.VideoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.SocketException;

public class VideoStreamer extends HttpServlet {

    private static final int BUFFER_SIZE = 1024 * 64; // 64KB buffer

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = (String) request.getSession().getAttribute("username");
        String videoName = request.getParameter("name");
        String videoPath = VideoDAO.getInstance().path(username, videoName);

        response.setContentType("video/mp4");
        // response.setContentLengthLong();
        response.setHeader("Content-Disposition", "inline;filename=\"" + videoName + "\"");
        try (BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            streamFile(videoPath, outputStream);
        }
    }

    private boolean streamFile(String path, OutputStream outputStream) throws FileNotFoundException {
        String server = "fs"; int port = 21;
        String user = "webapp"; String password = "1234";

        boolean success = false;

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            try {
                 var in = ftpClient.retrieveFileStream(path);
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                while ((bytesRead = in.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                ftpClient.completePendingCommand();
                return true;
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

        return false;
    }
}

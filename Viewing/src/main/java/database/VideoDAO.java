package database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class VideoDAO implements IVideoDAO {
    DataSource dataSource = VideoDataSource.getInstance();

    private static final VideoDAO instance = new VideoDAO();

    public static VideoDAO getInstance() {
        return instance;
    }

    private VideoDAO() { }


    @Override
    public void addVideo(String owner, String name, String path) {
        try (Connection connection = dataSource.getConnection()){

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO video (owner, name, path) VALUES (?, ?, ?)");
            ps.setString(1, owner);
            ps.setString(2, name);
            ps.setString(3, path);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String path(String owner, String videoName) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT path FROM video WHERE owner = ? AND name = ?");
            ps.setString(1, owner);
            ps.setString(2, videoName);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("path");
            } else {
                return "";
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<String> videoNames(String owner) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT name FROM video WHERE owner = ?");
            ps.setString(1, owner);
            ResultSet resultSet = ps.executeQuery();

            List<String> names = new LinkedList<>();
            while (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }

            return names;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}

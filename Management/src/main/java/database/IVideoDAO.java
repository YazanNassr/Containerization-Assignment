package database;

import java.util.List;

public interface IVideoDAO {
    void addVideo(String owner, String name, String path);
    String path(String owner, String videoName);
    List<String> videoNames(String owner);
}

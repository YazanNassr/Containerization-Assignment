package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class VideoDataSource {
    private static DataSource instance = initDataSource();

    public static DataSource getInstance() {
        return instance;
    }

    private VideoDataSource() { }

    public Connection getConnection() throws SQLException {
        return instance.getConnection();
    }

    private static DataSource initDataSource() {
        String url = "jdbc:mysql://db:3306/videoStreaming";
        String name = "managementApp";
        String password = "password";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(name);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "50");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

}

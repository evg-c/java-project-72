package hexlet.code.repository;

import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.model.Url;

public class BaseRepository {
    public static Url url;
    public static HikariDataSource dataSource;
}

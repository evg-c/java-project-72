package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.model.Urls;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hexlet.code.controller.UrlsController.statusCodeToString;
import static hexlet.code.controller.UrlsController.timeStampToString;

public class UrlsRepository extends BaseRepository {
    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?,?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.setTimestamp(2, url.getCreatedAt());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            // устанавливаем ID в сохраненную сущность
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at");
                var url = new Url(name, createdAt);
                url.setId(id);
                result.add(url);
            }
            return result;
        }
    }

    public static List<Urls> getEntitiesFull() throws SQLException {
        String sql =
            "SELECT t1.id, t1.name, t2.max_date, t3.status_code " +
            "FROM urls t1 " +
            "LEFT JOIN ( " +
            "  SELECT url_id, MAX(created_at) AS max_date " +
            "  FROM url_checks GROUP BY url_id) t2 " +
            "  ON t1.id = t2.url_id " +
            "LEFT JOIN url_checks t3 " +
            "  ON t1.id = t3.url_id AND t2.max_date = t3.created_at ";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<Urls>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var maxDate = resultSet.getTimestamp("max_date");
                var status_code = resultSet.getInt("status_code");
                var url = new Url(name, maxDate);
                url.setId(id);
                var urls = new Urls(url, timeStampToString(maxDate), statusCodeToString(status_code));
                result.add(urls);
            }
            return result;
        }
    }

    public static Optional<Url> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at");
                var url = new Url(name, createdAt);
                url.setId(id);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static Optional<Url> findByName(String nameUrl) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nameUrl);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var id = resultSet.getLong("id");
                var createdAt = resultSet.getTimestamp("created_at");
                var url = new Url(nameUrl, createdAt);
                url.setId(id);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }
}

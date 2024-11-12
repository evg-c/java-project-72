package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChecksRepository extends BaseRepository {
    public static void save(UrlCheck urlCheck) throws SQLException {
        String sql = "INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
                var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, urlCheck.getStatusCode());
            preparedStatement.setString(2, urlCheck.getTitle());
            preparedStatement.setString(3, urlCheck.getH1());
            preparedStatement.setString(4, String.valueOf(urlCheck.getDescription()));
            preparedStatement.setLong(5, urlCheck.getUrlId());
            //preparedStatement.setTimestamp(6, urlCheck.getCreatedAt());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            // устанавливаем ID в сохраненную сущность
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<UrlCheck> getEntities(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";
        try (var conn = dataSource.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<UrlCheck>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var statusCode = resultSet.getInt("status_code");
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");
                var createdAt = resultSet.getTimestamp("created_at");
                var check = new UrlCheck(statusCode, title, h1, new StringBuilder(description), urlId, createdAt);
                check.setId(id);
                result.add(check);
            }
            return result;
        }
    }

    public static Timestamp lastDateOfCheck(Long urlId) throws SQLException {
        //String sql = "SELECT MAX(created_at) AS latestDateOfCheck FROM url_checks WHERE url_id = ?";
        String sql = "SELECT created_at FROM url_checks WHERE url_id = ? ORDER BY created_at DESC LIMIT 1";
        try (var conn = dataSource.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                var maxCreatedAt = resultSet.getTimestamp("created_at");
                return maxCreatedAt;
            }
            return null;
        }
    }

    public static int lastStatusCode(Long urlId) throws SQLException {
        //String sql = "SELECT status_code FROM url_checks "
        //        + "WHERE url_id = ? AND created_at = (SELECT MAX(created_at) FROM url_checks WHERE url_id = ?)";
        String sql = "SELECT status_code FROM url_checks WHERE url_id = ? ORDER BY created_at DESC LIMIT 1";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            //stmt.setLong(2, urlId);
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                var statusCode = resultSet.getInt("status_code");
                return statusCode;
            }
            return 0;
        }
    }
}

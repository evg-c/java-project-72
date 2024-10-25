package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.ChecksRepository;
import hexlet.code.repository.UrlsRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    Javalin app;

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        }));
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            System.out.println(response.body().string());
        });
    }

//    @Test
//    void testIndex() {
//        JavalinTest.test(app, (server, client) -> {
//            var response = client.get("/urls");
//            assertThat(response.code()).isEqualTo(200);
//            assertThat(response.body().string())
//                    .contains(existingUrl.get("name").toString())
//                    .contains(existingUrlCheck.get("status_code").toString());
//        });
//    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, ((server, client) -> {
            var requestBody = "url=http://google.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("http://google.com");
        }));
    }

    @Test
    public void testUrlPage() throws SQLException {
        var url = new Url("http://google.com", Timestamp.valueOf(LocalDateTime.now()));
        UrlsRepository.save(url);
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
        }));
    }

    @Test
    public void testUrlNotFound() {
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        }));
    }

    @Test
    public void testCreateCheck() throws SQLException {
        var url = new Url("http://google.com", Timestamp.valueOf(LocalDateTime.now()));
        UrlsRepository.save(url);
        var check = new UrlCheck(200, "", "", new StringBuilder(""), url.getId(),
                Timestamp.valueOf(LocalDateTime.now()));
        ChecksRepository.save(check);
        JavalinTest.test(app, ((server, client) -> {
            var response = client.post("/urls/" + url.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);
        }));
    }

    @Test
    public void testCheckPage() throws SQLException {
        var url = new Url("http://google.com", Timestamp.valueOf(LocalDateTime.now()));
        UrlsRepository.save(url);
        var check = new UrlCheck(200, "", "", new StringBuilder(""), url.getId(),
                Timestamp.valueOf(LocalDateTime.now()));
        ChecksRepository.save(check);
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/urls/" + url.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);
        }));
    }
}

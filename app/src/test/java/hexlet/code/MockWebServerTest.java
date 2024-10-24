package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.ChecksRepository;
import hexlet.code.repository.UrlsRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class MockWebServerTest {
    MockWebServer mockWebServer;
    OkHttpClient client;
    @BeforeEach
    public final void setServerUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        client = new OkHttpClient();
    }

    @AfterEach
    public final void setDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testCheck() throws IOException, SQLException {
        String mockResponse = "";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponse)
                .setResponseCode(200));
        String baseUrl = mockWebServer.url("/").toString();
        var url = new Url(baseUrl, Timestamp.valueOf(LocalDateTime.now()));
        UrlsRepository.save(url);
        Request request = new Request.Builder()
                .url(baseUrl)
                .build();
        var check = new UrlCheck(200, "", "", new StringBuilder(""), url.getId(),
                Timestamp.valueOf(LocalDateTime.now()));
        ChecksRepository.save(check);
        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertEquals(mockResponse, response.body().string());
        }
    }
}

package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    public static Javalin getApp() {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getJdbcUrl());

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> {
            ctx.result("Hello World");
        });
        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(getPort());
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    private static String getJdbcUrl() {
        String jdbcUrlH2 = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";
        String jdbcUrlDeploy = System.getenv().getOrDefault("JDBC_DATABASE_URL", jdbcUrlH2);
        return jdbcUrlDeploy;
    }
}

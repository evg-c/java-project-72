package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Slf4j
public class App {
    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        //String jdbcUrlH2 = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";
        //String jdbcUrlPostgres =
        //        "jdbc:postgresql://dpg-cqsg6rbqf0us739084g0-a.frankfurt-postgres.render.com:5432/db_project_72?password=A7NoRJzQZ40owUWRAk9FJl37NVy1dBKI&user=db_project_72_user";
        //String jdbcUrlDeploy = System.getenv().getOrDefault("JDBC_DATABASE_URL", jdbcUrlH2);
        hikariConfig.setJdbcUrl(getJdbcUrl());
        //hikariConfig.setJdbcUrl(jdbcUrlPostgres);

        var dataSource = new HikariDataSource(hikariConfig);
        var sql = readResourceFile("schema.sql");
        log.info(sql);

        try (var connection = dataSource.getConnection();
            var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get("/", ctx -> {
            //ctx.result("Hello World");
            ctx.render("index.jte");
        });
        return app;
    }

    public static void main(String[] args) throws IOException, SQLException {
        Javalin app = getApp();
        app.start(getPort());
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    private static String getJdbcUrl() {
        String jdbcUrlH2 = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";
        //String jdbcUrlPostgres =
        //        "jdbc:postgresql://dpg-cqsg6rbqf0us739084g0-a.frankfurt-postgres.render.com:5432/db_project_72?password=A7NoRJzQZ40owUWRAk9FJl37NVy1dBKI&user=db_project_72_user";
        String jdbcUrlDeploy = System.getenv().getOrDefault("JDBC_DATABASE_URL", jdbcUrlH2);
        return jdbcUrlDeploy;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }

    private static String readResourceFile(String filename) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(filename);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}

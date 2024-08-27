package hexlet.code.controller;

import hexlet.code.NamedRoutes;
import hexlet.code.dto.BuildUrlPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static String to_be_added_url;
    public static String flashMessage = "";
    public static StringBuilder stringException = new StringBuilder();
    public static void build(Context ctx) {
        var page = new BuildUrlPage();
        ctx.render("index.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var createdAt = Timestamp.valueOf(LocalDateTime.now());
        try {
            var name = ctx.formParamAsClass("url", String.class)
                    .check(value -> isUrl(value), flashMessage)
                    .check(value -> urlNotFoundInDB(value), flashMessage)
                    .get();
            var url = new Url(to_be_added_url, createdAt);
            UrlsRepository.save(url);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (ValidationException e) {
            ctx.sessionAttribute("flash", flashMessage);
            var name = ctx.formParam("url");
            var page = new BuildUrlPage(name, e.getErrors());
            stringException.append(e);
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("index.jte", model("page", page));
        }
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlsRepository.getEntities();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls_index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.find_by_id(id)
                .orElseThrow(() -> new NotFoundResponse("URL with id = " + id + " not found"));
        var page = new UrlPage(url);
        ctx.render("url_show.jte", model("page", page));
    }

    public static boolean isUrl(String nameUrl) {
        try {
            URI uri = new URI(nameUrl);
            URL entered_url = uri.toURL();
            String port = (entered_url.getPort() > 1) ? ":" + String.valueOf(entered_url.getPort()) : "";
            to_be_added_url = entered_url.getProtocol() + "://" + entered_url.getHost() + port;
        } catch (URISyntaxException | MalformedURLException e) {
            stringException.append(e);
            flashMessage = "Некорректный URL";
            return false;
        }
        return true;
    }

    public static boolean urlNotFoundInDB(String nameUrl) {
        try {
            if (UrlsRepository.find_by_name(to_be_added_url).orElse(null) == null) {
                return true;
            }
        } catch (SQLException e) {
            stringException.append(e);
            return false;
        }
        flashMessage = "Страница уже существует";
        return false;
    }
}

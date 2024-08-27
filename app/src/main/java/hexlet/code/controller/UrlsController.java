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

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static String toBeAddedUrl;
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
            var url = new Url(toBeAddedUrl, createdAt);
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
        var url = UrlsRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("URL with id = " + id + " not found"));
        var page = new UrlPage(url);
        ctx.render("url_show.jte", model("page", page));
    }

    public static boolean isUrl(String nameUrl) {
        try {
            URI uri = new URI(nameUrl);
            URL enteredUrl = uri.toURL();
            String port = (enteredUrl.getPort() > 1) ? ":" + String.valueOf(enteredUrl.getPort()) : "";
            toBeAddedUrl = enteredUrl.getProtocol() + "://" + enteredUrl.getHost() + port;
        } catch (URISyntaxException | MalformedURLException e) {
            stringException.append(e);
            flashMessage = "Некорректный URL";
            return false;
        }
        return true;
    }

    public static boolean urlNotFoundInDB(String nameUrl) {
        try {
            if (UrlsRepository.findByName(toBeAddedUrl).orElse(null) == null) {
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

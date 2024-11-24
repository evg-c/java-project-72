package hexlet.code.controller;

import hexlet.code.NamedRoutes;
import hexlet.code.dto.BuildUrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.ChecksRepository;
import hexlet.code.repository.UrlsRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (ValidationException e) {
            ctx.sessionAttribute("flash", flashMessage);
            ctx.sessionAttribute("flash-type", "danger");
            var name = ctx.formParam("url");
            //var page = new BuildUrlPage(name, e.getErrors());
            var page = new BuildUrlPage();
            //stringException.append(e.getMessage());
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("index.jte", model("page", page));
        }
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlsRepository.getEntitiesFull();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/urls_index.jte", model("page", page));
    }

    public static void check(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("URL with id = " + id + " not found"));
        try {
            // здесь проверка конкретного url
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            // заполение полей urlCheck полученными результатами
            var statusCode = response.getStatus();
            var body = response.getBody();
            var urlId = id;
            Document doc = Jsoup.parse(body);
            String title = doc.title() == null ? "" : doc.title();
            Element tagH1 = doc.selectFirst("h1");
            String h1 = tagH1 == null ? "" : tagH1.text();
            Elements nameDescription = doc.select("meta[name=description]");
            String description = nameDescription == null ? "" : nameDescription.attr("content");
            //String description = doc.select("meta[name=description]").attr("content");
            //String title = "";
            //if (body.contains("<title>")) {
            //    title = body.substring(body.indexOf("<title>") + 7, body.indexOf("</title>"));
            //}
            //String h1 = "";
            //if (body.contains("<h1>")) {
            //    h1 = body.substring(body.indexOf("<h1>") + 4, body.indexOf("</h1>"));
            //}
            //String description = "";
            //if (body.contains("Description")) {
            //    String descriptionToEnd = body.substring(body.indexOf("Description") + 22);
            //    description = descriptionToEnd.substring(0, descriptionToEnd.indexOf("\" />"));
            //}
            StringBuilder desc = new StringBuilder(description);
            var check = new UrlCheck(statusCode, title, h1, desc, urlId, null);
            // сохраняем в репозиторий
            ChecksRepository.save(check);
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flash-type", "success");
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flash-type", "danger");
        } catch (Exception ex) {
            ctx.sessionAttribute("flash", ex.getMessage());
            ctx.sessionAttribute("flash-type", "danger");
        } finally {
            Unirest.shutDown();
        }
        ctx.redirect(NamedRoutes.urlCheck(id));
    }

    public static boolean isUrl(String nameUrl) {
        try {
            URI uri = new URI(nameUrl);
            URL enteredUrl = uri.toURL();
            String port = (enteredUrl.getPort() > 1) ? ":" + String.valueOf(enteredUrl.getPort()) : "";
            toBeAddedUrl = enteredUrl.getProtocol() + "://" + enteredUrl.getHost() + port;
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException e) {
            stringException.append(e.getMessage());
            flashMessage = "Некорректный URL";
            return false;
        }
        return true;
    }

    public static boolean urlNotFoundInDB(String nameUrl) {
        try {
            URI uri = new URI(nameUrl);
            URL enteredUrl = uri.toURL();
            String port = (enteredUrl.getPort() > 1) ? ":" + String.valueOf(enteredUrl.getPort()) : "";
            toBeAddedUrl = enteredUrl.getProtocol() + "://" + enteredUrl.getHost() + port;
            if (UrlsRepository.findByName(toBeAddedUrl).orElse(null) == null) {
                return true;
            }
        } catch (Exception e) {
            stringException.append(e.getMessage());
            return false;
        }
        flashMessage = "Страница уже существует";
        return false;
    }

    public static String timeStampToString(Timestamp stamp) {
        String dateTime = (stamp == null ? ""
                : String.valueOf(stamp.toLocalDateTime().withSecond(0).withNano(0)))
                .replaceFirst("T", " ");
        return dateTime;
    }

    public static String statusCodeToString(int statusCode) {
        String lastStatusCode = (statusCode == 0 ? "" : String.valueOf(statusCode));
        return lastStatusCode;
    }
}

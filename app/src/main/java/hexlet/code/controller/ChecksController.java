package hexlet.code.controller;

import hexlet.code.dto.UrlCheckPage;
import hexlet.code.repository.ChecksRepository;
import hexlet.code.repository.UrlsRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class ChecksController {
    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("URL with id = " + id + " not found"));
        var urlName = url.getName();
        var createdUrl = url.getCreatedAt();
        var checks = ChecksRepository.getEntities(id);
        var page = new UrlCheckPage(id, urlName, createdUrl, checks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/checks_index.jte", model("page", page));
    }
}

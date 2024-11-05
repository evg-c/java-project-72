package hexlet.code.dto;

import hexlet.code.model.Urls;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UrlsPage extends BasePage {
    private List<Urls> urls;
}

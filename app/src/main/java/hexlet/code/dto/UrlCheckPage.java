package hexlet.code.dto;

import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@AllArgsConstructor
public class UrlCheckPage extends BasePage {
    private Long idUrl;
    private String nameUrl;
    private Timestamp createdUrl;
    private List<UrlCheck> urlCheck;
}

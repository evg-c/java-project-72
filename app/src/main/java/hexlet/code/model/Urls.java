package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Urls {
    private Url url;
    private String lastDate;
    private String statusCode;
    public Urls(Url url, String lastDate, String statusCode) {
        this.url = url;
        this.lastDate = lastDate;
        this.statusCode = statusCode;

    }
}

package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
public class Urls {
    private Url url;
    private String lastDate;
    private String status_code;
    public Urls(Url url, String lastDate, String status_code) {
        this.url = url;
        this.lastDate = lastDate;
        this.status_code = status_code;

    }
}

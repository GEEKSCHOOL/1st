package jp.geekschool.web.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Message {

    private String id;
    private String userId;
    private String text;
    private String profilePictureUrl;
    private LocalDateTime localDateTime;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    // TODO getter, setter をEclipseの機能を使って生成しよう！

    public long getTime() {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime();
    }

}


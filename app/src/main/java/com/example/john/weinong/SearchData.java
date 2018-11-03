package com.example.john.weinong;

/**
 * Created by john on 2018/9/4.
 */

public class SearchData {

    private String id;
    private String content;
    public String getId() {
        return id;
    }
    public SearchData setId(String id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SearchData setContent(String content) {
        this.content = content;
        return this;
    }
}

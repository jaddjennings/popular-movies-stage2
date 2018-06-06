package com.jennings.jadd.popular_movies_stage2.models;

public class MovieReviewObject {
    private String author;
    private String content;
    public MovieReviewObject(String authorP, String contentP) {
        setAuthor(authorP);
        setContent(contentP);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

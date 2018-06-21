package com.jennings.jadd.popular_movies_stage2.models;

public class MovieTrailerObject {

    private String key;
    private String name;

    public MovieTrailerObject(String keyP, String nameP) {
        key = keyP;
        name = nameP;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String keyP) {
        this.key = keyP;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameP) {
        this.name = name;
    }
}

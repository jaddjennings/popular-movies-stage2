package com.jennings.jadd.popular_movies_stage2.models;

public class MovieTrailerObject {

    private String key;
    private String name;

    public MovieTrailerObject(String keyP, String nameP) {
        setKey(keyP);
        setName(nameP);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String id) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.election.backendjava.entities;

public class Article {
    private final String title;
    private final String link;

    public Article(String title, String link) {
        this.title = title;
        this.link = link;
    }

    // Getters en Setters
    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

}

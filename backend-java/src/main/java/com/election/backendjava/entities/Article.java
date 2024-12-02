package com.election.backendjava.entities;

public class Article {
    private String title;
    private String link;
    private String pubDate;
    private String imageUrl;

    public Article(String title, String link, String pubDate, String imageUrl) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.imageUrl = imageUrl;
    }

    // Getters en Setters
    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

package com.election.backendjava.controllers;

import com.election.backendjava.entities.Article;
import com.election.backendjava.processors.RssParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private RssParser rssService;
    @GetMapping("/api/political-news")
    public List<Article> getPoliticalNews() {
        return rssService.getPoliticalNewsFromRSS();
    }
}

package com.election.backendjava.processors;

import com.election.backendjava.Article;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssParser {

    public List<Article> getPoliticalNewsFromRSS() {
        List<Article> newsList = new ArrayList<>();
        try {
            // RSS feed URL
            URL feedSource = new URL("https://www.nu.nl/rss/Politiek");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(feedSource.openStream());

            // Items ophalen uit RSS feed
            NodeList items = document.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Element element = (Element) items.item(i);

                String title = element.getElementsByTagName("title").item(0).getTextContent();
                String link = element.getElementsByTagName("link").item(0).getTextContent();

                // Maak een nieuw Artikel object
                Article article = new Article(title, link);
                newsList.add(article);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }
}

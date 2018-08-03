package com.html.parsers.parser.Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserAboutYouImpl implements ParserAboutYou {

    private Document document;

    @Override
    public String getName() {

        return document.select("span[class=styles__title--3Jos_]").html();
    }

    @Override
    public String getBrand() {
        return document.select("div[class=styles__brandName--2XS22]").first().html();
    }

    @Override
    public List<String> getColorsLinks() {
        return document.select("div[class=styles__thumbnailWrapper--3uDnG] > a[href]")
                .eachAttr("abs:href");
    }

    @Override
    public String getPrice() {
        String price = document.select("div[class=productPrices priceStyles__normal--3aCVn]").html();
        price = price.replaceAll("[a-z]*(<.*?--.*?-->)*\n*", "");
        return price;
    }

    @Override
    public String getInitialPrice() {
        String price = document.select("div[class=priceStyles__strike--PSBGK]").html();
        if (price.isEmpty()) {
            return "ABSENT";
        }
        price = price.replaceAll("[a-z]*(<.*?--.*?-->)*", "");
        return price;
    }

    @Override
    public String getDescription() {
        List<String> descriptions = new ArrayList<>();
        document.select("div[class=styles__accordionContainer--1dPP0]")
                .forEach(ul ->
                        descriptions.add(ul.select("ul[class] > li").html()));
        String result = "";
        for (String description : descriptions) {
            result += description + " ";
        }
        return result;
    }

    @Override
    public String getArticleId() {

        String article = document.select("li[class=styles__articleNumber--1UszN]").html();
        article = article.replaceAll("Artikel-Nr: ", "");
        return article;
    }

    @Override
    public String getShippingCosts() {
        return "Zero";
    }

    @Override
    public String getNextPage() {
        return document.select("li[class=styles__buttonNext--3YXvj] > a[href]")
                .attr("abs:href");
    }

    @Override
    public List<String> getLinks() {

        if(document == null) {
            return new ArrayList<>();
        }
        List<String> links = new ArrayList<>();
        String cssQuery = "div[class=row] > div[class=styles__container--1bqmB]";
        document.select(cssQuery).forEach(element ->
                element.select("div[data-tracking-qa] > a[href]")
                        .eachAttr("abs:href")
                        .forEach(link -> links.add(link)));
        return links;
    }

    @Override
    public void setDocument(String url) {

        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

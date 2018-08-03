package com.html.parser.parser;

import com.html.parser.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ParserAboutYouImpl implements Parser {

    @Override
    public List<Product> parse(Document document, String url) {

        Random random = new Random();
        List<Product> products =  new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);

        try {

            document = Jsoup.connect(url).get();
            counter.getAndIncrement();
            String brand = getBrand(document);

            while (true) {
                getLinks(document).forEach((String link) -> {
                    delay(random);
                    Product product = new Product();
                    Document inst = null;
                    try {
                        inst = Jsoup.connect(link).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    counter.getAndIncrement();
                    product.setName(getName(inst));
                    product.setBrand(brand);
                    List<String> colors = new ArrayList<>();
                    getColorsLinks(inst).forEach(linkToColor -> {
                        delay(random);
                        colors.add(linkToColor);
                        counter.getAndIncrement();
                    });
                    product.setColors(colors);
                    product.setPrice(getPrice(inst));
                    product.setInitialPrice(getInitialPrice(inst));
                    product.setDescription(getDescription(inst));
                    product.setArticleId(getArticleId(inst));
                    product.setShippingCosts(getShippingCosts());
                    products.add(product);
                });

                String nextPage = getNextPage(document);
                if (nextPage.isEmpty()) {
                    break;
                }
                document = Jsoup.connect(nextPage).get();
                counter.getAndIncrement();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Amount of triggered HTTP request: " + counter);
        System.out.println("Amount of extracted products: " + products.size());

        return products;
    }

    private String getName(Document document) {

        return document.select("span[class=styles__title--3Jos_]").html();
    }

    private String getBrand(Document document) {
        Elements elements = document.select("div[class=styles__brandName--2XS22]");
        String str =elements.first().text();
        return str;
    }

    private List<String> getColorsLinks(Document document) {
        return document.select("div[class=styles__thumbnailWrapper--3uDnG] > a[href]")
                .eachAttr("abs:href");
    }

    private String getPrice(Document document) {
        String price = document.select("div[class=productPrices priceStyles__normal--3aCVn]").html();
        price = price.replaceAll("[a-z]*(<.*?--.*?-->)*\n*", "");
        return price;
    }

    private String getInitialPrice(Document document) {
        String price = document.select("div[class=priceStyles__strike--PSBGK]").html();
        if (price.isEmpty()) {
            return Optional.empty().toString();
        }
        price = price.replaceAll("[a-z]*(<.*?--.*?-->)*", "");
        return price;
    }

    private String getDescription(Document document) {
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

    private String getArticleId(Document document) {

        String article = document.select("li[class=styles__articleNumber--1UszN]").html();
        article = article.replaceAll("Artikel-Nr: ", "");
        return article;
    }

    private String getShippingCosts() {
        return "0";
    }

    private String getNextPage(Document document) {
        return document.select("li[class=styles__buttonNext--3YXvj] > a[href]")
                .attr("abs:href");
    }

    private List<String> getLinks(Document document) {

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

    private void delay(Random random) {
        try {
            Thread.sleep(random.nextInt(1550) + 1750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

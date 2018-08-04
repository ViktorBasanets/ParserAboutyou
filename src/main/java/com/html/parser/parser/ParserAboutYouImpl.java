package com.html.parser.parser;

import com.html.parser.Const;
import com.html.parser.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ParserAboutYouImpl implements Parser {

    private ResourceBundle bundle = ResourceBundle.getBundle("expressions");

    @Override
    public List<Product> parse(Document document, String url) {

        Random random = new Random();
        List<Product> products = new ArrayList<>();
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
                    System.out.println(products.size() + ") " + product.getName() + " ...");
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

        return document.select(bundle.getString("getName")).html();
    }

    private String getBrand(Document document) {
        Elements elements = document.select(bundle.getString("getBrand"));
        String str = elements.first().text();
        return str;
    }

    private List<String> getColorsLinks(Document document) {
        return document.select(bundle.getString("getColorsLinks"))
                .eachAttr(bundle.getString("getHref"));
    }

    private String getPrice(Document document) {
        String price = document.select(bundle.getString("getPrice")).html();
        price = price.replaceAll(bundle.getString("replaceAll"), "");
        return price;
    }

    private String getInitialPrice(Document document) {
        String price = document.select(bundle.getString("getInitialPrice")).html();
        if (price.isEmpty()) {
            return Optional.empty().toString();
        }
        price = price.replaceAll(bundle.getString("replaceAll"), "");
        return price;
    }

    private String getDescription(Document document) {
        List<String> descriptions = new ArrayList<>();
        document.select(bundle.getString("getDescription.div")).forEach(ul ->
                descriptions.add(ul.select(bundle.getString("getDescription.ul")).html()));
        String result = "";
        for (String description : descriptions) {
            result += description + " ";
        }
        return result;
    }

    private String getArticleId(Document document) {

        String article = document.select(bundle.getString("getArticleId")).html();
        article = article.replaceAll(bundle.getString("replaceArt"), "");
        return article;
    }

    private String getShippingCosts() {
        return "0";
    }

    private String getNextPage(Document document) {
        return document.select(bundle.getString("getNextPage"))
                .attr(bundle.getString("getHref"));
    }

    private List<String> getLinks(Document document) {

        if (document == null) {
            return new ArrayList<>();
        }
        List<String> links = new ArrayList<>();
        document.select(bundle.getString("getLinks.div1")).forEach(element ->
                element.select(bundle.getString("getLinks.div2"))
                        .eachAttr(bundle.getString("getHref"))
                        .forEach(link -> links.add(link)));
        return links;
    }

    private void delay(Random random) {
        try {
            Thread.sleep(random.nextInt(Const.BOTTOM_EDGE) + Const.TOP_FACE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

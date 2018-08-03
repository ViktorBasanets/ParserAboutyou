package com.html.parsers.parser;

import com.html.parsers.parser.Model.Product;
import com.html.parsers.parser.Parser.Parser;
import com.html.parsers.parser.Parser.ParserAboutYouImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static int counterOfRequests;

    public static void main(String[] args)
            throws IOException {

        long countingTime = System.currentTimeMillis();
        long memorySize = deltaOfMemory();

        int theNumberOfTicks = 1000;
        int byteInMb = 1048576;

        Random random = new Random();
        String url = "https://www.aboutyou.de/suche?term=" + args[0];

        Parser parser = new ParserAboutYouImpl();
        List<Product> products =  new ArrayList<>();
        parser.setDocument(url);
        String brand = parser.getBrand();
        while (true) {

            increaseCnt();
            parser.getLinks().forEach(link -> {
                delay(random);
                Product product = new Product();
                parser.setDocument(link);
                increaseCnt();
                product.setName(parser.getName());
                product.setBrand(brand);
                List<String> colors = new ArrayList<>();
                parser.getColorsLinks().forEach(linkToColor -> {
                    delay(random);
                    colors.add(linkToColor);
                    increaseCnt();
                });
                product.setColors(colors);
                product.setPrice(parser.getPrice());
                product.setInitialPrice(parser.getInitialPrice());
                product.setDescription(parser.getDescription());
                product.setArticleId(parser.getArticleId());
                product.setShippingCosts(parser.getShippingCosts());
                products.add(product);
            });

            String nextPage = parser.getNextPage();
            if (nextPage.isEmpty()) {
                break;
            }
            parser.setDocument(nextPage);
            increaseCnt();
        }

        long deltaTime = System.currentTimeMillis() - countingTime;
        long deltaMemory = deltaOfMemory() - memorySize;

        System.out.println("Amount of triggered HTTP request: " + getCounterOfRequests());
        System.out.println("Run-time: " + deltaTime / theNumberOfTicks + " sec");
        System.out.println("Memory footprint: " + deltaMemory / byteInMb + " MB");
        System.out.println("Amount of extracted products: " + products.size());
    }

    private static long deltaOfMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    private static void delay(Random random) {
        try {
            Thread.sleep(random.nextInt(1550) + 1750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void increaseCnt() {
        counterOfRequests++;
    }

    private static int getCounterOfRequests() {
        return counterOfRequests;
    }
}

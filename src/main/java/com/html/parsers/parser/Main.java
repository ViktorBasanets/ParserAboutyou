package com.html.parsers.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args)
            throws IOException {

        long countingTime = System.currentTimeMillis();
        long memorySize = deltaOfMemory();

        String keyword = args[0];
        String url = "https://www.aboutyou.de/suche?term=";
        String cssQueryLinks = "div[class=row] > div[class=styles__container--1bqmB]";
        String cssQueryButtonNext = "li.styles__buttonNext--3YXvj > a[href]";
        List<String> links = new ArrayList<>();

        Document document = Jsoup.connect(url + keyword).get();

        document.select(cssQueryLinks).forEach(element ->
            element.select("div[data-tracking-qa] > a[href]")
                    .eachAttr("abs:href")
                    .forEach(link -> links.add(link))
        );

        links.forEach(System.out::println);

        url = document.select(cssQueryButtonNext).attr("abs:href");

        System.out.println(links.size());

        long deltaTime = System.currentTimeMillis() - countingTime;
        long deltaMemory = deltaOfMemory() - memorySize;

        int theNumberOfTicks = 1000;
        int byteInMb = 1048576;

        System.out.println("Run-time: " + deltaTime / theNumberOfTicks + " sec");
        System.out.println("Memory footprint: " + deltaMemory / byteInMb + " MB");
    }

    private static long deltaOfMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}

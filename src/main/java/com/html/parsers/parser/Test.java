package com.html.parsers.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Test {
    public static void main(String[] args) throws IOException {
        String url = "https://www.aboutyou.de/p/tom-tailor/jacke-fake-leather-price-starter-3803129";
        Document document = Jsoup.connect(url).get();
        List<String> descriptions = new ArrayList<>();
        System.out.println(document.select("li[class=styles__articleNumber--1UszN]").html());

    }
}

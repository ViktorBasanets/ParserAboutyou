package com.html.parsers.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Test {
    public static void main(String[] args) throws IOException {
        String url = "https://www.aboutyou.de/suche?term=" + args[0];
        Document document = Jsoup.connect(url).get();
        String str = document.select("div[class=styles__brandName--2XS22]").first().html();
        System.out.println(str);

    }
}

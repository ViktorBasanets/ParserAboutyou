package com.html.parser;
import com.html.parser.dao.Scribe;
import com.html.parser.dao.XmlScribeImpl;
import com.html.parser.parser.Parser;
import com.html.parser.parser.ParserAboutYouImpl;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ProductParserApp {
    public static void main(String[] args) {
        long countingTime = System.currentTimeMillis();
        long memorySize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        int theNumberOfTicks = 1000;
        int byteInMb = 1048576;

        String url = "https://www.aboutyou.de/suche?term=" + args[0];
        Document document = null;

        Parser parser = new ParserAboutYouImpl();
        Scribe scribe = new XmlScribeImpl();
        try {
            scribe.write("testOfferFiles.xml", scribe.toXml(parser.parse(document, url)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        long deltaTime = System.currentTimeMillis() - countingTime;
        long deltaMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - memorySize;

        System.out.println("Run-time: " + deltaTime / theNumberOfTicks + " sec");
        System.out.println("Memory footprint: " + deltaMemory / byteInMb + " MB");

    }
}

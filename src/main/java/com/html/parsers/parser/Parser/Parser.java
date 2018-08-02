package com.html.parsers.parser.Parser;

import com.html.parsers.parser.Model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Document document;
    private List<String> links;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Parser setLinks() {

        try {
            if (links == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        links = document.select("").eachAttr("");

        return this;
    }

    public Parser setDocument(String url) {
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public List<Product> parse(String keyword)
            throws IOException {

        List<Product> products = new ArrayList<>();

        if (document == null) {
            throw new IOException("Document don't set");
        }



        return products;
    }
}

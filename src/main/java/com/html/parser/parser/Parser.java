package com.html.parser.parser;

import com.html.parser.model.Product;
import org.jsoup.nodes.Document;

import java.util.List;

public interface Parser {

    List<Product> parse(Document document, String url);
}
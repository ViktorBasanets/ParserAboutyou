package com.html.parsers.parser.Parser;

import java.util.List;

public interface Parser {

    String getName();

    String getBrand();

    List<String> getColorsLinks();

    String getPrice();

    String getInitialPrice();

    String getDescription();

    String getArticleId();

    String getShippingCosts();

    String getNextPage();

    List<String> getLinks();

    void setDocument(String url);
}
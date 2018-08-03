package com.html.parsers.parser.Parser;

import com.html.parsers.parser.Model.Product;

import java.util.List;

public interface ParserAboutYou {

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
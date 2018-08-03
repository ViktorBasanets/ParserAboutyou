package com.html.parsers.parser.Dao;

import com.html.parsers.parser.Model.Product;

import java.util.List;

public interface Scribe {
    StringBuilder toXml(List<Product> products);
    void write(String fileName, StringBuilder stringBuilder);
}

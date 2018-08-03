package com.html.parser.dao;

import com.html.parser.model.Product;

import java.io.IOException;
import java.util.List;

public interface Scribe {
    StringBuilder toXml(List<Product> products);
    void write(String fileName, StringBuilder stringBuilder) throws IOException;
}

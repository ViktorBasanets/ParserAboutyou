package com.html.parser.dao;

import com.html.parser.model.Product;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class XmlScribeImpl implements Scribe{
    @Override
    public StringBuilder toXml(List<Product> products) {

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<offers>\n");
        products.forEach(product -> {
            builder.append("<offer>\n")
                    .append("<name>" + product.getName() + "</name>\n")
                    .append("<brand>" + product.getBrand() + "</brand>\n")
                    .append("<colors>\n");
            if (product.getColors() != null) {
                product.getColors().forEach(color -> builder.append("<color>" + color + "</color>\n"));
            }
            builder.append("</colors>\n")
                    .append("<price>" + product.getPrice() + "</price>\n")
                    .append("<initialPrice>" + product.getInitialPrice() + "</initialPrice>\n")
                    .append("<description>" + product.getDescription() + "</description>\n")
                    .append("<articleID>" + product.getArticleId() + "</articleID>\n")
                    .append("<shippingCosts>" + product.getShippingCosts() + "</shippingCosts>\n")
                    .append("</offer>\n");
        });
        builder.append("</offers>\n");
        return builder;
    }

    @Override
    public void write(String fileName, StringBuilder builder) {

        File file = new File(fileName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

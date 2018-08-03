package com.html.parsers.parser.Model;

import java.util.List;

public class Product {

    private String name;
    private String brand;
    private List<String> colors;
    private String price;
    private String initialPrice;
    private String description;
    private String articleId;
    private String shippingCosts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getShippingCosts() {
        return shippingCosts;
    }

    public void setShippingCosts(String shippingCosts) {
        this.shippingCosts = shippingCosts;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", colors=" + colors +
                ", price='" + price + '\'' +
                ", initialPrice='" + initialPrice + '\'' +
                ", description='" + description + '\'' +
                ", articleId='" + articleId + '\'' +
                ", shippingCosts='" + shippingCosts + '\'' +
                '}';
    }
}

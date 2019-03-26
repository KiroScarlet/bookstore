package cn.KiroScarlet.bookstore.book.domain;

import cn.KiroScarlet.bookstore.category.domain.Category;

/**
 * @author KiroScarlet
 * @date 2019-03-26  -13:27
 */
public class Book {
    private String bid;
    private String bname;
    private double price;
    private String author;
    private String image;
    private Category category;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

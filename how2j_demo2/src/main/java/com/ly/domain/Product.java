package com.ly.domain;

import java.util.List;

/**
 * Product实体类
 */
public class Product {
    private int id;
    private String name;
    private float price;
    private int cid;
    private Category categorys;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Category getCategorys() {
        return categorys;
    }

    public void setCategorys(Category categorys) {
        this.categorys = categorys;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", cid=" + cid +
                ", categorys=" + categorys +
                '}';
    }
}

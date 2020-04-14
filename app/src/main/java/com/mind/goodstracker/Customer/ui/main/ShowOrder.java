package com.mind.goodstracker.Customer.ui.main;

import java.io.Serializable;

public class ShowOrder implements Serializable {
    String title,id;
    String productName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}

package br.com.developen.pdv.room;

import androidx.room.Embedded;

public class ProductProductModel {

    @Embedded(prefix = "product_")
    private ProductModel product;

    @Embedded(prefix = "part_")
    private ProductModel part;

    private Boolean active;

    private Double quantity;

    public ProductModel getProduct() {

        return product;

    }

    public void setProduct(ProductModel product) {

        this.product = product;

    }

    public ProductModel getPart() {

        return part;

    }

    public void setPart(ProductModel part) {

        this.part = part;

    }

    public Boolean getActive() {

        return active;

    }

    public void setActive(Boolean active) {

        this.active = active;

    }

    public Double getQuantity() {

        return quantity;

    }

    public void setQuantity(Double quantity) {

        this.quantity = quantity;

    }

}
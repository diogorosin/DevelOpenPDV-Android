package br.com.developen.pdv.room;

import androidx.room.Embedded;


public class MerchandiseModel extends ProductModel {

    @Embedded(prefix = "catalog_")
    private CatalogModel catalog;

    private Integer position;

    private Integer reference;

    private String label;

    private Double price;

    public CatalogModel getCatalog() {

        return catalog;

    }

    public void setCatalog(CatalogModel catalog) {

        this.catalog = catalog;

    }

    public Integer getPosition() {

        return position;

    }

    public void setPosition(Integer position) {

        this.position = position;

    }

    public Integer getReference() {

        return reference;

    }

    public void setReference(Integer reference) {

        this.reference = reference;

    }

    public String getLabel() {

        return label;

    }

    public void setLabel(String label) {

        this.label = label;

    }

    public Double getPrice() {

        return price;

    }

    public void setPrice(Double price) {

        this.price = price;

    }

}
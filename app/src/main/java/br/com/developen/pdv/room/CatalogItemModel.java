package br.com.developen.pdv.room;

public class CatalogItemModel {

    private SaleableModel saleable;

    private Double quantity;

    private Double total;

    public SaleableModel getSaleable() {

        return saleable;

    }

    public void setSaleable(SaleableModel saleable) {

        this.saleable = saleable;

    }

    public Double getQuantity() {

        return quantity;

    }

    public void setQuantity(Double quantity) {

        this.quantity = quantity;

    }

    public Double getTotal() {

        return total;

    }

    public void setTotal(Double total) {

        this.total = total;

    }

    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CatalogItemModel that = (CatalogItemModel) o;

        return saleable.equals(that.saleable);

    }

    public int hashCode() {

        return saleable.hashCode();

    }

}
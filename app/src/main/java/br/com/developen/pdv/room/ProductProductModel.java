package br.com.developen.pdv.room;

import androidx.room.Embedded;

public class ProductProductModel {

    @Embedded(prefix = "parent_")
    private ProductModel parent;

    @Embedded(prefix = "child_")
    private ProductModel child;

    private Boolean active;

    private Double quantity;

    public ProductModel getParent() {

        return parent;

    }

    public void setParent(ProductModel parent) {

        this.parent = parent;

    }

    public ProductModel getChild() {

        return child;

    }

    public void setChild(ProductModel child) {

        this.child = child;

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

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductProductModel that = (ProductProductModel) o;
        if (!parent.equals(that.parent)) return false;
        return child.equals(that.child);

    }

    public int hashCode() {

        int result = parent.hashCode();
        result = 31 * result + child.hashCode();
        return result;

    }

}
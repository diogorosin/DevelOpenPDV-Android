package br.com.developen.pdv.room;

public class CatalogReceiptModel {

    private Integer identifier;

    private ReceiptMethodModel method;

    private Double value;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public ReceiptMethodModel getMethod() {

        return method;

    }

    public void setMethod(ReceiptMethodModel method) {

        this.method = method;

    }

    public Double getValue() {

        return value;

    }

    public void setValue(Double value) {

        this.value = value;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogReceiptModel that = (CatalogReceiptModel) o;
        return identifier.equals(that.identifier);

    }

    public int hashCode() {

        return identifier.hashCode();

    }

}
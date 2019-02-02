package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.annotation.NonNull;

@Entity(tableName = "Merchandise",
        primaryKeys = {"product"},
        foreignKeys = {
                @ForeignKey(entity = ProductVO.class,
                        parentColumns = "progeny",
                        childColumns = "product",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = CatalogVO.class,
                        parentColumns = "identifier",
                        childColumns = "catalog",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)
        }, indices = {
        @Index(value={"product"}),
        @Index(value={"catalog"})})
public class MerchandiseVO {

    @ColumnInfo(name = "product")
    private Integer product;

    @NonNull
    @ColumnInfo(name = "catalog")
    private Integer catalog;

    @NonNull
    @ColumnInfo(name = "position")
    private Integer position;

    @NonNull
    @ColumnInfo(name = "reference")
    private Integer reference;

    @NonNull
    @ColumnInfo(name = "label")
    private String label;

    @NonNull
    @ColumnInfo(name = "price")
    private Double price;

    public Integer getProduct() {

        return product;

    }

    public void setProduct(Integer product) {

        this.product = product;

    }

    public Integer getCatalog() {

        return catalog;

    }

    public void setCatalog(Integer catalog) {

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

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchandiseVO that = (MerchandiseVO) o;
        return product.equals(that.product);

    }

    public int hashCode() {

        return product.hashCode();

    }

}
package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.annotation.NonNull;

@Entity(tableName = "SaleItem",
        primaryKeys = {"sale", "item"},
        foreignKeys = {
                @ForeignKey(entity = SaleVO.class,
                        parentColumns = "identifier",
                        childColumns = "sale",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = ProgenyVO.class,
                        parentColumns = "identifier",
                        childColumns = "progeny",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)})
public class SaleItemVO {

    @NonNull
    @ColumnInfo(name = "sale")
    private Integer sale;

    @NonNull
    @ColumnInfo(name = "item")
    private Integer item;

    @NonNull
    @ColumnInfo(name = "progeny")
    private Integer progeny;

    @NonNull
    @ColumnInfo(name="quantity")
    private Double quantity;

    @NonNull
    @ColumnInfo(name = "measureUnit")
    private Integer measureUnit;

    @NonNull
    @ColumnInfo(name="price")
    private Double price;

    @NonNull
    @ColumnInfo(name="total")
    private Double total;

    public Integer getSale() {

        return sale;

    }

    public void setSale(Integer sale) {

        this.sale = sale;

    }

    public Integer getItem() {

        return item;

    }

    public void setItem(Integer item) {

        this.item = item;

    }

    public Integer getProgeny() {

        return progeny;

    }

    public void setProgeny(Integer progeny) {

        this.progeny = progeny;

    }

    public Double getQuantity() {

        return quantity;

    }

    public void setQuantity(Double quantity) {

        this.quantity = quantity;

    }

    public Integer getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(Integer measureUnit) {

        this.measureUnit = measureUnit;

    }

    public Double getPrice() {

        return price;

    }

    public void setPrice(Double price) {

        this.price = price;

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
        SaleItemVO that = (SaleItemVO) o;
        if (!sale.equals(that.sale)) return false;
        return item.equals(that.item);

    }

    public int hashCode() {

        int result = sale.hashCode();
        result = 31 * result + item.hashCode();
        return result;

    }

}
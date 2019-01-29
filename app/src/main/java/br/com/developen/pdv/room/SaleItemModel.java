package br.com.developen.pdv.room;

import androidx.room.Embedded;

public class SaleItemModel {

    @Embedded(prefix = "sale_")
    private SaleModel sale;

    private Integer item;

    @Embedded(prefix = "saleable_")
    private SaleableModel saleable;

    private Double quantity;

    @Embedded(prefix = "measureUnit_")
    private MeasureUnitModel measureUnit;

    private Double price;

    private Double total;

    public SaleModel getSale() {

        return sale;

    }

    public void setSale(SaleModel sale) {

        this.sale = sale;

    }

    public Integer getItem() {

        return item;

    }

    public void setItem(Integer item) {

        this.item = item;

    }

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

    public MeasureUnitModel getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(MeasureUnitModel measureUnit) {

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
        SaleItemModel that = (SaleItemModel) o;
        if (!sale.equals(that.sale)) return false;
        return item.equals(that.item);

    }

    public int hashCode() {

        int result = sale.hashCode();
        result = 31 * result + item.hashCode();
        return result;

    }

}
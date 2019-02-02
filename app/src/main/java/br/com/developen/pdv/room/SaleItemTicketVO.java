package br.com.developen.pdv.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "SaleItemTicket",
        primaryKeys = {"sale", "item", "ticket"},
        foreignKeys = {
                @ForeignKey(entity = SaleItemVO.class,
                        parentColumns = {"sale", "item"},
                        childColumns = {"sale", "item"},
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)})
public class SaleItemTicketVO {

    @NonNull
    @ColumnInfo(name = "sale")
    private Integer sale;

    @NonNull
    @ColumnInfo(name = "item")
    private Integer item;

    @NonNull
    @ColumnInfo(name = "ticket")
    private Integer ticket;

    @NonNull
    @ColumnInfo(name = "of")
    private Integer of;

    @NonNull
    @ColumnInfo(name = "label")
    private String label;

    @ColumnInfo(name = "quantity")
    private Double quantity;

    @ColumnInfo(name = "measureUnit")
    private Integer measureUnit;

    @NonNull
    @ColumnInfo(name="printed")
    private Boolean printed;

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

    public Integer getTicket() {

        return ticket;

    }

    public void setTicket(Integer ticket) {

        this.ticket = ticket;

    }

    public Integer getOf() {

        return of;

    }

    public void setOf(Integer of) {

        this.of = of;

    }

    public String getLabel() {

        return label;

    }

    public void setLabel(String label) {

        this.label = label;

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

    public Boolean getPrinted() {

        return printed;

    }

    public void setPrinted(Boolean printed) {

        this.printed = printed;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleItemTicketVO that = (SaleItemTicketVO) o;
        if (!sale.equals(that.sale)) return false;
        if (!item.equals(that.item)) return false;
        return ticket.equals(that.ticket);

    }

    public int hashCode() {

        int result = sale.hashCode();
        result = 31 * result + item.hashCode();
        result = 31 * result + ticket.hashCode();
        return result;

    }

}
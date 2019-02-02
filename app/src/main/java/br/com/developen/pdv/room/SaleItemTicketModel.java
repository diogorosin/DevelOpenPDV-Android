package br.com.developen.pdv.room;

import androidx.room.Embedded;

public class SaleItemTicketModel {

    @Embedded(prefix = "saleItem_")
    private SaleItemModel saleItem;

    private Integer ticket;

    private Integer of;

    private String label;

    private Double quantity;

    @Embedded(prefix = "measureUnit_")
    private MeasureUnitModel measureUnit;

    private Boolean printed;

    public SaleItemModel getSaleItem() {

        return saleItem;

    }

    public void setSaleItem(SaleItemModel saleItem) {

        this.saleItem = saleItem;

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

    public MeasureUnitModel getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(MeasureUnitModel measureUnit) {

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
        SaleItemTicketModel that = (SaleItemTicketModel) o;
        if (saleItem != null ? !saleItem.equals(that.saleItem) : that.saleItem != null)
            return false;
        return ticket != null ? ticket.equals(that.ticket) : that.ticket == null;

    }

    public int hashCode() {

        int result = saleItem != null ? saleItem.hashCode() : 0;
        result = 31 * result + (ticket != null ? ticket.hashCode() : 0);
        return result;

    }

}
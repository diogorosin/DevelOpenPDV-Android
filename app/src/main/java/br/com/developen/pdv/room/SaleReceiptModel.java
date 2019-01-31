package br.com.developen.pdv.room;

import androidx.room.Embedded;

public class SaleReceiptModel {

    @Embedded(prefix = "sale_")
    private SaleModel sale;

    private Integer receipt;

    @Embedded(prefix = "receiptModel")
    private ReceiptMethodModel receiptMethod;

    private Double value;

    public SaleModel getSale() {

        return sale;

    }

    public void setSale(SaleModel sale) {

        this.sale = sale;

    }

    public Integer getReceipt() {

        return receipt;

    }

    public void setReceipt(Integer receipt) {

        this.receipt = receipt;

    }

    public ReceiptMethodModel getReceiptMethod() {

        return receiptMethod;

    }

    public void setReceiptMethod(ReceiptMethodModel receiptMethod) {

        this.receiptMethod = receiptMethod;

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
        SaleReceiptModel that = (SaleReceiptModel) o;
        if (!sale.equals(that.sale)) return false;
        return receipt.equals(that.receipt);

    }

    public int hashCode() {

        int result = sale.hashCode();
        result = 31 * result + receipt.hashCode();
        return result;

    }

}
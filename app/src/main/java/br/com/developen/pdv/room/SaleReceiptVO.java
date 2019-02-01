package br.com.developen.pdv.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "SaleReceipt",
        primaryKeys = {"sale", "receipt"},
        foreignKeys = {
                @ForeignKey(entity = SaleVO.class,
                        parentColumns = "identifier",
                        childColumns = "sale",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = ReceiptMethodVO.class,
                        parentColumns = "identifier",
                        childColumns = "receiptMethod",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)})
public class SaleReceiptVO {

    @NonNull
    @ColumnInfo(name = "sale")
    private Integer sale;

    @NonNull
    @ColumnInfo(name = "receipt")
    private Integer receipt;

    @NonNull
    @ColumnInfo(name = "receiptMethod")
    private String receiptMethod;

    @NonNull
    @ColumnInfo(name="value")
    private Double value;

    public Integer getSale() {

        return sale;

    }

    public void setSale(Integer sale) {

        this.sale = sale;

    }

    public Integer getReceipt() {

        return receipt;

    }

    public void setReceipt(Integer receipt) {

        this.receipt = receipt;

    }

    public String getReceiptMethod() {

        return receiptMethod;

    }

    public void setReceiptMethod(String receiptMethod) {

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
        SaleReceiptVO that = (SaleReceiptVO) o;
        if (!sale.equals(that.sale)) return false;
        return receipt.equals(that.receipt);

    }

    public int hashCode() {

        int result = sale.hashCode();
        result = 31 * result + receipt.hashCode();
        return result;

    }

}
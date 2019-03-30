package br.com.developen.pdv.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;


@Entity(tableName = "SaleReceiptCash",
        primaryKeys = {"sale", "receipt", "cash"},
        foreignKeys = {
                @ForeignKey(entity = SaleReceiptVO.class,
                        parentColumns = {"sale", "receipt"},
                        childColumns = {"sale", "receipt"},
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = CashVO.class,
                        parentColumns = "identifier",
                        childColumns = "cash",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index(value={"sale", "receipt", "cash"})})
public class SaleReceiptCashVO {

    @NonNull
    @ColumnInfo(name = "sale")
    private Integer sale;

    @NonNull
    @ColumnInfo(name = "receipt")
    private Integer receipt;

    @NonNull
    @ColumnInfo(name = "cash")
    private Integer cash;

    @ColumnInfo(name = "reversal")
    private Integer reversal;

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

    public Integer getCash() {

        return cash;

    }

    public void setCash(Integer cash) {

        this.cash = cash;

    }

    public Integer getReversal() {

        return reversal;

    }

    public void setReversal(Integer reversal) {

        this.reversal = reversal;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleReceiptCashVO that = (SaleReceiptCashVO) o;
        if (!sale.equals(that.sale)) return false;
        if (!receipt.equals(that.receipt)) return false;
        return cash.equals(that.cash);

    }

    public int hashCode() {

        int result = sale.hashCode();
        result = 31 * result + receipt.hashCode();
        result = 31 * result + cash.hashCode();
        return result;

    }

}
package br.com.developen.pdv.room;

import androidx.room.Embedded;


public class SaleReceiptCashModel {

    @Embedded(prefix = "saleReceipt_")
    private SaleReceiptModel saleReceipt;

    @Embedded(prefix = "cash_")
    private CashModel cash;

    @Embedded(prefix = "reversal_")
    private CashModel reversal;

    public SaleReceiptModel getSaleReceipt() {

        return saleReceipt;

    }

    public void setSaleReceipt(SaleReceiptModel saleReceipt) {

        this.saleReceipt = saleReceipt;

    }

    public CashModel getCash() {

        return cash;

    }

    public void setCash(CashModel cash) {

        this.cash = cash;

    }

    public CashModel getReversal() {

        return reversal;

    }

    public void setReversal(CashModel reversal) {

        this.reversal = reversal;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleReceiptCashModel that = (SaleReceiptCashModel) o;
        if (!saleReceipt.equals(that.saleReceipt)) return false;
        return cash.equals(that.cash);

    }

    public int hashCode() {

        int result = saleReceipt.hashCode();
        result = 31 * result + cash.hashCode();
        return result;

    }

}
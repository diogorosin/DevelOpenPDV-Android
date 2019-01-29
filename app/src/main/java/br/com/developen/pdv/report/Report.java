package br.com.developen.pdv.report;

import android.content.Context;

import java.util.Date;

import br.com.developen.pdv.report.adapter.OnPrintListener;
import br.com.developen.pdv.room.CashModel;

public interface Report {


    /*void printCouponsOfLastGeneratedSale(
            Context context,
            OnPrintListener listener,
            String title,
            String subtitle,
            String deviceAlias,
            String userName,
            String note,
            String footer);*/

    void printOpenCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

    void printSupplyCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

    void printRemoveCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

    void printCloseCashCoupon(
            OnPrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

}
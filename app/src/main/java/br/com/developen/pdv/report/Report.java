package br.com.developen.pdv.report;

import java.util.Date;

import br.com.developen.pdv.report.adapter.PrintListener;
import br.com.developen.pdv.room.CashModel;

public interface Report {


    /*void printTicketsOfSale(
            PrintListener listener,
            String title,
            String subtitle,
            String deviceAlias,
            String userName,
            String note,
            String footer);*/

    void printOpenCashCoupon(
            PrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

    void printSupplyCashCoupon(
            PrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

    void printRemoveCashCoupon(
            PrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

    void printCloseCashCoupon(
            PrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels);

}
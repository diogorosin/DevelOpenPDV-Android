package br.com.developen.pdv.report;

import java.util.Date;
import java.util.Map;

import br.com.developen.pdv.report.adapter.PrintListener;
import br.com.developen.pdv.room.CashModel;

public interface Report {

    int PERIOD_START_PARAM = 0;

    int PERIOD_END_PARAM = 1;

    void printTicketsOfSale(
            PrintListener listener,
            Integer sale,
            String title,
            String subtitle,
            String deviceAlias,
            String userName,
            String note,
            String footer);

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

    void printSalesByProgeny(
            PrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            Map... parameters);

}
package br.com.developen.pdv.report;

import java.util.Date;
import java.util.Map;

import br.com.developen.pdv.report.adapter.PrintListener;
import br.com.developen.pdv.report.task.PT7003CloseCashAsyncTask;
import br.com.developen.pdv.report.task.PT7003OpenCashAsyncTask;
import br.com.developen.pdv.report.task.PT7003PrintSalesByProgenyAsyncTask;
import br.com.developen.pdv.report.task.PT7003PrintTicketsOfSaleAsyncTask;
import br.com.developen.pdv.report.task.PT7003RemoveCashAsyncTask;
import br.com.developen.pdv.report.task.PT7003SupplyCashAsyncTask;
import br.com.developen.pdv.room.CashModel;

public class PT7003Report implements Report {

    /* CUPOM DO ITEM DA VENDA */
    public void printTicketsOfSale(PrintListener listener,
                                   Integer sale,
                                   String title,
                                   String subtitle,
                                   String deviceAlias,
                                   String userName,
                                   String note,
                                   String footer) {

        new PT7003PrintTicketsOfSaleAsyncTask<>(
                listener,
                title,
                subtitle,
                deviceAlias,
                userName,
                note,
                footer).execute(sale);

    }

    /* CUPOM DE ABERTURA DO CAIXA */
    public void printOpenCashCoupon(
            PrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels) {

        new PT7003OpenCashAsyncTask<>(
                listener,
                title,
                subtitle,
                dateTime,
                deviceAlias).execute(cashModels);

    }

    /* CUPOM DE COMPLEMENTO DO CAIXA */
    public void printSupplyCashCoupon(
            PrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels) {

        new PT7003SupplyCashAsyncTask<>(
                listener,
                title,
                subtitle,
                dateTime,
                deviceAlias).execute(cashModels);

    }

    /* CUPOM DE SANGRIA DO CAIXA */
    public void printRemoveCashCoupon(
            PrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels) {

        new PT7003RemoveCashAsyncTask<>(
                listener,
                title,
                subtitle,
                dateTime,
                deviceAlias).execute(cashModels);

    }

    /* CUPOM DE FECHAMENTO DO CAIXA */
    public void printCloseCashCoupon(
            PrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            CashModel... cashModels) {

        new PT7003CloseCashAsyncTask<>(
                listener,
                title,
                subtitle,
                dateTime,
                deviceAlias).execute(cashModels);

    }

    /* VENDAS POR PRODUTO/SERVICO */
    public void printSalesByProgeny(
            PrintListener listener,
            String title,
            String subtitle,
            Date dateTime,
            String deviceAlias,
            Map... parameters) {

        new PT7003PrintSalesByProgenyAsyncTask<>(
                listener,
                title,
                subtitle,
                dateTime,
                deviceAlias).execute(parameters);

    }

}
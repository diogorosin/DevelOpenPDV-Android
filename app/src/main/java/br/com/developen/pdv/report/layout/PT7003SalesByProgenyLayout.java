package br.com.developen.pdv.report.layout;

import android.pt.printer.Printer;

import br.com.developen.pdv.room.SaleDAO;
import br.com.developen.pdv.utils.StringUtils;

public class PT7003SalesByProgenyLayout extends SalesByProgenyLayout {


    private final Printer printer;


    public PT7003SalesByProgenyLayout(Printer printer){

        this.printer = printer;

    }


    public void print(){

        printer.setAlignment(1);

        printer.setBold(true);

        printer.printString(getReportName());

        printer.printString( "De.: " + StringUtils.formatDate(getPeriodStart()));

        printer.printString( "Até: " + StringUtils.formatDate(getPeriodEnd()));

        printer.setBold(false);

        printer.setAlignment(0);

        printer.printString(" ");

        printer.printString("DESCRICAO-----------------------");

        printer.printString("QUANTIDADE PREÇO_ME TOTAL-------");

        Double total = 0.0;

        for (SaleDAO.SalesByProgenyBean salesByProgenyBean: getRows()) {

            printer.printString(StringUtils.rightPad(salesByProgenyBean.getLabel(), 32, ' '));

            printer.printString(//StringUtils.leftPad(salesByProgenyBean.getReference(), 3, ' ') + " " +

                    StringUtils.leftPad(StringUtils.formatQuantityWithMinimumFractionDigit(salesByProgenyBean.getQuantity()), 10, ' ') + ' ' +

                    StringUtils.leftPad(StringUtils.formatCurrency(salesByProgenyBean.getTotal() / salesByProgenyBean.getQuantity()), 8, ' ') + ' ' +

                    StringUtils.leftPad(StringUtils.formatCurrency(salesByProgenyBean.getTotal()), 12, ' '));

            total += salesByProgenyBean.getTotal();

        }

        printer.printString("--------------------------------");

        printer.printString(StringUtils.leftPad(StringUtils.formatCurrency(total), 32, ' '));

        printer.printString(" ");

        printer.printString(" ");

        printer.printString(" ");

    }


}
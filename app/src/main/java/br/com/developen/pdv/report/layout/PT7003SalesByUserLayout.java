package br.com.developen.pdv.report.layout;

import android.pt.printer.Printer;

import br.com.developen.pdv.room.SaleDAO;
import br.com.developen.pdv.utils.StringUtils;

public class PT7003SalesByUserLayout extends SalesByUserLayout {


    private final Printer printer;


    public PT7003SalesByUserLayout(Printer printer){

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

        printer.printString("USUARIO-------------------------");

        printer.printString("PERCENTUAL------ TOTAL----------");

        Double total = 0.0;

        for (SaleDAO.SalesByUserBean salesByUserBean: getRows()) {

            printer.printString(StringUtils.rightPad(salesByUserBean.getName().toUpperCase(), 32, ' '));

            printer.printString(

                    StringUtils.leftPad(StringUtils.formatPercentageWithMinimumFractionDigit(salesByUserBean.getPercentage()) + '%', 16, ' ') + ' ' +

                            StringUtils.leftPad(StringUtils.formatCurrency(salesByUserBean.getTotal()), 15, ' '));

            total += salesByUserBean.getTotal();

        }

        printer.printString("--------------------------------");

        printer.printString(StringUtils.leftPad(StringUtils.formatCurrency(total), 32, ' '));

        printer.printString(" ");

        printer.printString(" ");

        printer.printString(" ");

    }


}
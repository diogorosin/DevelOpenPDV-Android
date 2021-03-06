package br.com.developen.pdv.report.layout;

import android.pt.printer.Printer;

import br.com.developen.pdv.utils.StringUtils;

public class PT7003OpenCashLayout extends OpenCashLayout {


    private final Printer printer;


    public PT7003OpenCashLayout(Printer printer){

        this.printer = printer;

    }


    public void print(){

        printer.setAlignment(1);

        printer.setBold(true);

        printer.printString(getReportName());

        printer.setBold(false);

        printer.setAlignment(0);

        printer.printString(" ");

        printer.printString("DATA/HORA---------- VALOR-------");

        printer.printString("USUARIO-------------------------");

        printer.printString(
                StringUtils.formatDateTime(getDateTime()) + " " +
                        StringUtils.leftPad(StringUtils.formatCurrency(getValue()), 12, ' '));

        printer.printString(getUserName().toUpperCase());

        printer.printString(" ");

        printer.printString(" ");

        printer.printString(" ");

        printer.printString("--------------------------------");

        printer.printString("VISTO DO(A) OPERADOR(A) DO CAIXA");

        printer.printString(" ");

        printer.printString(" ");

        printer.printString(" ");

        printer.printString(" ");

        printer.printString(" ");


    }


}
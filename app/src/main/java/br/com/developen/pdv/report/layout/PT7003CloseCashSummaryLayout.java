package br.com.developen.pdv.report.layout;

import android.pt.printer.Printer;

import br.com.developen.pdv.utils.StringUtils;

public class PT7003CloseCashSummaryLayout extends CloseCashSummaryLayout {


    private final Printer printer;


    public PT7003CloseCashSummaryLayout(Printer printer){

        this.printer = printer;

    }


    public void print(){

        printer.printString("  " +
                StringUtils.formatShortDateTime(getDateTime()) + " " +
                        StringUtils.leftPad(StringUtils.formatCurrency(  getType().equals("S") ? getValue() * -1 : getValue()), 18, ' '));

        printer.printString("  " +
                StringUtils.rightPad(getUserName(), 30, ' '));

    }


}
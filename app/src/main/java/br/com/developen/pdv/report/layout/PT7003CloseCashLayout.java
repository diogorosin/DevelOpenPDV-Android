package br.com.developen.pdv.report.layout;

import android.pt.printer.Printer;

import br.com.developen.pdv.utils.StringUtils;

public class PT7003CloseCashLayout extends CloseCashLayout {


    private final Printer printer;


    public PT7003CloseCashLayout(Printer printer){

        this.printer = printer;

    }


    public void print(){

        printer.setAlignment(1);

        printer.setBold(true);

        printer.printString(getReportName());

        printer.setBold(false);

        printer.setAlignment(0);

        printer.printString(" ");

        printer.printString("OPERACAO------------------------");

        printer.printString("  DATA/HORA-- VALOR-------------");

        printer.printString("  USUARIO-----------------------");

        String pageBreak = getSummary().get(0).getOperation();

        printer.printString(StringUtils.getDenominationOfCashOperation(pageBreak));

        for(CloseCashSummaryLayout row : getSummary()){

            if (!row.getOperation().equals(pageBreak)){

                pageBreak = row.getOperation();

                printer.printString(StringUtils.getDenominationOfCashOperation(pageBreak));

            }

            try {

                row.print();

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        printer.printString("--------------------------------");

        printer.printString("              DIN           0,00");

        printer.printString(" ");

        printer.printString(" ");

        printer.printString(" ");

        printer.printString(" ");

    }

}
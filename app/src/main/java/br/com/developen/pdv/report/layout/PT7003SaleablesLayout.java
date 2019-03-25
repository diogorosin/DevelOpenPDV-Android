package br.com.developen.pdv.report.layout;

import android.pt.printer.Printer;

import br.com.developen.pdv.room.SaleableModel;
import br.com.developen.pdv.utils.StringUtils;

public class PT7003SaleablesLayout extends SaleablesLayout {


    private final Printer printer;


    public PT7003SaleablesLayout(Printer printer){

        this.printer = printer;

    }


    public void print(){

        printer.setAlignment(1);

        printer.setBold(true);

        printer.printString(getReportName());

        printer.setBold(false);

        printer.setAlignment(0);

        printer.printString(" ");

        printer.printString("DESCRICAO-----------------------");

        printer.printString("REFERENCIA UNIDADE--- PRECO-----");

        for (SaleableModel saleableModel: getRows()) {

            printer.printString(StringUtils.rightPad(saleableModel.getLabel().toUpperCase(), 32, ' '));

            printer.printString(

                    StringUtils.rightPad(saleableModel.getReference().toString(), 10, ' ') + ' ' +

                    StringUtils.rightPad(saleableModel.getMeasureUnit().getAcronym().toUpperCase(), 10, ' ') + ' ' +

                    StringUtils.leftPad(StringUtils.formatCurrency(saleableModel.getPrice()), 10, ' '));

        }

        printer.printString(" ");

        printer.printString(" ");

        printer.printString(" ");

    }


}
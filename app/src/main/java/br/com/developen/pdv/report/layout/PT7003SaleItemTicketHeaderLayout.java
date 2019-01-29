package br.com.developen.pdv.report.layout;

import br.com.developen.pdv.report.exception.FailPrinterException;
import br.com.developen.pdv.report.exception.InParametersErrorPrinterException;
import br.com.developen.pdv.report.exception.NoPaperPrinterException;
import br.com.developen.pdv.report.exception.TimeoutPrinterException;
import br.com.developen.pdv.report.util.PT7003Printer;

public class PT7003SaleItemTicketHeaderLayout extends SaleItemTicketHeaderLayout {

    private final PT7003Printer printer;

    public PT7003SaleItemTicketHeaderLayout(PT7003Printer printer){

        this.printer = printer;

    }

    public void print() throws
            TimeoutPrinterException,
            InParametersErrorPrinterException,
            FailPrinterException,
            NoPaperPrinterException {

        printer.setAlignment(1);

        printer.setBold(true);

        printer.setFontHeightZoomIn(2);

        printer.printString(getTitle());

        if (getSubtitle() != null && !getSubtitle().isEmpty())

            printer.printString(getSubtitle());

        printer.setFontHeightZoomIn(1);

        printer.setBold(false);

    }

}
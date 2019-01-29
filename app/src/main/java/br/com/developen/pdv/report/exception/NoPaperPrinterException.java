package br.com.developen.pdv.report.exception;

import br.com.developen.pdv.utils.Messaging;

public class NoPaperPrinterException extends Exception implements Messaging {

    public NoPaperPrinterException(){

        super("<b>A impressora está sem papel</b>. Descarte o último cupom impresso pela metade, subistitua a bobina e tente novamente.");

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}
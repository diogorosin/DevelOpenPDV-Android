package br.com.developen.pdv.report.exception;

import br.com.developen.pdv.utils.Messaging;

public class TimeoutPrinterException extends Exception implements Messaging {

    public TimeoutPrinterException(){

        super("O tempo de conexão com a impressora foi esgotado.");

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}
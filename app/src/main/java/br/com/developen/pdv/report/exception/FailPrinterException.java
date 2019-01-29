package br.com.developen.pdv.report.exception;

import br.com.developen.pdv.utils.Messaging;

public class FailPrinterException extends Exception implements Messaging {

    public FailPrinterException(String command){

        super("Falha na impressora. Comando: " + command);

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}
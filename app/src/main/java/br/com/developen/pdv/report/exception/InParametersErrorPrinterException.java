package br.com.developen.pdv.report.exception;

import br.com.developen.pdv.utils.Messaging;

public class InParametersErrorPrinterException extends Exception implements Messaging {

    public InParametersErrorPrinterException(String command){

        super("Erro no(s) parâmetro(s) de entrada. Comando: " + command);

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}
package br.com.developen.pdv.exception;


import br.com.developen.pdv.utils.Messaging;

public class InternalException extends Exception implements Messaging {

    public InternalException(){

        super("Erro interno de processamento.");

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}
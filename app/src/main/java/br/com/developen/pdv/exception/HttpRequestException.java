package br.com.developen.pdv.exception;


import br.com.developen.pdv.utils.Messaging;

public class HttpRequestException extends Exception implements Messaging {

    public HttpRequestException(){

        super("Sem conexão com a internet ou servidor fora de operação.");

    }

    public String[] getMessages(){

        return new String[]{getMessage()};

    }

}
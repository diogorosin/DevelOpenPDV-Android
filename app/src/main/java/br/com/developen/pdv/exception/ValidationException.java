package br.com.developen.pdv.exception;


import br.com.developen.pdv.utils.Messaging;

public class ValidationException extends Exception implements Messaging {

    public ValidationException(String message){

        super(message);

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}
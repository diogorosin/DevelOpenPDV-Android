package br.com.developen.pdv.exception;


import br.com.developen.pdv.utils.Messaging;

public class CannotInitializeDatabaseException extends Exception implements Messaging {

    public CannotInitializeDatabaseException(){

        super("Não foi possível iniciar o banco de dados.");

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}
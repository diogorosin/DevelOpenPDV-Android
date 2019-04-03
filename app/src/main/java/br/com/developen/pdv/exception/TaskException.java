package br.com.developen.pdv.exception;


import br.com.developen.pdv.utils.Messaging;

public class TaskException extends Exception implements Messaging {

    private String[] messages;

    public TaskException(String[] messages){

        this.messages = messages;

    }

    public String[] getMessages(){

        return this.messages;

    }

}
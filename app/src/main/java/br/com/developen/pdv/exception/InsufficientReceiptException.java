package br.com.developen.pdv.exception;


import br.com.developen.pdv.utils.Messaging;

public class InsufficientReceiptException extends Exception implements Messaging {

    public InsufficientReceiptException(){

        super("Valor recebido é menor que o valor da venda.");

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}
package br.com.developen.pdv.jersey;

import java.io.Serializable;


public class TokenBean implements Serializable {

    private String token;

    public String getToken() {

        return token;

    }

    public void setToken(String token) {

        this.token = token;

    }

}
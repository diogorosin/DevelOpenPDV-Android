package br.com.developen.pdv.jersey;

import java.io.Serializable;


public class TokenBean implements Serializable {


    private String identifier;

    private UserBean user;

    private CompanyBean company;


    public String getIdentifier() {

        return identifier;

    }

    public void setIdentifier(String identifier) {

        this.identifier = identifier;

    }

    public UserBean getUser() {

        return user;

    }

    public void setUser(UserBean user) {

        this.user = user;

    }

    public CompanyBean getCompany() {

        return company;

    }

    public void setCompany(CompanyBean company) {

        this.company = company;

    }

}
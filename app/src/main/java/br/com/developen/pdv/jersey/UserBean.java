package br.com.developen.pdv.jersey;

public class UserBean extends IndividualBean {

    private String login;

    private String password;

    private String numericPassword;

    public String getLogin() {

        return login;

    }

    public void setLogin(String login) {

        this.login = login;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {

        this.password = password;

    }

    public String getNumericPassword() {

        return numericPassword;

    }

    public void setNumericPassword(String numericPassword) {

        this.numericPassword = numericPassword;

    }

}
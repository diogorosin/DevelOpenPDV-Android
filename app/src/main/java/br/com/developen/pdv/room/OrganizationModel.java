package br.com.developen.pdv.room;

public class OrganizationModel extends SubjectModel {

    private String denomination;

    private String fancyName;

    public String getDenomination() {

        return denomination;

    }

    public void setDenomination(String denomination) {

        this.denomination = denomination;

    }

    public String getFancyName() {

        return fancyName;

    }

    public void setFancyName(String fancyName) {

        this.fancyName = fancyName;

    }

}
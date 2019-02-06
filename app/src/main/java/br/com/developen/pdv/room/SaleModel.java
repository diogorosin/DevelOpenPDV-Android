package br.com.developen.pdv.room;

import java.util.Date;

import androidx.room.Embedded;
import androidx.room.TypeConverters;

public class SaleModel {

    private Integer identifier;

    private Integer number;

    private String status;

    @TypeConverters({TimestampConverter.class})
    private Date dateTime;

    @Embedded(prefix = "user_")
    private UserModel user;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public Integer getNumber() {

        return number;

    }

    public void setNumber(Integer number) {

        this.number = number;

    }

    public String getStatus() {

        return status;

    }

    public void setStatus(String status) {

        this.status = status;

    }

    public Date getDateTime() {

        return dateTime;

    }

    public void setDateTime(Date dateTime) {

        this.dateTime = dateTime;

    }

    public UserModel getUser() {

        return user;

    }

    public void setUser(UserModel user) {

        this.user = user;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleModel saleModel = (SaleModel) o;
        return identifier.equals(saleModel.identifier);

    }

    public int hashCode() {

        return identifier.hashCode();

    }

}
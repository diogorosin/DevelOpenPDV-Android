package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.TypeConverters;

import java.util.Date;

public class CashModel {

    private Integer identifier;

    private String operation;

    private String type;

    private Double value;

    private String note;

    @Embedded(prefix = "user_")
    private UserModel user;

    @ColumnInfo(name="dateTime")
    @TypeConverters({TimestampConverter.class})
    private Date dateTime;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public String getOperation() {

        return operation;

    }

    public void setOperation(String operation) {

        this.operation = operation;

    }

    public String getType() {

        return type;

    }

    public void setType(String type) {

        this.type = type;

    }

    public String getNote() {

        return note;

    }

    public void setNote(String note) {

        this.note = note;

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

    public Double getValue() {

        return value;

    }

    public void setValue(Double value) {

        this.value = value;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashModel cashModel = (CashModel) o;
        return identifier.equals(cashModel.identifier);

    }

    public int hashCode() {

        return identifier.hashCode();

    }

}
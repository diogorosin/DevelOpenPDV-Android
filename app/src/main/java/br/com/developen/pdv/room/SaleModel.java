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

    private Double total;

    private String note;

    private Boolean uploaded;


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

    public Double getTotal() {

        return total;

    }

    public void setTotal(Double total) {

        this.total = total;

    }

    public String getNote() {

        return note;

    }

    public void setNote(String note) {

        this.note = note;

    }

    public Boolean getUploaded() {

        return uploaded;

    }

    public void setUploaded(Boolean uploaded) {

        this.uploaded = uploaded;

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

    public boolean hasSameContents(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleModel saleModel = (SaleModel) o;
        if (identifier != null ? !identifier.equals(saleModel.identifier) : saleModel.identifier != null)
            return false;
        if (number != null ? !number.equals(saleModel.number) : saleModel.number != null)
            return false;
        if (status != null ? !status.equals(saleModel.status) : saleModel.status != null)
            return false;
        if (dateTime != null ? !dateTime.equals(saleModel.dateTime) : saleModel.dateTime != null)
            return false;
        if (user != null ? !user.equals(saleModel.user) : saleModel.user != null) return false;
        if (total != null ? !total.equals(saleModel.total) : saleModel.total != null) return false;
        if (note != null ? !note.equals(saleModel.note) : saleModel.note != null) return false;
        return uploaded != null ? uploaded.equals(saleModel.uploaded) : saleModel.uploaded == null;
    }

}
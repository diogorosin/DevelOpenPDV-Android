// REFERENCIA DE SQL COM DATAS
// http://androidkt.com/datetime-datatype-sqlite-using-room/

package br.com.developen.pdv.room;

import androidx.room.Embedded;
import androidx.room.TypeConverters;

import java.util.Date;

public class SaleModel {

    private Integer identifier;

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
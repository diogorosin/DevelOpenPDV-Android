/*
    IDENTIFIER: IDENTIFICADOR DA VENDA
    NUMBER....: NUMERO DA VENDA
    STATUS....: STATUS DA VENDA (A=ABERTA, F=FINALIZADA, C=CANCELADA)
    DATETIME..: DATA/HORA
    USER......: USUARIO
    NOTE......: OBSERVACOES
*/
package br.com.developen.pdv.room;

import java.io.Serializable;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "Sale",
        foreignKeys = {
                @ForeignKey(entity = UserVO.class,
                        parentColumns = "individual",
                        childColumns = "user",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index("identifier")})
public class SaleVO implements Serializable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @ColumnInfo(name="number")
    private Integer number;

    @NonNull
    @ColumnInfo(name="status")
    private String status;

    @NonNull
    @ColumnInfo(name="dateTime")
    @TypeConverters({TimestampConverter.class})
    private Date dateTime;

    @NonNull
    @ColumnInfo(name="user")
    private Integer user;

    @ColumnInfo(name="note")
    private String note;


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

    public Integer getUser() {

        return user;

    }

    public void setUser(Integer user) {

        this.user = user;

    }

    public String getNote() {

        return note;

    }

    public void setNote(String note) {

        this.note = note;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleVO saleVO = (SaleVO) o;
        return identifier.equals(saleVO.identifier);

    }

    public int hashCode() {

        return identifier.hashCode();

    }

}
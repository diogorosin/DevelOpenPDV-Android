// REFERENCIA DE SQL COM DATAS
// http://androidkt.com/datetime-datatype-sqlite-using-room/

package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

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

    @NonNull
    @ColumnInfo(name="dateTime")
    @TypeConverters({TimestampConverter.class})
    private Date dateTime;

    @NonNull
    @ColumnInfo(name="user")
    private Integer user;

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

    public Integer getUser() {

        return user;

    }

    public void setUser(Integer user) {

        this.user = user;

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
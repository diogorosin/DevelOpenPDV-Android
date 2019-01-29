package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "Progeny")
public class ProgenyVO {

    @PrimaryKey
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @NonNull
    @ColumnInfo(name="active")
    private Boolean active;

    @NonNull
    @ColumnInfo(name="denomination")
    private String denomination;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public Boolean getActive() {

        return active;

    }

    public void setActive(Boolean active) {

        this.active = active;

    }

    public String getDenomination() {

        return denomination;

    }

    public void setDenomination(String denomination) {

        this.denomination = denomination;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgenyVO progenyVO = (ProgenyVO) o;
        return identifier.equals(progenyVO.identifier);

    }

    public int hashCode() {

        return identifier.hashCode();

    }

}
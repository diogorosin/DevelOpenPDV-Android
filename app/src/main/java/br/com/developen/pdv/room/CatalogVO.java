package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "Catalog")
public class CatalogVO {

    @PrimaryKey
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @NonNull
    @ColumnInfo(name = "active")
    private Boolean active;

    @NonNull
    @ColumnInfo(name = "position")
    private Integer position;

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

    public Integer getPosition() {

        return position;

    }

    public void setPosition(Integer position) {

        this.position = position;

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
        CatalogVO catalogVO = (CatalogVO) o;
        return identifier.equals(catalogVO.identifier);

    }

    public int hashCode() {

        return identifier.hashCode();

    }

}
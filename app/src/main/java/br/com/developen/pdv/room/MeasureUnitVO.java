package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "MeasureUnit")
public class MeasureUnitVO {

    @PrimaryKey
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @NonNull
    @ColumnInfo(name="denomination")
    private String denomination;

    @NonNull
    @ColumnInfo(name="acronym")
    private String acronym;

    @NonNull
    @ColumnInfo(name="group")
    private Integer group;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public String getDenomination() {

        return denomination;

    }

    public void setDenomination(String denomination) {

        this.denomination = denomination;

    }

    public String getAcronym() {

        return acronym;

    }

    public void setAcronym(String acronym) {

        this.acronym = acronym;

    }

    public Integer getGroup() {

        return group;

    }

    public void setGroup(Integer group) {

        this.group = group;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasureUnitVO that = (MeasureUnitVO) o;
        return identifier.equals(that.identifier);

    }

    public int hashCode() {

        return identifier.hashCode();

    }

}
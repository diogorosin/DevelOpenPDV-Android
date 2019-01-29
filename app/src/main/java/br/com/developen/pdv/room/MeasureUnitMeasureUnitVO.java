package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.annotation.NonNull;


@Entity(tableName = "MeasureUnitMeasureUnit",
        primaryKeys = {"fromIdentifier", "toIdentifier"},
        foreignKeys = {
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "fromIdentifier",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "toIdentifier",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index(value={"fromIdentifier"}), @Index(value={"toIdentifier"})})
public class MeasureUnitMeasureUnitVO {

    @NonNull
    @ColumnInfo(name = "fromIdentifier")
    private Integer fromIdentifier;

    @NonNull
    @ColumnInfo(name = "toIdentifier")
    private Integer toIdentifier;

    @ColumnInfo(name="factor")
    private Double factor;

    public Integer getFromIdentifier() {

        return fromIdentifier;

    }

    public void setFromIdentifier(Integer fromIdentifier) {

        this.fromIdentifier = fromIdentifier;

    }

    public Integer getToIdentifier() {

        return toIdentifier;

    }

    public void setToIdentifier(Integer toIdentifier) {

        this.toIdentifier = toIdentifier;

    }

    public Double getFactor() {

        return factor;

    }

    public void setFactor(Double factor) {

        this.factor = factor;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasureUnitMeasureUnitVO that = (MeasureUnitMeasureUnitVO) o;
        if (!fromIdentifier.equals(that.fromIdentifier)) return false;
        return toIdentifier.equals(that.toIdentifier);

    }

    public int hashCode() {

        int result = fromIdentifier.hashCode();
        result = 31 * result + toIdentifier.hashCode();
        return result;

    }

}
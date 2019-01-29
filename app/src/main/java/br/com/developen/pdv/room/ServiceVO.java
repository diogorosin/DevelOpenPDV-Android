package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.annotation.NonNull;

@Entity(tableName = "Service",
        primaryKeys = {"progeny"},
        foreignKeys = {
                @ForeignKey(entity = ProgenyVO.class,
                        parentColumns = "identifier",
                        childColumns = "progeny",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = CatalogVO.class,
                        parentColumns = "identifier",
                        childColumns = "catalog",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "measureUnit",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)
        }, indices = {
        @Index(value={"progeny"}),
        @Index(value={"catalog"}),
        @Index(value={"measureUnit"})})
public class ServiceVO {

    @ColumnInfo(name = "progeny")
    private Integer progeny;

    @NonNull
    @ColumnInfo(name = "catalog")
    private Integer catalog;

    @NonNull
    @ColumnInfo(name = "position")
    private Integer position;

    @NonNull
    @ColumnInfo(name = "reference")
    private Integer reference;

    @NonNull
    @ColumnInfo(name = "label")
    private String label;

    @NonNull
    @ColumnInfo(name = "measureUnit")
    private Integer measureUnit;

    @NonNull
    @ColumnInfo(name = "price")
    private Double price;

    public Integer getProgeny() {

        return progeny;

    }

    public void setProgeny(Integer progeny) {

        this.progeny = progeny;

    }

    public Integer getCatalog() {

        return catalog;

    }

    public void setCatalog(Integer catalog) {

        this.catalog = catalog;

    }

    public Integer getPosition() {

        return position;

    }

    public void setPosition(Integer position) {

        this.position = position;

    }

    public Integer getReference() {

        return reference;

    }

    public void setReference(Integer reference) {

        this.reference = reference;

    }

    public String getLabel() {

        return label;

    }

    public void setLabel(String label) {

        this.label = label;

    }

    public Integer getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(Integer measureUnit) {

        this.measureUnit = measureUnit;

    }

    public Double getPrice() {

        return price;

    }

    public void setPrice(Double price) {

        this.price = price;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceVO that = (ServiceVO) o;
        return progeny.equals(that.progeny);

    }

    public int hashCode() {

        return progeny.hashCode();

    }

}
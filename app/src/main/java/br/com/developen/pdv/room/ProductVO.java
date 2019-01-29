package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "Product",
        primaryKeys = {"progeny"},
        foreignKeys = {
                @ForeignKey(entity = ProgenyVO.class,
                        parentColumns = "identifier",
                        childColumns = "progeny",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "widthUnit",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "heightUnit",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "lengthUnit",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "contentUnit",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "grossWeightUnit",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = MeasureUnitVO.class,
                        parentColumns = "identifier",
                        childColumns = "netWeightUnit",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)
        }, indices = {
        @Index(value={"progeny"}),
        @Index(value={"widthUnit"}),
        @Index(value={"heightUnit"}),
        @Index(value={"lengthUnit"}),
        @Index(value={"contentUnit"}),
        @Index(value={"grossWeightUnit"}),
        @Index(value={"netWeightUnit"})})
public class ProductVO {

    @ColumnInfo(name = "progeny")
    private Integer progeny;

    //DIMENSOES
    @ColumnInfo(name="widthUnit")
    private Integer widthUnit;

    @ColumnInfo(name="widthValue")
    private Double widthValue;

    @ColumnInfo(name="heightUnit")
    private Integer heightUnit;

    @ColumnInfo(name="heightValue")
    private Double heightValue;

    @ColumnInfo(name="lengthUnit")
    private Integer lengthUnit;

    @ColumnInfo(name="lengthValue")
    private Double lengthValue;

    //VOLUME
    @ColumnInfo(name="contentUnit")
    private Integer contentUnit;

    @ColumnInfo(name="contentValue")
    private Double contentValue;

    //PESO
    @ColumnInfo(name="grossWeightUnit")
    private Integer grossWeightUnit;

    @ColumnInfo(name="grossWeightValue")
    private Double grossWeightValue;

    @ColumnInfo(name="netWeightUnit")
    private Integer netWeightUnit;

    @ColumnInfo(name="netWeightValue")
    private Double netWeightValue;

    public Integer getProgeny() {

        return progeny;

    }

    public void setProgeny(Integer progeny) {

        this.progeny = progeny;

    }

    public Integer getWidthUnit() {

        return widthUnit;

    }

    public void setWidthUnit(Integer widthUnit) {

        this.widthUnit = widthUnit;

    }

    public Double getWidthValue() {

        return widthValue;

    }

    public void setWidthValue(Double widthValue) {

        this.widthValue = widthValue;

    }

    public Integer getHeightUnit() {

        return heightUnit;

    }

    public void setHeightUnit(Integer heightUnit) {

        this.heightUnit = heightUnit;

    }

    public Double getHeightValue() {

        return heightValue;

    }

    public void setHeightValue(Double heightValue) {

        this.heightValue = heightValue;

    }

    public Integer getLengthUnit() {

        return lengthUnit;

    }

    public void setLengthUnit(Integer lengthUnit) {

        this.lengthUnit = lengthUnit;

    }

    public Double getLengthValue() {

        return lengthValue;

    }

    public void setLengthValue(Double lengthValue) {

        this.lengthValue = lengthValue;

    }

    public Integer getContentUnit() {

        return contentUnit;

    }

    public void setContentUnit(Integer contentUnit) {

        this.contentUnit = contentUnit;

    }

    public Double getContentValue() {

        return contentValue;

    }

    public void setContentValue(Double contentValue) {

        this.contentValue = contentValue;

    }

    public Integer getGrossWeightUnit() {

        return grossWeightUnit;

    }

    public void setGrossWeightUnit(Integer grossWeightUnit) {

        this.grossWeightUnit = grossWeightUnit;

    }

    public Double getGrossWeightValue() {

        return grossWeightValue;

    }

    public void setGrossWeightValue(Double grossWeightValue) {

        this.grossWeightValue = grossWeightValue;

    }

    public Integer getNetWeightUnit() {

        return netWeightUnit;

    }

    public void setNetWeightUnit(Integer netWeightUnit) {

        this.netWeightUnit = netWeightUnit;

    }

    public Double getNetWeightValue() {

        return netWeightValue;

    }

    public void setNetWeightValue(Double netWeightValue) {

        this.netWeightValue = netWeightValue;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVO productVO = (ProductVO) o;
        return progeny.equals(productVO.progeny);

    }

    public int hashCode() {

        return progeny.hashCode();

    }

}
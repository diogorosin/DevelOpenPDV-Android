package br.com.developen.pdv.room;

import androidx.room.Embedded;

public class ProductModel extends ProgenyModel {

    //DIMENSOES
    @Embedded(prefix = "widthUnit_")
    private MeasureUnitModel widthUnit;

    private Double widthValue;

    @Embedded(prefix = "heightUnit_")
    private MeasureUnitModel heightUnit;

    private Double heightValue;

    @Embedded(prefix = "lengthUnit_")
    private MeasureUnitModel lengthUnit;

    private Double lengthValue;

    //VOLUME
    @Embedded(prefix = "contentUnit_")
    private MeasureUnitModel contentUnit;

    private Double contentValue;

    //PESO
    @Embedded(prefix = "grossWeightUnit_")
    private MeasureUnitModel grossWeightUnit;

    private Double grossWeightValue;

    @Embedded(prefix = "netWeightUnit_")
    private MeasureUnitModel netWeightUnit;

    private Double netWeightValue;

    public MeasureUnitModel getWidthUnit() {

        return widthUnit;

    }

    public void setWidthUnit(MeasureUnitModel widthUnit) {

        this.widthUnit = widthUnit;

    }

    public Double getWidthValue() {

        return widthValue;

    }

    public void setWidthValue(Double widthValue) {

        this.widthValue = widthValue;

    }

    public MeasureUnitModel getHeightUnit() {

        return heightUnit;

    }

    public void setHeightUnit(MeasureUnitModel heightUnit) {

        this.heightUnit = heightUnit;

    }

    public Double getHeightValue() {

        return heightValue;

    }

    public void setHeightValue(Double heightValue) {

        this.heightValue = heightValue;

    }

    public MeasureUnitModel getLengthUnit() {

        return lengthUnit;

    }

    public void setLengthUnit(MeasureUnitModel lengthUnit) {

        this.lengthUnit = lengthUnit;

    }

    public Double getLengthValue() {

        return lengthValue;

    }

    public void setLengthValue(Double lengthValue) {

        this.lengthValue = lengthValue;

    }

    public MeasureUnitModel getContentUnit() {

        return contentUnit;

    }

    public void setContentUnit(MeasureUnitModel contentUnit) {

        this.contentUnit = contentUnit;

    }

    public Double getContentValue() {

        return contentValue;

    }

    public void setContentValue(Double contentValue) {

        this.contentValue = contentValue;

    }

    public MeasureUnitModel getGrossWeightUnit() {

        return grossWeightUnit;

    }

    public void setGrossWeightUnit(MeasureUnitModel grossWeightUnit) {

        this.grossWeightUnit = grossWeightUnit;

    }

    public Double getGrossWeightValue() {

        return grossWeightValue;

    }

    public void setGrossWeightValue(Double grossWeightValue) {

        this.grossWeightValue = grossWeightValue;

    }

    public MeasureUnitModel getNetWeightUnit() {

        return netWeightUnit;

    }

    public void setNetWeightUnit(MeasureUnitModel netWeightUnit) {

        this.netWeightUnit = netWeightUnit;

    }

    public Double getNetWeightValue() {

        return netWeightValue;

    }

    public void setNetWeightValue(Double netWeightValue) {

        this.netWeightValue = netWeightValue;

    }

}
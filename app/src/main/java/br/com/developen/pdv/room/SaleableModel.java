package br.com.developen.pdv.room;

import androidx.room.DatabaseView;
import androidx.room.Embedded;

@DatabaseView(viewName = "Saleable", value = "SELECT " +
        "Mer.product AS 'identifier', " +
        "Pgn.active AS 'active', " +
        "Cat.identifier AS 'catalog_identifier', " +
        "Cat.active AS 'catalog_active', " +
        "Cat.position AS 'catalog_position', " +
        "Cat.denomination AS 'catalog_denomination', " +
        "Mer.position AS 'position', " +
        "Mer.reference AS 'reference', " +
        "Mer.label AS 'label', " +
        "MU.identifier AS 'measureUnit_identifier', " +
        "MU.denomination AS 'measureUnit_denomination', " +
        "MU.acronym AS 'measureUnit_acronym', " +
        "MU.'group' AS 'measureUnit_group', " +
        "Mer.price, " +
        "'M' AS 'type' " +
        "FROM Merchandise Mer " +
        "INNER JOIN Product Pdt ON Pdt.progeny = Mer.product " +
        "INNER JOIN Progeny Pgn ON Pgn.identifier = Pdt.progeny " +
        "INNER JOIN Catalog Cat ON Cat.identifier = Mer.catalog " +
        "INNER JOIN MeasureUnit MU ON MU.identifier = Mer.measureUnit " +
        "WHERE Cat.active = 1 AND Pgn.active = 1 " +
        "UNION ALL " +
        "SELECT " +
        "Ser.progeny AS 'identifier', " +
        "Pgn.active AS 'active', " +
        "Cat.identifier AS 'catalog_identifier', " +
        "Cat.active AS 'catalog_active', " +
        "Cat.position AS 'catalog_position', " +
        "Cat.denomination AS 'catalog_denomination', " +
        "Ser.position AS 'position', " +
        "Ser.reference AS 'reference', " +
        "Ser.label AS 'label', " +
        "MU.identifier AS 'measureUnit_identifier', " +
        "MU.denomination AS 'measureUnit_denomination', " +
        "MU.acronym AS 'measureUnit_acronym', " +
        "MU.'group' AS 'measureUnit_group', " +
        "Ser.price, " +
        "'S' AS 'type' " +
        "FROM Service Ser " +
        "INNER JOIN Progeny Pgn ON Pgn.identifier = Ser.progeny " +
        "INNER JOIN Catalog Cat ON Cat.identifier = Ser.catalog " +
        "INNER JOIN MeasureUnit MU ON MU.identifier = Ser.measureUnit " +
        "WHERE Cat.active = 1 AND Pgn.active = 1 " +
        "ORDER BY 5, 7")
public class SaleableModel {

    private Integer identifier;

    @Embedded(prefix = "catalog_")
    private CatalogModel catalog;

    private Integer position;

    private Integer reference;

    private String label;

    @Embedded(prefix = "measureUnit_")
    private MeasureUnitModel measureUnit;

    private Double price;

    private String type;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public CatalogModel getCatalog() {

        return catalog;

    }

    public void setCatalog(CatalogModel catalog) {

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

    public MeasureUnitModel getMeasureUnit() {

        return measureUnit;

    }

    public void setMeasureUnit(MeasureUnitModel measureUnit) {

        this.measureUnit = measureUnit;

    }

    public Double getPrice() {

        return price;

    }

    public void setPrice(Double price) {

        this.price = price;

    }

    public String getType() {

        return type;

    }

    public void setType(String type) {

        this.type = type;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleableModel that = (SaleableModel) o;
        return identifier.equals(that.identifier);

    }

    public int hashCode() {

        return identifier.hashCode();

    }

}
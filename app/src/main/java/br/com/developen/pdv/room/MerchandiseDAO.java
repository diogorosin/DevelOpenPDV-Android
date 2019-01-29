package br.com.developen.pdv.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MerchandiseDAO {

    @Insert
    void create(MerchandiseVO merchandiseVO);

    @Query("SELECT Mer.* " +
            "FROM Merchandise Mer " +
            "INNER JOIN Product Pdt ON Pdt.progeny = Mer.product " +
            "INNER JOIN Progeny Pgn ON Pgn.identifier = Pdt.progeny " +
            "WHERE Pgn.identifier = :identifier")
    MerchandiseVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 " +
            "FROM ProductProduct PdtPdt " +
            "WHERE PdtPdt.parent = :identifier AND PdtPdt.active = 1")
    Boolean isComposed(int identifier);

    @Query("SELECT COUNT(*) > 0 " +
            "FROM Merchandise Mer " +
            "INNER JOIN Product Pdt ON Pdt.progeny = Mer.product " +
            "INNER JOIN Progeny Pgn ON Pgn.identifier = Pdt.progeny " +
            "WHERE Pgn.identifier = :identifier")
    Boolean exists(int identifier);

    @Update
    void update(MerchandiseVO merchandiseVO);

    @Delete
    void delete(MerchandiseVO merchandiseVO);

    @Query("SELECT " +
            "Pgn.identifier AS 'identifier', " +
            "Pgn.denomination AS 'denomination', " +
            "Pgn.active AS 'active', " +
            "WidthUnit.identifier AS 'widthUnit_identifier', " +
            "WidthUnit.denomination AS 'widthUnit_denomination', " +
            "WidthUnit.acronym AS 'widthUnit_acronym', " +
            "WidthUnit.'group' AS 'widthUnit_group', " +
            "Pdt.widthValue AS 'widthValue', " +
            "HeightUnit.identifier AS 'heightUnit_identifier', " +
            "HeightUnit.denomination AS 'heightUnit_denomination', " +
            "HeightUnit.acronym AS 'heightUnit_acronym', " +
            "HeightUnit.'group' AS 'heightUnit_group', " +
            "Pdt.heightValue AS 'heightValue', " +
            "LengthUnit.identifier AS 'lengthUnit_identifier', " +
            "LengthUnit.denomination AS 'lengthUnit_denomination', " +
            "LengthUnit.acronym AS 'lengthUnit_acronym', " +
            "LengthUnit.'group' AS 'lengthUnit_group', " +
            "Pdt.lengthValue AS 'lengthValue', " +
            "ContentUnit.identifier AS 'contentUnit_identifier', " +
            "ContentUnit.denomination AS 'contentUnit_denomination', " +
            "ContentUnit.acronym AS 'contentUnit_acronym', " +
            "ContentUnit.'group' AS 'contentUnit_group', " +
            "Pdt.contentValue AS 'contentValue', " +
            "GrossWeightUnit.identifier AS 'grossWeightUnit_identifier', " +
            "GrossWeightUnit.denomination AS 'grossWeightUnit_denomination', " +
            "GrossWeightUnit.acronym AS 'grossWeightUnit_acronym', " +
            "GrossWeightUnit.'group' AS 'grossWeightUnit_group', " +
            "Pdt.grossWeightValue AS 'grossWeightValue', " +
            "NetWeightUnit.identifier AS 'netWeightUnit_identifier', " +
            "NetWeightUnit.denomination AS 'netWeightUnit_denomination', " +
            "NetWeightUnit.acronym AS 'netWeightUnit_acronym', " +
            "NetWeightUnit.'group' AS 'netWeightUnit_group', " +
            "Pdt.netWeightValue AS 'netWeightValue', " +
            "Catalog.identifier AS 'catalog_identifier', " +
            "Catalog.denomination AS 'catalog_denomination', " +
            "Catalog.position AS 'catalog_position', " +
            "Mer.position AS 'position', " +
            "Mer.reference AS 'reference', " +
            "Mer.label AS 'label', " +
            "MeasureUnit.identifier AS 'measureUnit_identifier', " +
            "MeasureUnit.denomination AS 'measureUnit_denomination', " +
            "MeasureUnit.acronym AS 'measureUnit_acronym', " +
            "MeasureUnit.'group' AS 'measureUnit_group', " +
            "Mer.price AS 'price' " +
            "FROM Merchandise Mer " +
            "INNER JOIN Product Pdt ON Pdt.progeny = Mer.product " +
            "INNER JOIN Progeny Pgn ON Pgn.identifier = Pdt.progeny " +
            "LEFT OUTER JOIN MeasureUnit WidthUnit ON WidthUnit.identifier = Pdt.widthUnit " +
            "LEFT OUTER JOIN MeasureUnit HeightUnit ON HeightUnit.identifier = Pdt.heightUnit " +
            "LEFT OUTER JOIN MeasureUnit LengthUnit ON LengthUnit.identifier = Pdt.lengthUnit " +
            "LEFT OUTER JOIN MeasureUnit ContentUnit ON ContentUnit.identifier = Pdt.contentUnit " +
            "LEFT OUTER JOIN MeasureUnit GrossWeightUnit ON GrossWeightUnit.identifier = Pdt.grossWeightUnit " +
            "LEFT OUTER JOIN MeasureUnit NetWeightUnit ON NetWeightUnit.identifier = Pdt.netWeightUnit " +
            "LEFT OUTER JOIN Catalog Catalog ON Catalog.identifier = Mer.catalog " +
            "LEFT OUTER JOIN MeasureUnit MeasureUnit ON MeasureUnit.identifier = Mer.measureUnit")
    LiveData<List<MerchandiseModel>> getMerchandises();

}
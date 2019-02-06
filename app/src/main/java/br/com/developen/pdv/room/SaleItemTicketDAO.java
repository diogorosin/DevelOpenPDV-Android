package br.com.developen.pdv.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SaleItemTicketDAO {

    @Insert
    void create(SaleItemTicketVO saleItemTicketVO);

    @Query("SELECT SleItmTkt.* " +
            "FROM SaleItemTicket SleItmTkt " +
            "WHERE SleItmTkt.sale = :sale AND SleItmTkt.item = :item AND SleItmTkt.ticket = :ticket")
    SaleItemTicketVO retrieve(int sale, int item, int ticket);

    @Query("SELECT COUNT(*) > 0 " +
            "FROM SaleItemTicket SleItmTkt " +
            "WHERE SleItmTkt.sale = :sale AND SleItmTkt.item = :item AND SleItmTkt.ticket = :ticket")
    Boolean exists(int sale, int item, int ticket);

    @Query("UPDATE SaleItemTicket " +
            "SET printed = 1 " +
            "WHERE sale = :sale AND item = :item AND ticket = :ticket")
    void setTicketAsPrinted(int sale, int item, int ticket);

    @Update
    void update(SaleItemTicketVO saleItemTicketVO);

    @Delete
    void delete(SaleItemTicketVO saleItemTicketVO);

    @Query("SELECT " +
            "Sle.identifier AS 'saleItem_sale_identifier', " +
            "Sle.number AS 'saleItem_sale_number', " +
            "Sle.status AS 'saleItem_sale_status', " +
            "SleItm.item AS 'saleItem_item', " +
            "SleItmTkt.ticket AS 'ticket', " +
            "SleItmTkt.'of' AS 'of', " +
            "SleItmTkt.label AS 'label', " +
            "SleItmTkt.quantity AS 'quantity', " +
            "MseUnt.identifier AS 'measureUnit_identifier', " +
            "MseUnt.denomination AS 'measureUnit_denomination', " +
            "MseUnt.acronym AS 'measureUnit_acronym', " +
            "MseUnt.'group' AS 'measureUnit_group', " +
            "SleItmTkt.printed AS 'printed' " +
            "FROM SaleItemTicket SleItmTkt " +
            "INNER JOIN SaleItem SleItm ON SleItm.sale = SleItmTkt.sale AND SleItm.item = SleItmTkt.item " +
            "INNER JOIN Sale Sle ON Sle.identifier = SleItm.sale " +
            "INNER JOIN MeasureUnit MseUnt ON MseUnt.identifier = SleItmTkt.measureunit " +
            "WHERE Sle.identifier = :sale")
    List<SaleItemTicketModel> getTicketsOfSale(int sale);

    @Query("SELECT COUNT(*) " +
            "FROM SaleItemTicket SleItmTkt " +
            "WHERE SleItmTkt.sale = :sale AND SleItmTkt.printed = 1")
    Integer getPrintedTicketsCountOfSale(int sale);

}
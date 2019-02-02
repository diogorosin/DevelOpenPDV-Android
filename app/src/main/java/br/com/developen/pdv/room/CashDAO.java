package br.com.developen.pdv.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CashDAO {

    @Insert
    Long create(CashVO cashVO);

    @Query("SELECT C.* FROM Cash C WHERE C.identifier = :identifier")
    CashVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 FROM Cash C WHERE C.identifier = :identifier")
    Boolean exists(int identifier);

    @Update
    void update(CashVO cashVO);

    @Delete
    void delete(CashVO cashVO);

    @Query("SELECT (COUNT(*) > 0) AND (C.operation <> 'FEC') FROM Cash C ORDER BY C.dateTime DESC LIMIT 1")
    LiveData<Boolean> isOpen();

    @Query("SELECT IFNULL(" +
            "(SELECT IFNULL(SUM(E1.value), 0) " +
            "FROM Cash E1 " +
            "WHERE E1.type = 'E' AND E1.identifier >= (SELECT MAX(E2.identifier) FROM Cash E2 WHERE E2.operation == 'ABE')) " +
            "-" +
            "(SELECT IFNULL(SUM(S1.value), 0) " +
            "FROM Cash S1 " +
            "WHERE S1.type = 'S' AND S1.identifier >= (SELECT MAX(S2.identifier) FROM Cash S2 WHERE S2.operation == 'ABE')) " +
            ", 0)")
    LiveData<Double> value();

    @Query("SELECT C1.operation AS 'operation', C1.dateTime AS 'dateTime', C1.value AS 'value', C1.type AS 'type' " +
            "FROM Cash C1 " +
            "WHERE C1.identifier = (SELECT MAX(S1.identifier) FROM Cash S1 WHERE S1.operation = 'ABE') AND C1.operation = 'ABE' " +
            "UNION ALL " +
            "SELECT C2.operation AS 'operation', MAX(C2.dateTime) AS 'dateTime', SUM(C2.value) AS 'value', C2.type AS 'type' " +
            "FROM Cash C2 " +
            "WHERE C2.identifier > (SELECT MAX(S2.identifier) FROM Cash S2 WHERE S2.operation = 'ABE') " +
            "GROUP BY 1, 4 " +
            "ORDER BY 2")
    LiveData<List<CashModel>> cashSummary();

    @Query(" SELECT C1.operation AS 'operation', C1.dateTime AS 'dateTime', C1.value AS 'value', C1.type AS 'type', I1.name AS 'user_name' " +
            "FROM Cash C1 " +
            "INNER JOIN User U1 on U1.individual = C1.user " +
            "INNER JOIN Individual I1 on I1.subject = U1.individual " +
            "WHERE C1.identifier = (SELECT MAX(S1.identifier) FROM Cash S1 WHERE S1.operation = 'ABE') AND C1.operation = 'ABE' " +
            "UNION ALL " +
            "SELECT C2.operation AS 'operation', MAX(C2.dateTime) AS 'dateTime', SUM(C2.value) AS 'value', C2.type AS 'type', I2.name AS 'user_name' " +
            "FROM Cash C2 " +
            "INNER JOIN User U2 on U2.individual = C2.user " +
            "INNER JOIN Individual I2 on I2.subject = U2.individual " +
            "WHERE C2.identifier > (SELECT MAX(S2.identifier) FROM Cash S2 WHERE S2.operation = 'ABE') AND C2.operation = 'REC' " +
            "GROUP BY 1, 4 " +
            "UNION ALL " +
            "SELECT C3.operation AS 'operation', MAX(C3.dateTime) AS 'dateTime', SUM(C3.value) AS 'value', C3.type AS 'type', I3.name AS 'user_name' " +
            "FROM Cash C3 " +
            "INNER JOIN User U3 on U3.individual = C3.user " +
            "INNER JOIN Individual I3 on I3.subject = U3.individual " +
            "WHERE C3.identifier > (SELECT MAX(S3.identifier) FROM Cash S3 WHERE S3.operation = 'ABE') AND C3.operation = 'TRC' " +
            "GROUP BY 1, 4 " +
            "UNION ALL " +
            "SELECT C4.operation AS 'operation', C4.dateTime AS 'dateTime', C4.value AS 'value', C4.type AS 'type', I4.name AS 'user_name' " +
            "FROM Cash C4 " +
            "INNER JOIN User U4 on U4.individual = C4.user " +
            "INNER JOIN Individual I4 on I4.subject = U4.individual " +
            "WHERE C4.identifier > (SELECT MAX(S4.identifier) FROM Cash S4 WHERE S4.operation = 'ABE') AND C4.operation = 'SAN' " +
            "UNION ALL " +
            "SELECT C5.operation AS 'operation', C5.dateTime AS 'dateTime', C5.value AS 'value', C5.type AS 'type', I5.name AS 'user_name' " +
            "FROM Cash C5 " +
            "INNER JOIN User U5 on U5.individual = C5.user " +
            "INNER JOIN Individual I5 on I5.subject = U5.individual " +
            "WHERE C5.identifier > (SELECT MAX(S5.identifier) FROM Cash S5 WHERE S5.operation = 'ABE') AND C5.operation = 'COM' " +
            "UNION ALL " +
            "SELECT C6.operation AS 'operation', C6.dateTime AS 'dateTime', C6.value AS 'value', C6.type AS 'type', I6.name AS 'user_name' " +
            "FROM Cash C6 " +
            "INNER JOIN User U6 on U6.individual = C6.user " +
            "INNER JOIN Individual I6 on I6.subject = U6.individual " +
            "WHERE C6.identifier > (SELECT MAX(S6.identifier) FROM Cash S6 WHERE S6.operation = 'ABE') AND C6.operation = 'FEC' " +
            "ORDER BY 2")
    List<CashModel> cashSummaryReport();

    @Query("SELECT C1.*, " +
            "S1.identifier AS 'user_identifier', " +
            "S1.active AS 'user_active', " +
            "S1.level AS 'user_level', " +
            "I1.name AS 'user_name', " +
            "U1.login AS 'user_login', " +
            "U1.password AS 'user_password' " +
            "FROM Cash C1 " +
            "INNER JOIN User U1 ON U1.individual = C1.user " +
            "INNER JOIN Individual I1 on I1.subject = U1.individual " +
            "INNER JOIN Subject S1 on S1.identifier = I1.subject " +
            "WHERE C1.identifier >= (SELECT MAX(C2.identifier) FROM Cash C2 WHERE C2.operation = 'ABE') " +
            "ORDER BY C1.dateTime")
    LiveData<List<CashModel>> cashEntry();

}
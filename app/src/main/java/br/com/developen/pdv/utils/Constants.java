package br.com.developen.pdv.utils;

public class Constants {

    public static final String SHARED_PREFERENCES_NAME = "DEVELOPEN_PDV";

    public static final String DEVICE_IDENTIFIER_PROPERTY = "DEVICE_IDENTIFIER_PROPERTY";

    public static final String DEVICE_ACTIVE_PROPERTY = "DEVICE_ACTIVE_PROPERTY";

    public static final String DEVICE_CONFIGURED_PROPERTY = "DEVICE_CONFIGURED_PROPERTY";

    public static final String DEVICE_ALIAS_PROPERTY = "DEVICE_ALIAS_PROPERTY";

    public static final String COMPANY_IDENTIFIER_PROPERTY = "COMPANY_IDENTIFIER_PROPERTY";

    public static final String COMPANY_ACTIVE_PROPERTY = "COMPANY_ACTIVE_PROPERTY";

    public static final String COMPANY_DENOMINATION_PROPERTY = "COMPANY_DENOMINATION_PROPERTY";

    public static final String COMPANY_FANCYNAME_PROPERTY = "COMPANY_FANCYNAME_PROPERTY";

    public static final String USER_IDENTIFIER_PROPERTY = "USER_IDENTIFIER_PROPERTY";

    public static final String USER_NAME_PROPERTY = "USER_NAME_PROPERTY";

    public static final String USER_LOGIN_PROPERTY = "USER_LOGIN_PROPERTY";

    public static final String CURRENT_SALE_NUMBER_PROPERTY = "CURRENT_SALE_NUMBER_PROPERTY";

    public static final String COUPON_TITLE_PROPERTY = "COUPON_TITLE_PROPERTY";

    public static final String COUPON_SUBTITLE_PROPERTY = "COUPON_SUBTITLE_PROPERTY";

    /* SALES *********************************************/
    public static final String GET_TICKET_COUNT_OF_TODAY =
            "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "SaleItemTicket SleItmTkt " +
                    "INNER JOIN " +
                    "SaleItem SleItm ON SleItm.sale = SleItmTkt.sale AND SleItm.item = SleItmTkt.item " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now') AND SleItmTkt.printed = 1";

    public static final String GET_SALE_BY_IDENTIFIER =
            "SELECT " +
                    "Sle.identifier AS 'identifier', " +
                    "Sle.number AS 'number', " +
                    "Sle.status AS 'status', " +
                    "Sle.dateTime AS 'dateTime', " +
                    "Sbj.identifier AS 'user_identifier', " +
                    "Sbj.active AS 'user_active', " +
                    "Sbj.level AS 'user_level', " +
                    "Ind.name AS 'user_name' " +
                    "FROM " +
                    "Sale Sle " +
                    "INNER JOIN " +
                    "User Usr ON Usr.individual = Sle.user " +
                    "INNER JOIN " +
                    "Individual Ind ON Ind.subject = Usr.individual " +
                    "INNER JOIN " +
                    "Subject Sbj ON Sbj.identifier = Ind.subject " +
                    "WHERE " +
                    "Sle.identifier = :identifier";

    public static final String GET_SALE_COUNT_OF_TODAY =
            "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "Sale Sle " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now')";

    public static  final String GET_SALE_BILLING_OF_TODAY =
            "SELECT " +
                    "IFNULL(SUM(SleItm.total), 0) " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now')";

    public static final String GET_SALES =
            "SELECT " +
                    "Sle.identifier AS 'identifier', " +
                    "Sle.number AS 'number', " +
                    "Sle.status AS 'status', " +
                    "Sle.dateTime AS 'dateTime', " +
                    "Sbj.identifier AS 'user_identifier', " +
                    "Sbj.active AS 'user_active', " +
                    "Sbj.level AS 'user_level', " +
                    "Ind.name AS 'user_name' " +
                    "FROM " +
                    "Sale Sle " +
                    "INNER JOIN " +
                    "User Usr ON Usr.individual = Sle.user " +
                    "INNER JOIN " +
                    "Individual Ind ON Ind.subject = Usr.individual " +
                    "INNER JOIN " +
                    "Subject Sbj ON Sbj.identifier = Ind.subject";

    public static final String GET_SALES_BY_PERIOD_OF_TODAY =
            "SELECT " +
                    "STRFTIME('%H', Sle.dateTime) AS 'period', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now') " +
                    "GROUP BY " +
                    "1";

    public static final String GET_SALES_BY_PROGENY_OF_TODAY =
            "SELECT " +
                    "Slb.identifier AS 'progeny', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "Saleable Slb ON Slb.identifier = SleItm.progeny " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now') " +
                    "GROUP BY " +
                    "1 " +
                    "ORDER BY " +
                    "2 DESC";

    public static final String GET_SALES_BY_USER_OF_TODAY =
            "SELECT " +
                    "Usr.individual AS 'user', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "User Usr ON Usr.individual = Sle.user " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now') " +
                    "GROUP BY " +
                    "1 " +
                    "ORDER BY " +
                    "2 DESC";

    public static final String GET_RECEIVED_OF_SALE =
            "SELECT " +
                    "IFNULL(SUM(SleRpt.value), 0) " +
                    "FROM " +
                    "SaleReceipt SleRpt " +
                    "WHERE " +
                    "SleRpt.sale = :sale";

    public static final String GET_TO_RECEIVE_OF_SALE =
            "SELECT " +
                    "(SELECT IFNULL(SUM(SleItm.total), 0) FROM SaleItem SleItm WHERE SleItm.sale = :sale)" +
                    " - " +
                    "(SELECT IFNULL(SUM(SleRpt.value), 0) FROM SaleReceipt SleRpt WHERE SleRpt.sale = :sale)";

    public static final String GET_TOTAL_OF_SALE =
            "SELECT " +
                    "IFNULL(SUM(SleItm.total), 0) " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE Sle.identifier = :sale";

    public static final String GET_SUBTOTAL_OF_SALE =
            "SELECT " +
                    "IFNULL(SUM(SleItm.total), 0) " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE Sle.identifier = :sale";

}
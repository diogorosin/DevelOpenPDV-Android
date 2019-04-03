package br.com.developen.pdv.utils;

public class Constants {


    /** 00 INDEFINIDO (BLOQUEADO) */
    public static final int UNDEFINED_SUBJECT_LEVEL = 00;

    /** 01 CLIENTE/FORNECEDOR */
    public static final int CUSTOMER_SUPPLIER_SUBJECT_LEVEL = 01;

    /** 02 OPERADOR DE CAIXA */
    public static final int CASHIER_SUBJECT_LEVEL = 02;

    /** 03 GERENTE */
    public static final int MANAGER_SUBJECT_LEVEL = 03;

    /** 04 SUPERVISOR */
    public static final int SUPERVISOR_SUBJECT_LEVEL = 04;

     /** 05 EMPRESARIO/SOCIO PROPRIETARIO */
    public static final int OWNER_PARTNER_SUBJECT_LEVEL = 05;

     /** 06 ANALISTA DE SUPORTE */
    public static final int SUPPORT_ANALYST_SUBJECT_LEVEL = 06;

     /** 07 DESENVOLVEDOR */
    public static final int DEVELOPER_SUBJECT_LEVEL = 07;


     /** VENDA EM ABERTO */
    public static final String OPENED_SALE_STATUS = "A";

     /** VENDA FINALIZADA */
    public static final String FINISHED_SALE_STATUS = "F";

    /** VENDA CANCELADA */
    public static final String CANCELED_SALE_STATUS = "C";


     /** ABERTURA DO CAIXA */
    public static final String OPEN_CASH_OPERATION = "ABE";

     /** FECHAMENTO DO CAIXA */
    public static final String CLOSE_CASH_OPERATION = "FEC";

     /** SANGRIA DO CAIXA */
    public static final String REMOVAL_CASH_OPERATION = "SAN";

     /** SUPRIMENTO DO CAIXA */
    public static final String SUPPLY_CASH_OPERATION = "SUP";

     /** RECEBIMENTO DO CAIXA */
    public static final String RECEIPT_CASH_OPERATION = "REC";

     /** TROCO DO CAIXA */
    public static final String CHANGE_CASH_OPERATION = "TRC";

    /** ESTORNO DO CAIXA */
    public static final String REVERSAL_CASH_OPERATION = "EST";


    /** SAIDA DO CAIXA */
    public static final String OUT_CASH_TYPE = "S";

    /** ENTRADA DO CAIXA */
    public static final String ENTRY_CASH_TYPE = "E";


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

    public static final String USER_LEVEL_PROPERTY = "USER_LEVEL_PROPERTY";

    public static final String USER_NUMERIC_PASSWORD_PROPERTY = "USER_NUMERIC_PASSWORD_PROPERTY";

    public static final String CURRENT_SALE_NUMBER_PROPERTY = "CURRENT_SALE_NUMBER_PROPERTY";

    public static final String COUPON_TITLE_PROPERTY = "COUPON_TITLE_PROPERTY";

    public static final String COUPON_SUBTITLE_PROPERTY = "COUPON_SUBTITLE_PROPERTY";

    /* SALES *********************************************/

    public static final String GET_SALE_BY_IDENTIFIER =
            "SELECT " +
                    "Sle.identifier AS 'identifier', " +
                    "Sle.number AS 'number', " +
                    "Sle.status AS 'status', " +
                    "Sle.dateTime AS 'dateTime', " +
                    "Sbj.identifier AS 'user_identifier', " +
                    "Sbj.active AS 'user_active', " +
                    "Sbj.level AS 'user_level', " +
                    "Ind.name AS 'user_name', " +
                    "(SELECT SUM(total) FROM SaleItem WHERE sale = Sle.identifier) AS 'total', " +
                    "Sle.note AS 'note' " +
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

    /* MONTH */

    public static final String GET_TICKET_COUNT_OF_MONTH =
            "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "SaleItemTicket SleItmTkt " +
                    "INNER JOIN " +
                    "SaleItem SleItm ON SleItm.sale = SleItmTkt.sale AND SleItm.item = SleItmTkt.item " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) >= DATE('now', 'start of month') AND SleItmTkt.printed = 1";

    public static final String GET_SALE_COUNT_OF_MONTH =
            "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "Sale Sle " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) >= DATE('now', 'start of month')";

    public static  final String GET_SALE_BILLING_OF_MONTH =
            "SELECT " +
                    "IFNULL(SUM(SleItm.total), 0) " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) >= DATE('now', 'start of month')";

    public static final String GET_SALES_BY_PERIOD_OF_MONTH =
            "SELECT " +
                    "STRFTIME('%d', Sle.dateTime) AS 'period', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) >= DATE('now', 'start of month') " +
                    "GROUP BY " +
                    "1";

    public static final String GET_SALES_BY_USER_OF_MONTH =
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
                    "Sle.status = 'F' AND DATE(Sle.dateTime) >= DATE('now', 'start of month') " +
                    "GROUP BY " +
                    "1 " +
                    "ORDER BY " +
                    "2 DESC";

    public static final String GET_SALES_BY_PROGENY_OF_MONTH =
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
                    "Sle.status = 'F' AND DATE(Sle.dateTime) >= DATE('now', 'start of month') " +
                    "GROUP BY " +
                    "1 " +
                    "ORDER BY " +
                    "2 DESC";

    /* WEEK */

    public static final String GET_TICKET_COUNT_OF_WEEK =
            "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "SaleItemTicket SleItmTkt " +
                    "INNER JOIN " +
                    "SaleItem SleItm ON SleItm.sale = SleItmTkt.sale AND SleItm.item = SleItmTkt.item " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND " +
                    "DATE(Sle.dateTime) >= CASE WHEN STRFTIME('%w', 'now') = '0' THEN DATE('now') ELSE DATE('now', 'weekday 0', '-7 days') END " +
                    "AND SleItmTkt.printed = 1";

    public static final String GET_SALE_COUNT_OF_WEEK =
            "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "Sale Sle " +
                    "WHERE " +
                    "Sle.status = 'F' AND " +
                    "DATE(Sle.dateTime) >= CASE WHEN STRFTIME('%w', 'now') = '0' THEN DATE('now') ELSE DATE('now', 'weekday 0', '-7 days') END";

    public static  final String GET_SALE_BILLING_OF_WEEK =
            "SELECT " +
                    "IFNULL(SUM(SleItm.total), 0) " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND " +
                    "DATE(Sle.dateTime) >= CASE WHEN STRFTIME('%w', 'now') = '0' THEN DATE('now') ELSE DATE('now', 'weekday 0', '-7 days') END";

    public static final String GET_SALES_BY_PERIOD_OF_WEEK =
            "SELECT " +
                    "STRFTIME('%w', Sle.dateTime) AS 'period', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND " +
                    "DATE(Sle.dateTime) >= CASE WHEN STRFTIME('%w', 'now') = '0' THEN DATE('now') ELSE DATE('now', 'weekday 0', '-7 days') END " +
                    "GROUP BY " +
                    "1";

    public static final String GET_SALES_BY_USER_OF_WEEK =
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
                    "Sle.status = 'F' AND " +
                    "DATE(Sle.dateTime) >= CASE WHEN STRFTIME('%w', 'now') = '0' THEN DATE('now') ELSE DATE('now', 'weekday 0', '-7 days') END " +
                    "GROUP BY " +
                    "1 " +
                    "ORDER BY " +
                    "2 DESC";

    public static final String GET_SALES_BY_PROGENY_OF_WEEK =
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
                    "Sle.status = 'F' AND " +
                    "DATE(Sle.dateTime) >= CASE WHEN STRFTIME('%w', 'now') = '0' THEN DATE('now') ELSE DATE('now', 'weekday 0', '-7 days') END " +
                    "GROUP BY " +
                    "1 " +
                    "ORDER BY " +
                    "2 DESC";

    /* TODAY */

    public static final String GET_TICKET_COUNT_OF_DATE =
            "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "SaleItemTicket SleItmTkt " +
                    "INNER JOIN " +
                    "SaleItem SleItm ON SleItm.sale = SleItmTkt.sale AND SleItm.item = SleItmTkt.item " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE(:date) AND SleItmTkt.printed = 1";

    public static final String GET_SALE_COUNT_OF_DATE =
            "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "Sale Sle " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE(:date)";

    public static  final String GET_SALE_BILLING_OF_DATE =
            "SELECT " +
                    "IFNULL(SUM(SleItm.total), 0) " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE(:date)";

    public static final String GET_SALES =
            "SELECT " +
                    "Sle.identifier AS 'identifier', " +
                    "Sle.number AS 'number', " +
                    "Sle.status AS 'status', " +
                    "Sle.dateTime AS 'dateTime', " +
                    "Sbj.identifier AS 'user_identifier', " +
                    "Sbj.active AS 'user_active', " +
                    "Sbj.level AS 'user_level', " +
                    "Ind.name AS 'user_name', " +
                    "(SELECT SUM(total) FROM SaleItem WHERE sale = Sle.identifier) AS 'total', " +
                    "Sle.note AS 'note' " +
                    "FROM " +
                    "Sale Sle " +
                    "INNER JOIN " +
                    "User Usr ON Usr.individual = Sle.user " +
                    "INNER JOIN " +
                    "Individual Ind ON Ind.subject = Usr.individual " +
                    "INNER JOIN " +
                    "Subject Sbj ON Sbj.identifier = Ind.subject " +
                    "WHERE Sle.status <> 'A' " +
                    "ORDER BY Sle.identifier DESC";

    public static final String GET_SALES_BY_PERIOD_OF_DATE =
            "SELECT " +
                    "STRFTIME('%H', Sle.dateTime) AS 'period', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE(:date) " +
                    "GROUP BY " +
                    "1";

    public static final String GET_SALES_BY_PROGENY_OF_DATE =
            "SELECT " +
                    "Slb.identifier AS 'progeny', " +
                    "Slb.reference AS 'reference', " +
                    "Slb.label AS 'label', " +
                    "SUM(SleItm.quantity) AS 'quantity', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "Saleable Slb ON Slb.identifier = SleItm.progeny " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE(:date) " +
                    "GROUP BY " +
                    "1, 2, 3 " +
                    "ORDER BY " +
                    "5 DESC";

    public static final String GET_SALES_BY_PROGENY_OF_PERIOD =
            "SELECT " +
                    "Slb.identifier AS 'progeny', " +
                    "Slb.reference AS 'reference', " +
                    "Slb.label AS 'label', " +
                    "SUM(SleItm.quantity) AS 'quantity', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "Saleable Slb ON Slb.identifier = SleItm.progeny " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) BETWEEN :start AND :end " +
                    "GROUP BY " +
                    "1, 2, 3 " +
                    "ORDER BY " +
                    "5 DESC";

    public static final String GET_SALES_BY_USER_OF_DATE =
            "SELECT " +
                    "Usr.individual AS 'user', " +
                    "Ind.name AS 'name', " +
                    "((SUM(SleItm.total)*100)/(SELECT SUM(SI.total) FROM SaleItem SI INNER JOIN Sale S ON S.identifier = SI.sale WHERE S.status = 'F' AND DATE(S.dateTime) = DATE(:date))) AS 'percentage', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "User Usr ON Usr.individual = Sle.user " +
                    "INNER JOIN " +
                    "Individual Ind ON Ind.subject = Usr.individual " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE(:date) " +
                    "GROUP BY " +
                    "1, 2 " +
                    "ORDER BY " +
                    "3 DESC";

    public static final String GET_SALES_BY_USER_OF_PERIOD =
            "SELECT " +
                    "Usr.individual AS 'user', " +
                    "Ind.name AS 'name', " +
                    "((SUM(SleItm.total)*100)/(SELECT SUM(SI.total) FROM SaleItem SI INNER JOIN Sale S ON S.identifier = SI.sale WHERE S.status = 'F' AND DATE(S.dateTime) BETWEEN :start AND :end)) AS 'percentage', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "User Usr ON Usr.individual = Sle.user " +
                    "INNER JOIN " +
                    "Individual Ind ON Ind.subject = Usr.individual " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) BETWEEN :start AND :end " +
                    "GROUP BY " +
                    "1, 2 " +
                    "ORDER BY " +
                    "3 DESC";

    public static final String GET_SALES_BY_CATALOG_OF_DATE =
            "SELECT " +
                    "Slb.catalog_identifier AS 'catalog', " +
                    "Slb.catalog_denomination AS 'denomination', " +
                    "((SUM(SleItm.total)*100)/(SELECT SUM(SI.total) FROM SaleItem SI INNER JOIN Sale S ON S.identifier = SI.sale WHERE S.status = 'F' AND DATE(S.dateTime) = DATE(:date))) AS 'percentage', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "Saleable Slb ON Slb.identifier = SleItm.progeny " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE(:date) " +
                    "GROUP BY " +
                    "1, 2 " +
                    "ORDER BY " +
                    "4 DESC";

    public static final String GET_SALES_BY_CATALOG_OF_PERIOD =
            "SELECT " +
                    "Slb.catalog_identifier AS 'catalog', " +
                    "Slb.catalog_denomination AS 'denomination', " +
                    "((SUM(SleItm.total)*100)/(SELECT SUM(SI.total) FROM SaleItem SI INNER JOIN Sale S ON S.identifier = SI.sale WHERE S.status = 'F' AND DATE(S.dateTime) BETWEEN :start AND :end)) AS 'percentage', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "Saleable Slb ON Slb.identifier = SleItm.progeny " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) BETWEEN :start AND :end " +
                    "GROUP BY " +
                    "1, 2 " +
                    "ORDER BY " +
                    "4 DESC";

}
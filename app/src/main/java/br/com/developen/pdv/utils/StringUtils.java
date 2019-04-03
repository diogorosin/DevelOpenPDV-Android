package br.com.developen.pdv.utils;

import android.text.format.DateFormat;
import android.util.Log;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;

public class StringUtils {

    private static final DecimalFormatSymbols symbols;

    private static final DecimalFormat decimalFormatWithSymbol;

    private static final DecimalFormat decimalFormatOfQuantity;

    private static final DecimalFormat decimalFormat;

    private static final DateFormat dateFormat;

    static{

        symbols = new DecimalFormatSymbols();

        symbols.setDecimalSeparator(',');

        symbols.setGroupingSeparator('.');

        symbols.setCurrencySymbol("R$");

        decimalFormat = new DecimalFormat("###,###,###,###.##", symbols);

        decimalFormat.setMaximumFractionDigits(2);

        decimalFormat.setMinimumFractionDigits(2);

        decimalFormatWithSymbol = new DecimalFormat("R$ ###,###,###,###.##", symbols);

        decimalFormatWithSymbol.setMaximumFractionDigits(2);

        decimalFormatWithSymbol.setMinimumFractionDigits(2);

        decimalFormatOfQuantity = new DecimalFormat("###,###,###,###.###", symbols);

        dateFormat = new DateFormat();

    }

    public static String formatCurrencyWithSymbol(Double currency){

        return decimalFormatWithSymbol.format(currency);

    }

    public static String formatCurrency(Double currency){

        return decimalFormat.format(currency);

    }

    public static String formatQuantity(Double currency){

        decimalFormatOfQuantity.setMinimumFractionDigits(0);

        decimalFormatOfQuantity.setMaximumFractionDigits(3);

        return decimalFormatOfQuantity.format(currency);

    }

    public static String formatPercentage(Double currency){

        decimalFormatOfQuantity.setMinimumFractionDigits(0);

        decimalFormatOfQuantity.setMaximumFractionDigits(2);

        return decimalFormatOfQuantity.format(currency);

    }

    public static String formatPercentageWithMinimumFractionDigit(Double currency){

        decimalFormatOfQuantity.setMinimumFractionDigits(3);

        decimalFormatOfQuantity.setMaximumFractionDigits(3);

        return decimalFormatOfQuantity.format(currency);

    }

    public static String formatQuantityWithMinimumFractionDigit(Double currency){

        decimalFormatOfQuantity.setMinimumFractionDigits(3);

        decimalFormatOfQuantity.setMaximumFractionDigits(3);

        return decimalFormatOfQuantity.format(currency);

    }

    public static String formatDate(Date dateTime){

        return dateFormat.format("dd/MM/yyyy", dateTime).toString();

    }

    public static String formatDateTime(Date dateTime){

        return dateFormat.format("dd/MM/yyyy HH:mm:ss", dateTime).toString();

    }

    public static String formatShortDateTime(Date dateTime){

        return dateFormat.format("dd/MM HH:mm", dateTime).toString();

    }

    public static String formatShortTime(Date dateTime){

        return dateFormat.format("HH'h'mm'min'", dateTime).toString();

    }

    public static String leftPad(String string, Integer size, Character character){

        String result = string;

        if (string.length() > size)

            result = string.substring(0, size - 1);

        else

            while (result.length() < size)

                result = character + result;

        return result;

    }

    public static String rightPad(String string, Integer size, Character character){

        String result = string;

        if (string.length() > size)

            result = string.substring(0, size - 1);

        else

            while (result.length() < size)

                result = result + character;

        return result;

    }

    public static String digestString(String password){

        String digestedString = "";

        try {

            MessageDigest alg = MessageDigest.getInstance("SHA-256");

            byte messageDigest[] = alg.digest(password.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();

            for (byte b : messageDigest)

                hexString.append(String.format("%02X", 0xFF & b));

            digestedString = hexString.toString();

        }catch(Exception e){

            Log.e(StringUtils.class.getSimpleName(), e.getMessage());

        }

        return digestedString;

    }

    public static String getDenominationOfLevel(int level){

        String result;

        switch (level){

            case Constants.UNDEFINED_SUBJECT_LEVEL: result = "Bloqueado";
                break;

            case Constants.CUSTOMER_SUPPLIER_SUBJECT_LEVEL: result = "Cliente/Fornecedor";
                break;

            case Constants.CASHIER_SUBJECT_LEVEL: result = "Operador de Caixa";
                break;

            case Constants.MANAGER_SUBJECT_LEVEL: result = "Gerente";
                break;

            case Constants.SUPERVISOR_SUBJECT_LEVEL: result = "Supervisor";
                break;

            case Constants.OWNER_PARTNER_SUBJECT_LEVEL: result = "Sócio/Proprietário";
                break;

            case Constants.SUPPORT_ANALYST_SUBJECT_LEVEL: result = "Analista de Suporte";
                break;

            case Constants.DEVELOPER_SUBJECT_LEVEL: result = "Desenvolvedor";
                break;

            default: result = "Indefinido";

        }

        return result;

    }


    public static String getDenominationOfSaleStatus(String status){

        String result;

        switch (status){

            case Constants.OPENED_SALE_STATUS: result = "Em aberto";
                break;

            case Constants.FINISHED_SALE_STATUS: result = "Finalizada";
                break;

            case Constants.CANCELED_SALE_STATUS: result = "Cancelada";
                break;

            default: result = "Indefinido";

        }

        return result;

    }


    public static String getDenominationOfCashOperation(String operation){

        String result;

        switch (operation){

            case Constants.CHANGE_CASH_OPERATION: result = "Troco";
                break;

            case Constants.OPEN_CASH_OPERATION: result = "Abertura";
                break;

            case Constants.CLOSE_CASH_OPERATION: result = "Fechamento";
                break;

            case Constants.SUPPLY_CASH_OPERATION: result = "Complemento";
                break;

            case Constants.REMOVAL_CASH_OPERATION: result = "Sangria";
                break;

            case Constants.RECEIPT_CASH_OPERATION: result = "Recebimento";
                break;

            case Constants.REVERSAL_CASH_OPERATION: result = "Estorno";
                break;

            default: result = "Indefinido";

        }

        return result;

    }


    public static String getDenominationOfCashType(String type){

        String result;

        switch (type){

            case Constants.OUT_CASH_TYPE: result = "Saída";
                break;

            case Constants.ENTRY_CASH_TYPE: result = "Entrada";
                break;

            default: result = "Indefinido";

        }

        return result;

    }

}
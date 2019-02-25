package br.com.developen.pdv.room;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimestampConverter {

    public static final String TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static DateFormat df = new SimpleDateFormat(TIME_STAMP_FORMAT);

    @TypeConverter
    public static Date fromTimestamp(String value) {

        if (value != null) {

            try {

                TimeZone timeZone = TimeZone.getTimeZone("UTC");

                df.setTimeZone(timeZone);

                return df.parse(value);

            } catch (ParseException e) {

                e.printStackTrace();

            }

            return null;

        } else {

            return null;

        }

    }

    @TypeConverter
    public static String dateToTimestamp(Date value) {

        TimeZone timeZone = TimeZone.getTimeZone("UTC");

        df.setTimeZone(timeZone);

        return value == null ? null : df.format(value);

    }

}
package javauction.util;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Time format for XML imports and exports used by xstream hibernate library
 */
public class DateXmlUtil extends AbstractSingleValueConverter {

    private final static String FORMAT = "MMM-dd-yy HH:mm:ss";

    public boolean canConvert(Class type) {
        return type.equals(Timestamp.class);     // Converter works only with Timestamps
    }

    @Override
    public Object fromString(String str) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT,  Locale.ENGLISH);
        try {
            Date date =  format.parse(str);
            return new Timestamp(date.getTime());  // we simply calculate days between using JodaTime
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString(Object source) {
        if (source == null) {
            return null;
        }

        return new SimpleDateFormat(FORMAT).format(source);
    }

}
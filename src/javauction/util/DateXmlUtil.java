package javauction.util;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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
            Timestamp time = new Timestamp(date.getTime());
            return time;  // we simply calculate days between using JodaTime
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

        String s = new SimpleDateFormat(FORMAT).format(source);
        return s;
    }
//    public void marshal(Object source, HierarchicalStreamWriter writer,
//                        MarshallingContext context) {
//        String S = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss").format(source);
//        writer.setValue(S   );
//    }
//
//    public Object unmarshal(HierarchicalStreamReader reader,
//                            UnmarshallingContext context) {
////        String money = new String(reader.getValue());
////        money = money.replace("$", "");
//        return "";
//    }
//
//    public boolean canConvert(Class type) {
//        return type.equals(Timestamp.class);
//    }
}
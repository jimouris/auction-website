package javauction.util;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.util.Objects;

/**
 * Money format for XML imports and exports used by xstream hibernate library
 * Convert amount to USD and vise versa
 */
public class MoneyXmlUtil extends AbstractSingleValueConverter {

    public boolean canConvert(Class type) {
        return type.equals(Double.class);     // Converter works only with Integers
    }

    @Override
    public Object fromString(String str) {
        Double money = -1.0;
        try {
            money = Double.valueOf(str.replace("$", "").replace(",", "."));
        } catch (Exception e){
            e.printStackTrace();
        }
        return money;
    }
    
    @Override
    public String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return "$" + Objects.toString(obj);
    }

}
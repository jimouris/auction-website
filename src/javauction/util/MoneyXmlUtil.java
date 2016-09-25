package javauction.util;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.util.Objects;

public class MoneyXmlUtil extends AbstractSingleValueConverter {

    public boolean canConvert(Class type) {
        return type.equals(Double.class);     // Converter works only with Integers
    }

    @Override
    public Object fromString(String str) {
        Double money = -1.0;
        try{
            money = Double.valueOf(str.replace("$", "").replace(",", "."));
        } catch( Exception e){
            System.out.println(e);
        }
        if (money == -1.0){
            System.out.println("shit happened");
        }
        return money;
    }
    
    @Override
    public String toString(Object obj) {
        if (obj == null) {
            return null;
        }

        String money = new String("$" + Objects.toString(obj));
        return money;
    }
}
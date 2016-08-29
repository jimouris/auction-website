package javauction.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Objects;

public class MoneyXmlUtil implements Converter {

    public void marshal(Object source, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        String money = Objects.toString(source, null);
        writer.setValue("$" + money);
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
        String money = new String(reader.getValue());
        money = money.replace("$", "");
        return money;
    }

    public boolean canConvert(Class type) {
        return type.equals(Double.class);
    }
}
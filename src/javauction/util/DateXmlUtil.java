package javauction.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateXmlUtil implements Converter {

    public void marshal(Object source, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        String S = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss").format(source);
        writer.setValue(S   );
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
//        String money = new String(reader.getValue());
//        money = money.replace("$", "");
        return "";
    }

    public boolean canConvert(Class type) {
        return type.equals(Timestamp.class);
    }
}
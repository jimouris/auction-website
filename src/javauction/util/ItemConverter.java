package javauction.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import javauction.model.AuctionEntity;
import javauction.model.UserEntity;

public class ItemConverter implements Converter {

    public boolean canConvert(Class clazz) {
        return clazz.equals(AuctionEntity.class);
    }

    // used to change the java object to xml
    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        // do nothing
    }

    //
    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
        UserEntity user = new UserEntity();

        return user;
    }

}
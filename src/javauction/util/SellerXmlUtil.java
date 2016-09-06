package javauction.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import javauction.model.UserEntity;

/**
 * Created by gpelelis on 4/9/2016.
 */
public class SellerXmlUtil implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext marshallingContext) {
        UserEntity seller = (UserEntity) o;
        writer.addAttribute("UserID", seller.getUsername());
        // this will compute the sum of ratings of a user as seller
        Byte isSeller = 1;
        seller.setSumRating(isSeller);
        writer.addAttribute("Rating", String.valueOf(seller.getSumRating()));

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return null;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(UserEntity.class);
    }
}

package javauction.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import javauction.controller.PasswordAuthentication;
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
        seller.setRatingAs("seller");
        writer.addAttribute("Rating", String.valueOf(seller.getRatingAsSeller()));

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext unmarshallingContext) {
        // create a user
        String username, country;
        country = "Greece";

        username = reader.getAttribute("UserID");
        String city = "Athens";
        String password = "123456";
        String name = "demo";
        String lastname = "user";
        String email = username + "@email.com";
        String phonenumber = "12312341234";
        String vat = " ";
        String homeaddress = " ";
        String lat = "";
        String longi = "";
        byte[] salt = PasswordAuthentication.genSalt();
        byte[] hash = PasswordAuthentication.hash(password.toCharArray(), salt);

        UserEntity user = new UserEntity(username, hash, salt, name, lastname, email, phonenumber, vat, homeaddress, lat, longi, city, country);
        user.setIsApproved((byte) 1);

        // return that
        return user;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(UserEntity.class);
    }
}

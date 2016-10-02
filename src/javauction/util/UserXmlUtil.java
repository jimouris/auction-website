package javauction.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import javauction.controller.PasswordAuthentication;
import javauction.model.UserEntity;

/**
 * User format for XML imports and exports used by xstream hibernate library
 */
public class UserXmlUtil implements Converter {

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
        country = "Ελλάδα";

        username = reader.getAttribute("UserID");
        String city = "Αθήνα";
        String password = "123456";
        String name = username;
        String lastname = username;
        String email = username + "@email.com";
        String phonenumber = "12312341234";
        String vat = "12345";
        String homeaddress = "Αθήνα";
        String lat = "37.968196";
        String longi = "23.77868710000007";
        byte[] salt = PasswordAuthentication.genSalt();
        byte[] hash = PasswordAuthentication.hash(password.toCharArray(), salt);

        UserEntity user = new UserEntity(username, hash, salt, name, lastname, email, phonenumber, vat, homeaddress, lat, longi, city, country);
        user.setIsApproved((byte) 1);
        return user;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(UserEntity.class);
    }

}

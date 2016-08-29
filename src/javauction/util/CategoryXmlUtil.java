package javauction.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import javauction.model.CategoryEntity;

public class CategoryXmlUtil implements Converter {

    public void marshal(Object source, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        CategoryEntity category = (CategoryEntity) source;
        writer.setValue(category.getCategoryName());
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
        CategoryEntity category = new CategoryEntity();
        category.setCategoryName(new String(reader.getValue()));
        return category;
    }

    public boolean canConvert(Class type) {
        return type.equals(CategoryEntity.class);
    }
}
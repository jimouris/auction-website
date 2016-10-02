package javauction.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import javauction.model.CategoryEntity;

/**
 * Category format for XML imports and exports used by xstream hibernate library
 */
public class CategoryXmlUtil implements Converter {

    public void marshal(Object source, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        CategoryEntity category = (CategoryEntity) source;
        writer.setValue(category.getCategoryName());
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return new CategoryEntity(reader.getValue());
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(CategoryEntity.class);
    }

}
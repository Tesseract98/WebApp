package server.sax.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class ReflectionHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionHelper.class);

    public static Object createInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            LOGGER.info("ReflectionHelper createInstance(), {0}", e.getCause());
        }
        return null;
    }

    public static void setFieldValue(Object object, String fieldName, String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            Types types = Types.getType(field.getType());
            switch (types) {
                case BYTE:
                    field.set(object, Byte.valueOf(value));
                    break;
                case BOOLEAN:
                    field.set(object, Boolean.valueOf(value));
                    break;
                case SHORT:
                    field.set(object, Short.valueOf(value));
                    break;
                case CHAR:
                    field.set(object, value.charAt(0));
                    break;
                case INT:
                    field.set(object, Integer.decode(value));
                    break;
                case FLOAT:
                    field.set(object, Float.valueOf(value));
                    break;
                case LONG:
                    field.set(object, Long.valueOf(value));
                    break;
                case DOUBLE:
                    field.set(object, Double.valueOf(value));
                    break;
                case STRING:
                    field.set(object, value);
            }
            field.setAccessible(false);
        } catch (Exception exc) {
            LOGGER.info("ReflectionHelper setFieldValue(), {0}", exc.getCause());
        }
    }

}

package server.sax.reflection;

public enum Types {
    CHAR,
    INT,
    FLOAT,
    LONG,
    DOUBLE,
    STRING,
    BYTE,
    BOOLEAN,
    SHORT;

    public static Types getType(Class<?> clazz) {
        String className = clazz.getSimpleName().toUpperCase();
        return Types.valueOf(className);
    }

}

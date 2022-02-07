package ru.clevertec.parser.impl;

import lombok.SneakyThrows;
import ru.clevertec.parser.Parseable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class JSONParser implements Parseable {

    @SneakyThrows
    private String choseParseWithField(Field field, Object o) {
        if (field.getType().isPrimitive()) {
            return _parsePrimitiveAndWrappers(field, o);
        } else if (field.getType().isArray()) {
            return _parseArray(field, o);
        } else if (Number.class.isAssignableFrom(field.getType())) {
            return _parsePrimitiveAndWrappers(field, o);
        } else if (String.class.isAssignableFrom(field.getType())) {
            return _parseString(field, o);
        } else if (Iterable.class.isAssignableFrom(field.getType())) {
            return _parseList(field, o);
        } else if (Map.class.isAssignableFrom(field.getType())) {
            return _parseMap(field, o);
        } else {
            return _parseObject(field, o);
        }

    }

    @SneakyThrows
    private String _parsePrimitiveAndWrappers(Field field, Object o) {
        if (char.class.isAssignableFrom(field.getType())) {
            return "\"" + field.getName() + "\"" + ":\"" + field.get(o) + "\",";
        }
        return "\"" + field.getName() + "\"" + ":" + field.get(o) + ",";
    }

    @SneakyThrows
    private String _parseArray(Field field, Object o) {
        Object[] array2 = new Object[Array.getLength(field.get(o))];
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < array2.length; i++) {
            array2[i] = Array.get(field.get(o), i);
        }
        for (Object o1 : array2) {
            res.append(__choseParse(o1)).append(",");
        }

        return "\"" + field.getName() + "\":" + res.deleteCharAt(res.length() - 1).append("],");
    }

    @SneakyThrows
    private String _parseMap(Field field, Object o) {
        Map<?, ?> map = ((Map<?, ?>) field.get(o));
        StringBuilder str = new StringBuilder("{");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            str.append(__choseParse(entry.getKey())).append(":");
            str.append(__choseParse(entry.getValue())).append(",");
        }

        return "\"" + field.getName() + "\":" + str.deleteCharAt(str.length() - 1).append("},");
    }

    @SneakyThrows
    private String _parseList(Field field, Object list) {
        StringBuilder res = new StringBuilder("[");
        List<?> listOfObjects = ((List<?>) field.get(list));
        for (Object o1 : listOfObjects) {
            res.append(__choseParse(o1)).append(",");
        }
        return "\"" + field.getName() + "\":" + res.deleteCharAt(res.length() - 1).append("],");
    }

    @SneakyThrows
    private String _parseString(Field field, Object o) {
        Object o1 = field.get(o);
        if (o1 == null) {
            return "\"" + field.getName() + "\":" + null + ",";
        }
        return "\"" + field.getName() + "\":" + "\"" + field.get(o) + "\",";
    }

    @SneakyThrows
    private String _parseObject(Field field, Object o) {
        if (o == null) {
            return null;
        }

        Object o1 = field.get(o);
        StringBuilder result = new StringBuilder("{");
        Class<?> oClass = o1.getClass();
        Field[] declaredFields = oClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            result.append(choseParseWithField(declaredField, o1));

            declaredField.setAccessible(false);
        }
        result.deleteCharAt(result.length() - 1);

        return "\"" + field.getName() + "\":" + result + "},";
    }

    @Override
    public String parseToString(Object o) {
        if (o == null) {
            return null;
        }
        StringBuilder result = new StringBuilder("{");
        Class<?> oClass = o.getClass();
        Field[] declaredFields = oClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            result.append(choseParseWithField(declaredField, o));

            declaredField.setAccessible(false);
        }

        return result.deleteCharAt(result.length() - 1) + "}";
    }

    @SneakyThrows
    private String __parsePrimitiveAndWrappers(Object o) {
        return o + "";
    }

    @SneakyThrows
    private String __parseArray(Object o) {
        Object[] array2 = new Object[Array.getLength(o)];
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < array2.length; i++) {
            array2[i] = Array.get(o, i);
        }
        for (Object o1 : array2) {
            res.append(__choseParse(o1)).append(",");
        }

        return res.deleteCharAt(res.length() - 1).append("]").toString();
    }

    @SneakyThrows
    private String __parseMap(Object o) {
        Map<?, ?> map = ((Map<?, ?>) o);
        StringBuilder str = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            str.append(__choseParse(entry.getKey())).append(":");
            str.append(__choseParse(entry.getValue())).append(",");
        }

        return str.deleteCharAt(str.length() - 1).append("}").toString();
    }

    @SneakyThrows
    private String __parseList(Object o) {
        StringBuilder res = new StringBuilder("[");
        List<?> list = ((List<?>) (o));
        for (Object o1 : list) {
            res.append("\"").append(__choseParse(o1)).append(",");
        }
        return res.deleteCharAt(res.length() - 1).append("]").toString();
    }

    @SneakyThrows
    private String __parseString(Object o) {
        return "\"" + o + "\"";
    }

    @SneakyThrows
    public String __parseObject(Object o) {
        if (o == null) {
            return null;
        }

        StringBuilder result = new StringBuilder("{");
        Class<?> oClass = o.getClass();
        Field[] declaredFields = oClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            result.append(choseParseWithField(declaredField, o));

            declaredField.setAccessible(false);
        }
        result.deleteCharAt(result.length() - 1);

        return result + "}";
    }

    @SneakyThrows
    private String __choseParse(Object o) {
        if (o.getClass().isPrimitive()) {
            return __parsePrimitiveAndWrappers(o);
        } else if (o.getClass().isArray()) {
            return __parseArray(o);
        } else if (Number.class.isAssignableFrom(o.getClass())) {
            return __parsePrimitiveAndWrappers(o);
        } else if (String.class.isAssignableFrom(o.getClass())) {
            return __parseString(o);
        } else if (Iterable.class.isAssignableFrom(o.getClass())) {
            return __parseList(o);
        } else if (Map.class.isAssignableFrom(o.getClass())) {
            return __parseMap(o);
        } else {
            return __parseObject(o);
        }
    }

}

package com.astrider.sfc.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.astrider.sfc.helper.annotation.Column;
import com.astrider.sfc.model.vo.BaseVo;

public class Mapper<T extends BaseVo> {
    private Class<T> type;

    @SuppressWarnings("unchecked")
    public Mapper(T... t) {
        this.type = (Class<T>) t.getClass().getComponentType();
    }

    /*
     * Method for DBs
     */
    public T fromResultSet(ResultSet rs) {
        T vo = getNewInstance();
        mapResultSet(rs, vo);
        return vo;
    }

    /*
     * Methods for HttpServletRequests
     */
    public T fromHttpRequest(HttpServletRequest request) {
        T vo = getNewInstance();
        HashMap<Field, String> parameters = extractRequestParameters(request, vo);
        mapRequestParameters(parameters, vo);
        return vo;
    }

    private T getNewInstance() {
        T obj = null;
        try {
            obj = type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private void mapResultSet(ResultSet rs, T vo) {
        Field[] declaredFields = vo.getClass().getDeclaredFields();

        for (Field f : declaredFields) {
            Column column = f.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            try {
                Method method = ResultSet.class.getMethod(getGetterName(f), String.class);
                f.setAccessible(true);
                f.set(vo, method.invoke(rs, column.value()));
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private String getGetterName(Field f) {
        String type = f.getType().getSimpleName();
        // ResultSet has no method getInteger()
        if (type.equals("Integer"))
            type = "Int";

        return "get" + StringUtils.toCamelCase(type);
    }

    private HashMap<Field, String> extractRequestParameters(HttpServletRequest request, T vo) {
        HashMap<Field, String> parameters = new HashMap<Field, String>();
        Field[] declaredFields = vo.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            Column column = f.getAnnotation(Column.class);
            if (column == null)
                continue;
            String parameter = request.getParameter(column.value());
            parameters.put(f, parameter);
        }
        return parameters;
    }

    private void mapRequestParameters(HashMap<Field, String> parameters, T vo) {
        for (Field f : parameters.keySet()) {
            String value = parameters.get(f);
            Type t = f.getType();
            f.setAccessible(true);
            try {
                if (t == String.class)
                    f.set(vo, value);
                if (t == short.class || t == Short.class)
                    f.set(vo, Short.valueOf(value));
                if (t == int.class || t == Integer.class)
                    f.set(vo, Integer.valueOf(value));
                if (t == long.class || t == Long.class)
                    f.set(vo, Long.valueOf(value));
                if (t == float.class || t == Float.class)
                    f.set(vo, Float.valueOf(value));
                if (t == double.class || t == Double.class)
                    f.set(vo, Double.valueOf(value));
                if (t == boolean.class || t == Boolean.class)
                    f.set(vo, Boolean.valueOf(value));
            } catch (NumberFormatException e) {
                continue;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

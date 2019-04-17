package edu.fudan.common.util;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    /**
     * <p>
     * 对象转JSON字符串
     * </p>
     */
    public static String object2Json(Object obj) {
        String result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Map object2Map(Object obj) {
        String object2Json = object2Json(obj);
        Map<?, ?> result = jsonToMap(object2Json);
        return result;
    }

    /**
     * <p>
     * JSON字符串转Map对象
     * </p>
     */
    public static Map<?, ?> jsonToMap(String json) {
        return json2Object(json, Map.class);
    }

    /**
     * <p>
     * JSON转Object对象
     * </p>
     */
    public static <T> T json2Object(String json, Class<T> cls) {
        T result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static <T> T conveterObject(Object srcObject, Class<T> destObjectType) {
        String jsonContent = object2Json(srcObject);
        return json2Object(jsonContent, destObjectType);
    }
}

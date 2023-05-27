package com.example.admin.presentation.internalmodel;


import com.example.library.utils.JsonHelper;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class Response {

    public static final String KEY_STATUS = "status";
    public static final String KEY_ERROR = "error";
    public static final String KEY_DATA = "data";

    private final Map<String, Object> data = new HashMap<>();

    private Response() {
    }

    @NotNull
    public static Response ok() {
        Response response = new Response();
        response.data.put(KEY_STATUS, HttpStatus.OK);
        return response;
    }

    @NotNull
    public static Response error(HttpStatus status, String error) {
        Response response = new Response();
        response.data.put(KEY_STATUS, status);
        response.data.put(KEY_ERROR, error);
        return response;
    }

    @NotNull
    public static Response data(Object data) {
        return ok().put(KEY_DATA, data);
    }

    public Response put(String key, Object val) {
        data.put(key, val);
        return this;
    }

    public Map<String, Object> body() {
        return data;
    }

    public String json(){
        return JsonHelper.objToJson(data);
    }

}

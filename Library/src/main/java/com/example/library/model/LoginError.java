package com.example.library.model;

import com.example.library.utils.JsonHelper;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LoginError {
    private String error;
    private String username;

    public LoginError() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String encode(LoginError error) {
        return Base64.getEncoder().encodeToString(JsonHelper.objToJson(error).getBytes(StandardCharsets.UTF_8));
    }

    @NotNull
    public static LoginError decode(String data) {
        try {
            LoginError err = JsonHelper.jsonToObj(new String(Base64.getDecoder().decode(data)), LoginError.class);
            if (err == null) {
                throw new RuntimeException();
            }
            return err;
        } catch (Exception e) {
            return new LoginError();
        }
    }
}

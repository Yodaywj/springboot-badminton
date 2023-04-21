package com.ywj.badminton.utils;

import java.util.HashMap;

public class ResultMessage extends HashMap<String, Object> {

    public static ResultMessage success(String message){
        ResultMessage response = new ResultMessage();
        response.setResult(Boolean.TRUE);
        response.setMessage(message);
        return response;
    }

    public static ResultMessage failure(String message){
        ResultMessage response = new ResultMessage();
        response.setResult(Boolean.FALSE);
        response.setMessage(message);
        return response;
    }

    public static ResultMessage data(String name,Object data){
        ResultMessage response = new ResultMessage();
        response.setOther(name,data);
        return response;
    }

    public void setResult(Boolean success) {
        if (success != null) put("result", success);
    }

    public void setMessage(String message) {
        if (message != null) put("message", message);
    }

    public void setOther(String key, Object value) {
        if (key != null && value != null) put(key, value);
    }
}

package com.mervyn.springboot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by mengran.gao on 2017/7/19.
 */
public class Test {

    public static void main(String[] args) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openId","aaaaa");
        jsonObject.put("roles",Arrays.asList("aaa", "bbb"));
        jsonObject.put("invokeMethod",".sysShopInfo");
        System.out.println(jsonObject.toString());
    }
}

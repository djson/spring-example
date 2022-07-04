package rest.api.sample.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import rest.api.sample.data.DataJsonClass;

@Service
public class ApiDataService {

    public List<DataJsonClass> getData1(HashMap<String, Object> param) {
        System.out.println(
                "File: ApiDataService.java ~ line: (18) ~ function: List<DataJsonClass>getData1 ---> param: " + param);
        Date date = new Date();
        List<DataJsonClass> result = new ArrayList<DataJsonClass>();
        try {
            for (int i = 1; i <= 100; i++) {
                DataJsonClass d = new DataJsonClass();
                d.setUserId("user" + i);
                d.setUserName("유저" + i);
                d.setUserEmail("user" + i + "@user.net");
                d.setUserAge(10 * i);
                d.setUserAddress("서울시 낙원구 행복동 " + i + "길 ");
                d.setUserEnterDate(date);
                result.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getData2() {
        Date date = new Date();
        JsonArray ja = new JsonArray();
        try {
            for (int i = 1; i <= 100; i++) {
                JsonObject jo = new JsonObject();
                jo.addProperty("userId", "user" + i);
                jo.addProperty("userName", "유저" + i);
                jo.addProperty("userEmail", "user" + i + "@user.net");
                jo.addProperty("userAge", 10 * i);
                jo.addProperty("userAddress", "서울시 낙원구 행복동 " + i + "길 ");
                jo.addProperty("userEnterDate", date.toString());
                ja.add(jo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja.toString();
    }
}

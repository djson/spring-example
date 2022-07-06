package rest.api.sample.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import rest.api.sample.data.DataJsonClass;
import rest.api.sample.repository.userRepo;

/**
 * @apiNote Service Sample
 * @version 1.0
 * @author DK
 * @since 2022.07
 */
@Service
public class ApiDataService {

    @Autowired
    userRepo repo;

    public List<DataJsonClass> getData1(HashMap<String, Object> param) {
        return repo.getList(param);
    }

    public String getData2(HashMap<String, Object> param) {
        JsonArray ja = new JsonArray();
        List<DataJsonClass> list = repo.getList(param);
        for (int i = 0; i < list.size(); i++) {
            JsonObject jo = new JsonObject();
            jo.addProperty("userId", list.get(i).getUserId());
            jo.addProperty("userName", list.get(i).getUserName());
            jo.addProperty("userEmail", list.get(i).getUserEmail());
            jo.addProperty("userAge", list.get(i).getUserAge());
            jo.addProperty("userAddress", list.get(i).getUserAddress());
            jo.addProperty("userEnterDate", list.get(i).getUserEnterDate().toString());
            ja.add(jo);
        }
        return ja.toString();
    }
}

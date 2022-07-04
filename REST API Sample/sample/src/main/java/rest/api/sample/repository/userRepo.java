package rest.api.sample.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import rest.api.sample.data.DataJsonClass;

@Repository
public interface userRepo {
    public List<DataJsonClass> getList(HashMap<String, Object> param);
}

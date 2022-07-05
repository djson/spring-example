package rest.api.sample.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import rest.api.sample.data.DataJsonClass;

/**
 * @apiNote Repository Sample
 * @version 1.0
 * @author DK
 * @since 2022.07
 */
@Repository
public interface userRepo {
    public List<DataJsonClass> getList(HashMap<String, Object> param);
}

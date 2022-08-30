package com.sample.demo.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.demo.repository.SampleRepo;

@Service
public class SampleService {

    @Autowired
    SampleRepo repo;

    public HashMap<String, Object> getUser() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("list", repo.getUser());
        return result;
    }
}

package com.sample.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sample.demo.data.SampleUser;

@Repository
public interface SampleRepo {
    List<SampleUser> getUser();
}

package rest.api.sample.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.api.sample.response.ApiResFormat;

@RestController
public class UserController {

    @GetMapping(value = "/users", produces = "application/json; charset=utf8")
    public ResponseEntity<ApiResFormat> getUsers() {
        System.out.println("get get users");
        return new ResponseEntity<ApiResFormat>(new ApiResFormat("success", "200", "요청에 성공하였습니다.", null),
                HttpStatus.OK);
    }

    @PostMapping(value = "/users/", produces = "application/json; charset=utf8")
    public void postUsers() {
        System.out.println("post get users");
    }

    @PutMapping(value = "/users/save", produces = "application/json; charset=utf8")
    public void updateUsers() {
        System.out.println("put save users");
    }

    @DeleteMapping(value = "/users/delete", produces = "application/json; charset=utf8")
    public void deleteUsers() {
        System.out.println("delete del users");
    }
}

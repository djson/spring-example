package rest.api.sample.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.api.sample.response.ApiResFormat;
import rest.api.sample.service.ApiDataService;

@RestController
public class UserController {

    @Autowired
    ApiDataService a;

    @GetMapping(value = "/users", produces = "application/json; charset=utf8")
    public ResponseEntity<ApiResFormat> getUsers(HttpServletRequest request,
            @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<ApiResFormat>(new ApiResFormat("success", "200", "요청에 성공하였습니다.", a.getData1(param)),
                HttpStatus.OK);
    }

    @PostMapping(value = "/users", produces = "application/json; charset=utf8")
    public ResponseEntity<ApiResFormat> postUsers(HttpServletRequest request,
            @RequestBody HashMap<String, Object> param) {
        System.out.println("post get users");
        return new ResponseEntity<ApiResFormat>(new ApiResFormat("success", "200", "요청에 성공하였습니다.", a.getData2(param)),
                HttpStatus.OK);
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

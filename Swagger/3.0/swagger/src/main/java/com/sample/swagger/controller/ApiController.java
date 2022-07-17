package com.sample.swagger.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.swagger.data.data;
import com.sample.swagger.data.dataParam;
import com.sample.swagger.data.dataRes;
import com.sample.swagger.response.ApiErr;
import com.sample.swagger.response.ApiNoAuth;
import com.sample.swagger.response.ApiRes;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "user", description = "get user list")
@RestController
@RequestMapping("/api")
public class ApiController {

    @Tag(name = "user")
    @ApiOperation(value = "유저 리스트", notes = "파라미터로 넘어온 수 만큼 유저를 리턴한다.", authorizations = {
            @Authorization(value = "apiKey") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(schema = @Schema(implementation = ApiRes.class))),
            @ApiResponse(responseCode = "201", description = "500과 동일"),
            @ApiResponse(responseCode = "401", description = "권한 없음(키누락)", content = @Content(schema = @Schema(implementation = ApiNoAuth.class))),
            @ApiResponse(responseCode = "403", description = "500과 동일"),
            @ApiResponse(responseCode = "404", description = "500과 동일"),
            @ApiResponse(responseCode = "500", description = "요청 실패", content = @Content(schema = @Schema(implementation = ApiErr.class)))
    })
    @GetMapping(value = "/getUser", produces = "application/json")
    public ResponseEntity<ApiRes<dataRes>> getUser(@RequestParam int num) {
        dataRes res = new dataRes();
        List<data> result = new ArrayList<data>();
        for (int i = 0; i < num; i++) {
            data d = new data();
            d.setUserNo(i);
            d.setUsername("user" + i);
            result.add(d);
        }
        res.setDataList(result);
        return new ResponseEntity<ApiRes<dataRes>>(new ApiRes<dataRes>(res), HttpStatus.OK);
    }

    @Tag(name = "user")
    @ApiOperation(value = "유저 리스트", notes = "파라미터로 넘어온 수 만큼 유저를 리턴한다.", authorizations = {
            @Authorization(value = "apiKey") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(schema = @Schema(implementation = ApiRes.class))),
            @ApiResponse(responseCode = "201", description = "500과 동일"),
            @ApiResponse(responseCode = "401", description = "권한 없음(키누락)", content = @Content(schema = @Schema(implementation = ApiNoAuth.class))),
            @ApiResponse(responseCode = "403", description = "500과 동일"),
            @ApiResponse(responseCode = "404", description = "500과 동일"),
            @ApiResponse(responseCode = "500", description = "요청 실패", content = @Content(schema = @Schema(implementation = ApiErr.class)))
    })
    @PostMapping(value = "/getUser", produces = "application/json")
    public ResponseEntity<ApiRes<dataRes>> postUser(@RequestBody dataParam param) {
        dataRes res = new dataRes();
        List<data> result = new ArrayList<data>();
        for (int i = 0; i < param.getNum(); i++) {
            data d = new data();
            d.setUserNo(i);
            d.setUsername("user" + i);
            result.add(d);
        }
        res.setDataList(result);
        return new ResponseEntity<ApiRes<dataRes>>(new ApiRes<dataRes>(res), HttpStatus.OK);
    }
}
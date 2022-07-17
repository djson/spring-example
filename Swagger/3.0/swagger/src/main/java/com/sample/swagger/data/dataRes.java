package com.sample.swagger.data;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "dataRes", description = "유저 조회 응답 데이터")
@Getter
@Setter
public class dataRes {
    @Schema(description = "유저 리스트", defaultValue = "", allowableValues = {}, example = "")
    private List<data> dataList;
}

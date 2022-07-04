package rest.api.sample.response;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResFormat {
    // result_status -> fail / success
    private String result_status;
    // result_code -> 200 / 4XX / 5XX
    private int result_code;
    // result_message -> NOT ACCEPTED , EXCEPTION ...
    private String result_message;
    // result -> result obj
    private HashMap<String, Object> result = new HashMap<String, Object>();

    public ApiResFormat() {

    }

    public ApiResFormat(String status, int resultCode, String resultMessage, Object resultObj) {
        this.result_status = status;
        this.result_code = resultCode;
        this.result_message = resultMessage;
        result.put("result", resultObj);
    }

    public ApiResFormat(int resultCode, Object resultObj) {
        this.result_status = "success";
        this.result_code = resultCode;
        this.result_message = "요청에 성공하였습니다.";
        result.put("result", resultObj);
    }

    public ApiResFormat(int resultCode, String resultMessage, Object resultObj) {
        this.result_status = "success";
        this.result_code = resultCode;
        this.result_message = resultMessage;
        result.put("result", resultObj);
    }

    public ApiResFormat(String resultMessage, Object resultObj) {
        this.result_status = "success";
        this.result_code = 200;
        this.result_message = resultMessage;
        result.put("result", resultObj);
    }

    public ApiResFormat(Object resultObj) {
        this.result_status = "success";
        this.result_code = 200;
        this.result_message = "요청에 성공하였습니다.";
        result.put("result", resultObj);
    }
}

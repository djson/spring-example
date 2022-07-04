package rest.api.sample.response;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResFormat {
    private String result_status;
    private String result_code;
    private String result_message;
    private HashMap<String, Object> result = new HashMap<String, Object>();

    public ApiResFormat() {

    }

    public ApiResFormat(String status, String resultCode, String resultMessage, Object resultObj) {
        this.result_status = status;
        this.result_code = resultCode;
        this.result_message = resultMessage;
        result.put("data", resultObj);
    }
}

package rest.api.sample.error;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

/**
 * @apiNote Error Handling
 * @version 1.0
 * @author DK
 */
@Component
public class RestResponseEntityExceptionHandler extends DefaultErrorAttributes {

    /**
     * @apiNote 에러 발생 시, repsonse 통일을 위한 메소드
     * @param webRequest
     * @param options
     * @return
     *         result_status = FAIL
     *         result_message = 잘못된 요청 URL 입니다.
     */
    @Override
    public Map<String, Object> getErrorAttributes(
            WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        errorAttributes.put("result_status", "fail");
        errorAttributes.put("result_code", errorAttributes.get("status"));
        errorAttributes.put("result_message", "NOT ACCEPTED");
        errorAttributes.put("response", null);
        errorAttributes.remove("timestamp");
        errorAttributes.remove("status");
        errorAttributes.remove("message");
        errorAttributes.remove("error");
        errorAttributes.remove("path");
        if (errorAttributes.containsKey("trace"))
            errorAttributes.remove("trace");
        return errorAttributes;
    }

}

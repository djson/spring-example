package rest.api.sample.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import rest.api.sample.response.ApiResFormat;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResFormat> globalException(Exception ex) {
        return new ResponseEntity<ApiResFormat>(new ApiResFormat("fail", 500, "요청에 실패하였습니다.", null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

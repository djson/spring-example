package rest.api.sample.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/* null이 아닌것만 return */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataJsonClass {

    private String userId;
    private String userName;
    private String userEmail;
    private int userAge;
    private String userAddress;
    /* date format 고정 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date userEnterDate;

}

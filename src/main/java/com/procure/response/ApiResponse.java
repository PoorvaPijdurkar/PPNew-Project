package com.procure.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.procure.ApplicationConstants;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime timestamp;

    protected String status;
    protected Integer code;
    protected String message;
    protected Object data;

    public static ApiResponse getSuccessResponse(Object data) {
        return new ApiResponse(LocalDateTime.now(), ApplicationConstants.SUCCESS, 200, ApplicationConstants.SUCCESS, data);
    }

    public static ApiResponse getSuccessResponse(Object data, HttpStatus code) {
        return new ApiResponse(LocalDateTime.now(), ApplicationConstants.SUCCESS, code.value(), ApplicationConstants.SUCCESS, data);
    }

    public static ApiResponse getErrorResponse(Integer code, String message) {
        return new ApiResponse(LocalDateTime.now(), ApplicationConstants.ERROR, code, message, null);
    }

    public static ApiResponse getErrorResponse(Integer code, String message, Object data) {
        return new ApiResponse(LocalDateTime.now(), ApplicationConstants.ERROR, code, message, data);
    }
}

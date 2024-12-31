package com.example.lab09.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private int code;
    private String message;
    private Object Object;
    public static ApiResponse of(int code, String message, Object data){
        return new ApiResponse(code, message, data);
    }

}

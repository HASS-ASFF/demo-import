package com.api.demoimport.message;

import lombok.Data;
import lombok.Getter;

@Data
public class ResponseMessage {
    private String message;
    public ResponseMessage(String message) {
        this.message = message;
    }
}

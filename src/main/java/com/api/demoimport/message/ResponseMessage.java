package com.api.demoimport.message;

import lombok.Data;
import lombok.Getter;

@Data
public class ResponseMessage {
    private String message;

    // methode pour gérer les messages des requetes HTTP (POST/GET...)
    public ResponseMessage(String message) {
        this.message = message;
    }
}

package com.api.demoimport.message;

import lombok.Data;
import lombok.Getter;

/**
 * Class representing a response message.
 */
@Data
public class ResponseMessage {
    private String message;

    // methode pour gérer les messages des requetes HTTP (POST/GET...)
    public ResponseMessage(String message) {
        this.message = message;
    }
}

package com.api.demoimport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceResponse<T> {

    private String status;
    private T singledata;
    private List<T> data;

    public ServiceResponse(String status, T savedpassage) {
        this.status = status;
        this.singledata = savedpassage;
    }

    public ServiceResponse(String status, List<T> listpassage) {
        this.status = status;
        this.data = listpassage;
    }
}
package com.api.demoimport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * A generic class representing a service response with status and data.
 * @param <T> The type of data contained in the response.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceResponse<T> {

    private String status;
    private T singledata;
    private List<T> data;

    /**
     * Constructor for creating a response with a single data item.
     * @param status The status of the response.
     * @param savedpassage The single data item.
     */
    public ServiceResponse(String status, T savedpassage) {
        this.status = status;
        this.singledata = savedpassage;
    }

    /**
     * Constructor for creating a response with a list of data items.
     * @param status The status of the response.
     * @param listpassage The list of data items.
     */
    public ServiceResponse(String status, List<T> listpassage) {
        this.status = status;
        this.data = listpassage;
    }
}
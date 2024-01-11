package org.example.event_management_system.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Response<T> {

    private String message;
    private T result;
    private List<ErrorMessage> errors;
    private Map<String, String> errorMap;
    private String error;
}

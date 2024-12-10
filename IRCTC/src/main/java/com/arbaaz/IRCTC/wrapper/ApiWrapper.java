package com.arbaaz.IRCTC.wrapper;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApiWrapper {

    private String message;
    private Object data;
}

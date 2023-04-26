package com.dabige.httpclient.enitiy;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class Book {
    private Integer id;
    private String name;
    private String type;
    private String description;
}

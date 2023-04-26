package com.dabige.annotation;

import lombok.Data;

@Data
public class User {
    @MyUser("大逼哥")
    private String name;
    @MyUser("age")
    private String age;


}

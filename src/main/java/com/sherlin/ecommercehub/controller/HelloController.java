package com.sherlin.ecommercehub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("/api/hello")
    public String hello(){
        return "EccomerceHub is Running!";
    }


}

package com.mzura.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestController {

    @RequestMapping(path="/test", method = RequestMethod.GET)
    public String[] testMethod(){
        return new String[]{"one", "two", "three", "four"};
    }
}

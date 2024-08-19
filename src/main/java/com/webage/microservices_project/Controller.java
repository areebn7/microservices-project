package com.webage.microservices_project;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
;

@RestController
public class Controller {
    
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
    
}

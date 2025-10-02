package com.example.hello_world_security_auth_manager_req_matcher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping(path="/hello")
    public String hello() {
        return "Hello World!";
    }
    @GetMapping(path="/report")
    public String report() {
        return "Report View";
    }
    @GetMapping(path="/manager")
    public String manager() {
        return "Manager View";
    }
    @GetMapping(path="/admin")
    public String admin() {
        return "Admin View";
    }
}

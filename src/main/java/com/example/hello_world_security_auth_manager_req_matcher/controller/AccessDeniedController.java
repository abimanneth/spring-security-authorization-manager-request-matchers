package com.example.hello_world_security_auth_manager_req_matcher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessDeniedController {

    @GetMapping(path="/access-denied")
    public String accessDenied() {
        return "403 - Access Denied";
    }
}

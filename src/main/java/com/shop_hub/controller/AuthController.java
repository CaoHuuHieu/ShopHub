package com.shop_hub.controller;

import org.springframework.web.bind.annotation.GetMapping;

public interface AuthController {

    @GetMapping("/login")
    String login();
}

package com.shop_hub.controller.impl;

import com.shop_hub.controller.AuthController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Override
    public String login() {
        return "Hello Auth";
    }
}

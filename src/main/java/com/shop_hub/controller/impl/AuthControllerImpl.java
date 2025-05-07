package com.shop_hub.controller.impl;

import com.shop_hub.controller.AuthController;
import com.shop_hub.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JwtUtils jwtUtil;


    @Override
    public String login() {
        System.out.println("sayhihi");
        return jwtUtil.generateTokenFromUsername("caohuuhieu");
    }
}

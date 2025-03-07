package com.shop_hub.exception;

public class ForbiddenException extends RuntimeException{

    public ForbiddenException() {
        super("FORBIDDEN");
    }

    public ForbiddenException(String msg) {
        super(msg);
    }
}

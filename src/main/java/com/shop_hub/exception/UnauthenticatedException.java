package com.shop_hub.exception;

public class UnauthenticatedException extends RuntimeException{

    public UnauthenticatedException(){
        super("Unauthenticated");
    }

}

package com.shop_hub.exception;

public class ServerException extends RuntimeException{

    public ServerException() {
        super();
    }

    public ServerException(String msg) {
        super(msg);
    }
}

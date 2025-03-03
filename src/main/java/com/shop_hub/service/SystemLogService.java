package com.shop_hub.service;

public interface SystemLogService {

    void createLog(String functionName, int logType, String message, String detail);
}

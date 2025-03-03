package com.shop_hub.service.impl;

import com.shop_hub.model.SystemLog;
import com.shop_hub.repository.SystemLogRepository;
import com.shop_hub.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemLogServiceImpl implements SystemLogService {

    private final SystemLogRepository systemLogRepository;

    @Override
    @Transactional
    public void createLog(String functionName, int logType, String message, String detail) {
        try {
            log.debug("[SystemLogService][createLog] functionName: {}", functionName);
            SystemLog systemLog = SystemLog.builder()
                    .functionName(functionName)
                    .logType(logType)
                    .message(message)
                    .detail(detail)
                    .build();
            systemLogRepository.save(systemLog);
        }catch (Exception e) {
            log.error("[SystemLogService][createLog] ERROR functionName: {}", functionName);
        }
    }

}

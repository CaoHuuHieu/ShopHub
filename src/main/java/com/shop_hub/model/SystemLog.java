package com.shop_hub.model;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_logs")
@Builder
public class SystemLog extends AbstractEntity{

    @Column(name = "function_name")
    private String functionName;

    @Column(name = "log")
    private String message;

    @Column(name = "detail")
    private String detail;

    @Column(name = "log_type")
    private int logType;

    public static final int LOG_SUCCESS = 1;
    public static final int LOG_ERROR = 0;

}

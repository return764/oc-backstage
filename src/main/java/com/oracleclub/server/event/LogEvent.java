package com.oracleclub.server.event;

import com.oracleclub.server.entity.param.LogParam;
import org.springframework.context.ApplicationEvent;

/**
 * @author :RETURN
 * @date :2021/9/23 11:30
 */
public class LogEvent extends ApplicationEvent {

    private final LogParam logParam;

    public LogEvent(Object source, LogParam logParam) {
        super(source);
        this.logParam = logParam;
    }

    public LogEvent(Object source, String type, String content){
        this(source,new LogParam(type, content));
    }

    public LogParam getLog(){
        return this.logParam;
    }
}

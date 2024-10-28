package com.cyc.imagon.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.cyc.imagon.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class LogTimeUsedConvert extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        String requestTime = MDC.get("requestTime");
        if (StringUtil.isBlank(requestTime)) {
            return "0";
        }
        return String.valueOf(event.getTimeStamp() - Long.parseLong(requestTime));
    }

}

package cn.yueshutong.springbootstartercurrentlimiting.core;

import cn.yueshutong.springbootstartercurrentlimiting.common.SpringContextUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface RateLimiter {
    String message = "<pre>The specified service is not currently available.</pre>";

    ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(SpringContextUtil.getCorePoolSize());

    boolean tryAcquire();

    boolean tryAcquireFailed();

    LocalDateTime getExpirationTime();

    static RateLimiter of(double QPS, long initialDelay, String bucket, boolean overflow){
        boolean cloudEnabled = SpringContextUtil.isCloudEnabled();
        if (cloudEnabled){
            return RateLimiterCloud.of(QPS,initialDelay,bucket,overflow);
        }else {
            return RateLimiterSingle.of(QPS,initialDelay,overflow);
        }
    }

    static RateLimiter of(double QPS, long initialDelay, String bucket, boolean overflow, long time, ChronoUnit unit) {
        boolean cloudEnabled = SpringContextUtil.isCloudEnabled();
        if (cloudEnabled){
            return RateLimiterCloud.of(QPS,initialDelay,bucket,overflow,time,unit);
        }else {
            return RateLimiterSingle.of(QPS,initialDelay,overflow,time,unit);
        }
    }
}

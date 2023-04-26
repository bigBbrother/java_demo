package com.dabige;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component //让spring容器帮我们创建此类的对象
public class MyQuaryz {
    //用来开启定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    public void task() {
        System.out.println("吃饭,睡觉,打豆豆");
    }
}

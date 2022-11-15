package com.aojiaodage.portal.config;

import com.aojiaodage.portal.entity.OmsOrder;
import com.aojiaodage.portal.service.OmsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableScheduling
public class ScheduleTaskConfig {

    @Autowired
    OmsOrderService orderService;

    // 这里会有延迟问题【每隔30分钟】，但影响不大；
    @Scheduled(cron = "0 0/30 * * * ?")
    public void cancelOrder() {
        log.info("执行定时取消订单任务...");
        List<OmsOrder> orders = orderService.cancelByTask();
        log.info("执行完成，影响记录数为：" + orders.size());
        if (orders.size() > 0) {
            log.info("影响订单号为：" + orders.stream().map(OmsOrder::getOrderSn).collect(Collectors.toList()));
        }
    }
}

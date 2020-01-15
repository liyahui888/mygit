package com.cloud.provider;

import com.alibaba.fastjson.JSON;
import com.cloud.provider.entity.TicketEntity;
import com.cloud.provider.service.ThreadTestService;
import com.cloud.provider.service.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThreadTestServiceTests {

    private static Logger logger = LoggerFactory.getLogger(ThreadTestServiceTests.class);

    @Autowired
    private ThreadTestService threadTestService;
    private int reqNum;

    @Test
    public void testSaleTicket() throws Exception {
        logger.info("开始做任务");
        reqNum++;
        TicketEntity ticketEntity = threadTestService.saleTicket(reqNum);
        logger.info("结束做任务：{}", JSON.toJSONString(ticketEntity));
    }


}

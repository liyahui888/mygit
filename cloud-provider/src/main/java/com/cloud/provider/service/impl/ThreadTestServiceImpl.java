package com.cloud.provider.service.impl;

import com.cloud.provider.entity.TicketEntity;
import com.cloud.provider.entity.UserEntity;
import com.cloud.provider.mapper.UserMapper;
import com.cloud.provider.service.ThreadTestService;
import com.cloud.provider.util.SaleTicketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Service
public class ThreadTestServiceImpl implements ThreadTestService {

    private static Logger logger = LoggerFactory.getLogger(ThreadTestServiceImpl.class);

    private int TicketNum = 100;

    @Override
    public TicketEntity saleTicket(int reqNum)
            throws Exception{
        logger.info("共计：{}张票",TicketNum);
        ExecutorService executorService  = Executors.newFixedThreadPool(3);
        FutureTask<TicketEntity> futureTask =null;
        for (int i = 1; i <=reqNum; i++) {
            futureTask = new FutureTask<TicketEntity>(getdt(TicketNum));
            executorService.submit(futureTask);
        }
        TicketEntity ticketEntity = futureTask.get();
        executorService.shutdown();
        return  ticketEntity;
    }

    private Callable<TicketEntity> getdt(int offset) {
        Callable<TicketEntity> callable = new Callable<TicketEntity>() {
            @Override
            public TicketEntity call() throws Exception {
                TicketEntity  tickt =null;
                    if (TicketNum>0) {
                        synchronized (this) {
                                System.out.println("第" + Thread.currentThread().getName() + "个车站正在卖出第" + (101 - TicketNum) + "张车票");
                                --TicketNum;
                                tickt =new TicketEntity();
                                tickt.setTicketId((long) (101 - TicketNum));
                                tickt.setName("张三"+(long) (101 - TicketNum));
                        }
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName()+"--票卖完了！");
                    }


                return tickt;
            }
        };
        return callable;
    }
}

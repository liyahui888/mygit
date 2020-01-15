package com.cloud.provider.service;

import com.cloud.provider.entity.TicketEntity;

public interface ThreadTestService {

    TicketEntity saleTicket(int reqNum) throws Exception;
}

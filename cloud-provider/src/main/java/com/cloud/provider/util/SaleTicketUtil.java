package com.cloud.provider.util;

public class SaleTicketUtil {

    private int count = 100;

    public void sale(){
        synchronized (this) {
            if (count>0) {
                System.out.println("第" + Thread.currentThread().getName() + "个车站正在卖出第" + (101 - count) + "张车票");
                --count;
            } else {
                System.out.println(Thread.currentThread().getName()+"--票卖完了！");
            }
        }
    }

    public int getCount() {
        return count;
    }
}

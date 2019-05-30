package com.shidq.sofabolt.common;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DISCONNECTEventProcessor implements ConnectionEventProcessor {

    private AtomicBoolean dicConnected    = new AtomicBoolean();
    private AtomicInteger disConnectTimes = new AtomicInteger();

    public void onEvent(String remoteAddr, Connection conn) {
       // Assert.assertNotNull(conn);
        dicConnected.set(true);
        disConnectTimes.incrementAndGet();
    }

    public boolean isDisConnected() {
        return this.dicConnected.get();
    }

    public int getDisConnectTimes() {
        return this.disConnectTimes.get();
    }

    public void reset() {
        this.disConnectTimes.set(0);
        this.dicConnected.set(false);
    }
}

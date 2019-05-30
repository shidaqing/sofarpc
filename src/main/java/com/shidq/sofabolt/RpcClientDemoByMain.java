package com.shidq.sofabolt;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.shidq.sofabolt.common.CONNECTEventProcessor;
import com.shidq.sofabolt.common.DISCONNECTEventProcessor;
import com.shidq.sofabolt.common.RequestBody;
import com.shidq.sofabolt.common.SimpleClientUserProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcClientDemoByMain {
    static Logger logger                    = LoggerFactory
            .getLogger(RpcClientDemoByMain.class);

    static RpcClient          client;

    static String             addr                      = "127.0.0.1:8999";

    SimpleClientUserProcessor clientUserProcessor       = new SimpleClientUserProcessor();
    CONNECTEventProcessor clientConnectProcessor    = new CONNECTEventProcessor();
    DISCONNECTEventProcessor clientDisConnectProcessor = new DISCONNECTEventProcessor();

    public RpcClientDemoByMain() {
        // 1. create a rpc client
        client = new RpcClient();
        // 2. add processor for connect and close event if you need
        client.addConnectionEventProcessor(ConnectionEventType.CONNECT, clientConnectProcessor);
        client.addConnectionEventProcessor(ConnectionEventType.CLOSE, clientDisConnectProcessor);
        // 3. do init
        client.init();
    }

    public static void main(String[] args) {
        new RpcClientDemoByMain();
        RequestBody req = new RequestBody(2, "hello world sync");
        try {
            String res = (String) client.invokeSync(addr, req, 3000);
            System.out.println("invoke sync result = [" + res + "]");
        } catch (RemotingException e) {
            String errMsg = "RemotingException caught in oneway!";
            logger.error(errMsg, e);
            //Assert.fail(errMsg);
        } catch (InterruptedException e) {
            logger.error("interrupted!");
        }
        client.shutdown();
    }
}

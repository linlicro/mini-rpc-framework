package me.icro.rpc.example.consumer;

import me.icro.rpc.entity.RpcServiceProperties;
import me.icro.rpc.example.Message;
import me.icro.rpc.example.MessageService;
import me.icro.rpc.proxy.RpcClientProxy;
import me.icro.rpc.remoting.transport.IRpcRequestTransport;
import me.icro.rpc.remoting.transport.socket.SocketRpcClient;

/**
 * @author lin
 * @version v 0.1 2021/1/19
 **/
public class SocketConsumerMain {
    public static void main(String[] args) {
        IRpcRequestTransport rpcRequestTransport = new SocketRpcClient();
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder().group("test-socket").version("v1").build();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcRequestTransport, rpcServiceProperties);
        MessageService messageService = rpcClientProxy.getProxy(MessageService.class);
        String msg = messageService.send(new Message("title", "description"));
        System.out.println(msg);
    }
}

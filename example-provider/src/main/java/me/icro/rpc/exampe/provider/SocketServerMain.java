package me.icro.rpc.exampe.provider;

import me.icro.rpc.entity.RpcServiceProperties;
import me.icro.rpc.example.MessageService;
import me.icro.rpc.remoting.transport.socket.SocketRpcServer;

/**
 * @author lin
 * @version v 0.1 2021/1/18
 **/
public class SocketServerMain {
    public static void main(String[] args) {
        MessageService messageService = new MessageServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
                .group("test-socket").version("v1").build();
        socketRpcServer.registerService(messageService, rpcServiceProperties);
        socketRpcServer.start();
    }
}

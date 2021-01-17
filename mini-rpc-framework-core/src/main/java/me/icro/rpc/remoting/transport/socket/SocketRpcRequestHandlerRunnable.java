package me.icro.rpc.remoting.transport.socket;

import lombok.extern.slf4j.Slf4j;
import me.icro.rpc.remoting.dto.RpcRequest;
import me.icro.rpc.remoting.dto.RpcResponse;
import me.icro.rpc.remoting.handler.RpcRequestHandler;
import me.icro.rpc.utils.SingletonFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author lin
 * @version v 0.1 2021/1/17
 **/
@Slf4j
public class SocketRpcRequestHandlerRunnable implements Runnable {

    private final Socket socket;
    private final RpcRequestHandler rpcRequestHandler;

    public SocketRpcRequestHandlerRunnable(Socket socket) {
        this.socket = socket;
        rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    public void run() {
        log.info("server handle message from client by thread: [{}]", Thread.currentThread().getName());
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = rpcRequestHandler.handle(rpcRequest);
            objectOutputStream.writeObject(RpcResponse.success(result, rpcRequest.getRequestId()));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            log.error("occur exception:", e);
        }
    }
}

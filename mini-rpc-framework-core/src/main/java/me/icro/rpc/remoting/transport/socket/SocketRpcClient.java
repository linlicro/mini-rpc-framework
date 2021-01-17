package me.icro.rpc.remoting.transport.socket;

import me.icro.rpc.entity.RpcServiceProperties;
import me.icro.rpc.exception.RpcException;
import me.icro.rpc.extension.ExtensionLoader;
import me.icro.rpc.registry.IServiceDiscovery;
import me.icro.rpc.remoting.dto.RpcRequest;
import me.icro.rpc.remoting.transport.IRpcRequestTransport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 基于 Socket 实现Rpc请求
 *
 * @author lin
 * @version v 0.1 2021/1/17
 **/
public class SocketRpcClient implements IRpcRequestTransport {

    private final IServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(IServiceDiscovery.class).getExtension("zk");
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        String rpcServiceName = RpcServiceProperties.builder()
                .serviceName(rpcRequest.getInterfaceName())
                .group(rpcRequest.getGroup())
                .version(rpcRequest.getVersion())
                .build()
                .toRpcServiceName();
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcServiceName);
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败:", e);
        }
    }
}

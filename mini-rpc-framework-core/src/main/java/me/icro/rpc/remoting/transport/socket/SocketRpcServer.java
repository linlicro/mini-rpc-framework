package me.icro.rpc.remoting.transport.socket;

import lombok.extern.slf4j.Slf4j;
import me.icro.rpc.config.DefaultShutdownHook;
import me.icro.rpc.entity.RpcServiceProperties;
import me.icro.rpc.provider.IServiceProvider;
import me.icro.rpc.provider.ServiceProviderImpl;
import me.icro.rpc.utils.SingletonFactory;
import me.icro.rpc.utils.ThreadPoolFactoryUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static me.icro.rpc.remoting.constants.RpcConstants.PORT;

/**
 * @author lin
 * @version v 0.1 2021/1/17
 **/
@Slf4j
public class SocketRpcServer {
    private final ExecutorService threadPool;
    private final IServiceProvider serviceProvider;

    public SocketRpcServer() {
        threadPool = ThreadPoolFactoryUtils.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
    }

    public void registerService(Object service) {
        serviceProvider.publishService(service);
    }

    public void registerService(Object service, RpcServiceProperties rpcServiceProperties) {
        serviceProvider.publishService(service, rpcServiceProperties);
    }

    public void start() {
        try (ServerSocket server = new ServerSocket()) {
            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(new InetSocketAddress(host, PORT));
            DefaultShutdownHook.getCustomShutdownHook().clearAll();
            Socket socket;
            while ((socket = server.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                threadPool.execute(new SocketRpcRequestHandlerRunnable(socket));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("occur IOException:", e);
        }
    }
}

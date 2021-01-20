package me.icro.rpc.remoting.handler;

import lombok.extern.slf4j.Slf4j;
import me.icro.rpc.exception.RpcException;
import me.icro.rpc.provider.IServiceProvider;
import me.icro.rpc.provider.ServiceProviderImpl;
import me.icro.rpc.remoting.dto.RpcRequest;
import me.icro.rpc.utils.SingletonFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author lin
 * @version v 0.1 2021/1/16
 **/
@Slf4j
public class RpcRequestHandler {
    private final IServiceProvider serviceProvider;

    public RpcRequestHandler() {
        serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
    }

    public Object handle(RpcRequest rpcRequest) {
        Object service = serviceProvider.getService(rpcRequest.toRpcProperties());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     * 调用目标方法
     *
     * @param rpcRequest rpc请求
     * @param service    服务提供者对象
     * @return result
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service:[{}] successful invoke method:[{}]", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }
}

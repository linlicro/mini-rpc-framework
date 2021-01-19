package me.icro.rpc.proxy;

import me.icro.rpc.entity.RpcServiceProperties;
import me.icro.rpc.enums.RpcResponseCodeEnum;
import me.icro.rpc.exception.RpcException;
import me.icro.rpc.remoting.dto.RpcRequest;
import me.icro.rpc.remoting.dto.RpcResponse;
import me.icro.rpc.remoting.transport.IRpcRequestTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import static me.icro.rpc.exception.RpcException.REQUEST_NOT_MATCH_RESPONSE;
import static me.icro.rpc.exception.RpcException.SERVICE_INVOCATION_FAILURE;

/**
 * @author lin
 * @version v 0.1 2021/1/19
 **/
public class RpcClientProxy implements InvocationHandler {

    private final IRpcRequestTransport rpcRequestTransport;
    private final RpcServiceProperties rpcServiceProperties;

    public RpcClientProxy(IRpcRequestTransport rpcRequestTransport, RpcServiceProperties rpcServiceProperties) {
        this.rpcRequestTransport = rpcRequestTransport;
        this.rpcServiceProperties = rpcServiceProperties;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .group(rpcServiceProperties.getGroup())
                .version(rpcServiceProperties.getVersion())
                .build();
        RpcResponse<Object> rpcResponse = null;
        rpcResponse = (RpcResponse<Object>) rpcRequestTransport.sendRpcRequest(rpcRequest);
        this.check(rpcResponse, rpcRequest);
        return rpcResponse.getData();
    }

    private void check(RpcResponse<Object> rpcResponse, RpcRequest rpcRequest) {
        if (rpcResponse == null) {
            throw new RpcException(SERVICE_INVOCATION_FAILURE, rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(REQUEST_NOT_MATCH_RESPONSE, rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getCode() == null || !rpcResponse.getCode().equals(RpcResponseCodeEnum.SUCCESS.getCode())) {
            throw new RpcException(SERVICE_INVOCATION_FAILURE, rpcRequest.getInterfaceName());
        }
    }
}

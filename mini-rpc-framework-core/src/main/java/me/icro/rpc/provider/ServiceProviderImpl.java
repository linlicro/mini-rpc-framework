package me.icro.rpc.provider;

import lombok.extern.slf4j.Slf4j;
import me.icro.rpc.entity.RpcServiceProperties;
import me.icro.rpc.exception.RpcException;
import me.icro.rpc.extension.ExtensionLoader;
import me.icro.rpc.registry.IServiceRegistry;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lin
 * @version v 0.1 2021/1/17
 **/
@Slf4j
public class ServiceProviderImpl implements IServiceProvider {

    /**
     * key: rpc服务名; value: 服务对象
     */
    private final Map<String, Object> serviceMap;
    private final Set<String> registeredService;
    private final IServiceRegistry serviceRegistry;

    public ServiceProviderImpl() {
        serviceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
        serviceRegistry = ExtensionLoader.getExtensionLoader(IServiceRegistry.class).getExtension("zk");
    }

    @Override
    public void addService(Object service, Class<?> serviceClass, RpcServiceProperties rpcServiceProperties) {
        String rpcServiceName = rpcServiceProperties.toRpcServiceName();
        if (registeredService.contains(rpcServiceName)) {
            return;
        }
        registeredService.add(rpcServiceName);
        serviceMap.put(rpcServiceName, service);
        log.info("Add service: {} and interfaces:{}", rpcServiceName, service.getClass().getInterfaces());
    }

    @Override
    public Object getService(RpcServiceProperties rpcServiceProperties) {
        Object service = serviceMap.get(rpcServiceProperties.toRpcServiceName());
        if (null == service) {
            throw new RpcException(RpcException.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(Object service, RpcServiceProperties rpcServiceProperties) {
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            Class<?> serviceRelatedInterface = service.getClass().getInterfaces()[0];
            String serviceName = serviceRelatedInterface.getCanonicalName();
            rpcServiceProperties.setServiceName(serviceName);
            this.addService(service, serviceRelatedInterface, rpcServiceProperties);
            serviceRegistry.registerService(rpcServiceProperties.toRpcServiceName(), new InetSocketAddress(host, 9998));
        } catch (UnknownHostException e) {
            log.error("occur exception when getHostAddress", e);
        }
    }

    @Override
    public void publishService(Object service) {
        this.publishService(service, RpcServiceProperties.builder().group("").version("").build());
    }
}

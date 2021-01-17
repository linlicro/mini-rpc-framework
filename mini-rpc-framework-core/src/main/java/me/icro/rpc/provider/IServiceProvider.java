package me.icro.rpc.provider;

import me.icro.rpc.entity.RpcServiceProperties;

/**
 * 服务提供Interface 添加、发布、获取 提供者服务
 *
 * @author lin
 * @version v 0.1 2021/1/17
 **/
public interface IServiceProvider {

    /**
     * 新增 服务提供者
     * @param service 服务对象
     * @param serviceClass 服务的接口类
     * @param rpcServiceProperties 服务属性值
     */
    void addService(Object service, Class<?> serviceClass, RpcServiceProperties rpcServiceProperties);

    /**
     * 获取 服务提供者
     * @param rpcServiceProperties 服务属性值
     * @return 服务对象
     */
    Object getService(RpcServiceProperties rpcServiceProperties);

    /**
     * 发布 服务
     * @param service 服务对象
     * @param rpcServiceProperties 服务属性值
     */
    void publishService(Object service, RpcServiceProperties rpcServiceProperties);

    /**
     * 发布 服务
     * @param service 服务对象
     */
    void publishService(Object service);
}

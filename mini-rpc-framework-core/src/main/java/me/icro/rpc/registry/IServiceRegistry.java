package me.icro.rpc.registry;

import me.icro.rpc.extension.SPI;

import java.net.InetSocketAddress;

/**
 * 服务注册 Interface
 *
 * @author lin
 * @version v 0.1 2021/1/17
 **/
@SPI
public interface IServiceRegistry {
    /**
     * 注册服务
     * @param rpcServiceName 服务名
     * @param inetSocketAddress 服务地址
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}

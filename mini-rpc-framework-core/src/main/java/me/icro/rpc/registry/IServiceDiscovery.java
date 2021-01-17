package me.icro.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务发现 interface
 * @author lin
 * @version v 0.1 2021/1/17
 **/
public interface IServiceDiscovery {
    /**
     * 查找服务地址
     *
     * @param rpcServiceName rpc服务名
     * @return 服务地址
     */
    InetSocketAddress lookupService(String rpcServiceName);
}

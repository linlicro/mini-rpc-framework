package me.icro.rpc.registry;

import me.icro.rpc.extension.SPI;
import me.icro.rpc.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * 服务发现 interface
 * @author lin
 * @version v 0.1 2021/1/17
 **/
@SPI
public interface IServiceDiscovery {
    /**
     * 查找服务地址
     *
     * @param rpcRequest rpcRequest
     * @return 服务地址
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}

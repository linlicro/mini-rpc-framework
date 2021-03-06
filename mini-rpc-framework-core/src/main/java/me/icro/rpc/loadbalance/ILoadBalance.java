package me.icro.rpc.loadbalance;

import me.icro.rpc.extension.SPI;
import me.icro.rpc.remoting.dto.RpcRequest;

import java.util.List;

/**
 * 负载均衡 interface
 *
 * @author lin
 * @version v 0.1 2021/1/19
 **/
@SPI
public interface ILoadBalance {

    /**
     * 根据 负载均衡 获取服务提供者的地址
     *
     * @param serviceAddresses 服务提供者地址集合
     * @param rpcRequest       rpcRequest
     * @return 地址
     */
    String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest);
}

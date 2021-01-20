package me.icro.rpc.loadbalance;

import me.icro.rpc.remoting.dto.RpcRequest;

import java.util.List;

/**
 * @author lin
 * @version v 0.1 2021/1/19
 **/
public abstract class AbstractLoadBalance implements ILoadBalance {

    @Override
    public String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest) {
        if (serviceAddresses == null || serviceAddresses.size() == 0) {
            return null;
        }
        if (serviceAddresses.size() == 1) {
            return serviceAddresses.get(0);
        }
        return doSelect(serviceAddresses, rpcRequest);
    }

    /**
     * 负载均衡的实现
     *
     * @param serviceAddresses 服务提供者地址集合
     * @param rpcRequest rpcRequest
     * @return 地址
     */
    protected abstract String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest);
}

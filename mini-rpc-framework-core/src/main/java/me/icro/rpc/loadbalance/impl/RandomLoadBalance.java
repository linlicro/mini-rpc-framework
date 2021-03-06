package me.icro.rpc.loadbalance.impl;

import me.icro.rpc.loadbalance.AbstractLoadBalance;
import me.icro.rpc.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * 随机选择
 *
 * @author lin
 * @version v 0.1 2021/1/19
 **/
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}

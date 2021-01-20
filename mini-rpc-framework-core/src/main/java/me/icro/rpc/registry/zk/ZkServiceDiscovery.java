package me.icro.rpc.registry.zk;

import lombok.extern.slf4j.Slf4j;
import me.icro.rpc.exception.RpcException;
import me.icro.rpc.extension.ExtensionLoader;
import me.icro.rpc.loadbalance.ILoadBalance;
import me.icro.rpc.registry.IServiceDiscovery;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

import static me.icro.rpc.exception.RpcException.SERVICE_CAN_NOT_BE_FOUND;

/**
 * @author lin
 * @version v 0.1 2021/1/19
 **/
@Slf4j
public class ZkServiceDiscovery implements IServiceDiscovery {

    private final ILoadBalance loadBalance;

    public ZkServiceDiscovery() {
        this.loadBalance = ExtensionLoader.getExtensionLoader(ILoadBalance.class).getExtension("loadBalance");
    }

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (serviceUrlList == null || serviceUrlList.size() == 0) {
            throw new RpcException(SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        // load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcServiceName);
        log.info("获得服务提供者地址为:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}

package me.icro.rpc.registry.zk;

import lombok.extern.slf4j.Slf4j;
import me.icro.rpc.registry.IServiceRegistry;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;

/**
 * @author lin
 * @version v 0.1 2021/1/19
 **/
@Slf4j
public class ZkServiceRegistry implements IServiceRegistry {

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}

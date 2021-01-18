package me.icro.rpc.config;

import lombok.extern.slf4j.Slf4j;
import me.icro.rpc.registry.zk.CuratorUtils;
import me.icro.rpc.utils.ThreadPoolFactoryUtils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import static me.icro.rpc.remoting.constants.RpcConstants.PORT;

/**
 * @author lin
 * @version v 0.1 2021/1/18
 **/
@Slf4j
public class DefaultShutdownHook {
    private static final DefaultShutdownHook CUSTOM_SHUTDOWN_HOOK = new DefaultShutdownHook();

    public static DefaultShutdownHook getCustomShutdownHook() {
        return CUSTOM_SHUTDOWN_HOOK;
    }

    public void clearAll() {
        log.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), PORT);
                CuratorUtils.clearRegistry(CuratorUtils.getZkClient(), inetSocketAddress);
            } catch (UnknownHostException ignored) {
            }
            ThreadPoolFactoryUtils.shutDownAllThreadPool();
        }));
    }
}

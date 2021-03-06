package me.icro.rpc.entity;

import lombok.Builder;
import lombok.Data;
import me.icro.rpc.remoting.dto.RpcRequest;

/**
 * @author lin
 * @version v 0.1 2021/1/17
 **/
@Data
@Builder
public class RpcServiceProperties {

    private static final String DELIMITER = ":";

    /**
     * 版本号
     */
    private String version;
    /**
     * 分组
     */
    private String group;
    private String serviceName;

    public RpcServiceProperties(RpcRequest rpcRequest) {
        this.group = rpcRequest.getGroup();
        this.version = rpcRequest.getVersion();
        this.serviceName = rpcRequest.getInterfaceName();
    }

    public String toRpcServiceName() {
        return this.getServiceName() + DELIMITER + this.getGroup() + DELIMITER + this.getVersion();
    }
}

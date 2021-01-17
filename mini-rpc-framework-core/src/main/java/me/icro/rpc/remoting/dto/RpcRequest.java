package me.icro.rpc.remoting.dto;

import lombok.Builder;
import lombok.Data;
import me.icro.rpc.entity.RpcServiceProperties;

import java.io.Serializable;

/**
 * @author lin
 * @version v 0.1 2021/1/16
 **/
@Data
@Builder
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = -755753291738203709L;

    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    public RpcServiceProperties toRpcProperties() {
        return RpcServiceProperties.builder().serviceName(this.getInterfaceName())
                .version(this.getVersion())
                .group(this.getGroup()).build();
    }
}

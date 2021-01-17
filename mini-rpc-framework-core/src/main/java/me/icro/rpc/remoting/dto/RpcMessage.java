package me.icro.rpc.remoting.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author lin
 * @version v 0.1 2021/1/16
 **/
@Data
@Builder
public class RpcMessage {
    private byte messageType;
    private byte codec;
    private byte compress;
    private int requestId;
    private Object data;
}

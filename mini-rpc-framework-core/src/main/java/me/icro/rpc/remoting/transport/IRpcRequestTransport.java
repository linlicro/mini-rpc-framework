package me.icro.rpc.remoting.transport;

import me.icro.rpc.extension.SPI;
import me.icro.rpc.remoting.dto.RpcRequest;

/**
 * Rpc请求传输
 *
 * @author lin
 * @version v 0.1 2021/1/17
 **/
@SPI
public interface IRpcRequestTransport {
    /**
     * 发送 RPC请求
     * @param rpcRequest rpc请求
     * @return result
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}

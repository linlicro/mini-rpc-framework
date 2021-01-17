package me.icro.rpc.exception;

/**
 * @author lin
 * @version v 0.1 2021/1/17
 **/
public class RpcException extends RuntimeException {
    private static final long serialVersionUID = 7900737719911136241L;

    public static final String CLIENT_CONNECT_SERVER_FAILURE = "客户端连接服务端失败";
    public static final String SERVICE_INVOCATION_FAILURE = "服务调用失败";
    public static final String SERVICE_CAN_NOT_BE_FOUND = "没有找到指定的服务";
    public static final String SERVICE_NOT_IMPLEMENT_ANY_INTERFACE = "注册的服务没有实现任何接口";
    public static final String REQUEST_NOT_MATCH_RESPONSE = "返回结果错误，请求和返回的相应不匹配";

    public RpcException(String message, String detail) {
        super(message + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(String message) {
        super(message);
    }
}

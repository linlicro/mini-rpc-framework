package me.icro.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lin
 * @version v 0.1 2021/1/16
 **/
@AllArgsConstructor
@Getter
public enum RpcResponseCodeEnum {
    /**
     *
     */
    SUCCESS(200, "Rpc调用成功"),
    FAIL(500, "Rpc调用失败");
    private final int code;

    private final String message;
}

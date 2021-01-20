package me.icro.rpc.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lin
 * @version v 0.1 2021/1/18
 **/
@Data
@AllArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = -472781286104497301L;

    private String title;
    private String description;
}

package me.icro.rpc.example;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lin
 * @version v 0.1 2021/1/18
 **/
@Data
@AllArgsConstructor
public class Message {
    private String title;
    private String description;
}

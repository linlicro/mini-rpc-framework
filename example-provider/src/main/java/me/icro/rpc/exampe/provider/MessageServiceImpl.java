package me.icro.rpc.exampe.provider;

import lombok.extern.slf4j.Slf4j;
import me.icro.rpc.example.MessageService;
import me.icro.rpc.example.Message;

/**
 * @author lin
 * @version v 0.1 2021/1/18
 **/
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Override
    public String send(Message message) {
        log.info("MessageServiceImpl 收到: {}.", message.getTitle());
        String result = "description is " + message.getDescription();
        log.info("MessageServiceImpl 返回: {}.", result);
        return result;
    }
}

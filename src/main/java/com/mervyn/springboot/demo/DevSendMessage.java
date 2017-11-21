package com.mervyn.springboot.demo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by mengran.gao on 2017/11/21.
 */
@Component
@Profile("dev")
public class DevSendMessage implements SendMessage {
    @Override
    public void send() {
        System.out.println("dev message");
    }
}

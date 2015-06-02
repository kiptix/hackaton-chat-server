package com.btc.hackaton.chat;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;

public class Bootstrap {

    public static void main(String... args) throws BootstrapException {
        PayaraMicro
                .getInstance()
                .addDeployment("target/chat.war")
                .bootStrap();
    }
}

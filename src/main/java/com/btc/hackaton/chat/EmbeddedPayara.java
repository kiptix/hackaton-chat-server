package com.btc.hackaton.chat;

import java.io.File;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;

public class EmbeddedPayara {

    public static void main(String[] args) throws GlassFishException {
        GlassFishRuntime runtime = GlassFishRuntime.bootstrap();
        GlassFishProperties gfproperties = new GlassFishProperties();
        gfproperties.setPort("http-listener", 8080);
        GlassFish gf = runtime.newGlassFish(gfproperties);
        gf.start();
        gf.getDeployer().deploy(new File("target/chat.war"));
    }
}

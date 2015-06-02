package com.btc.hackaton.chat;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class LoginMessageEncoder implements Encoder.Text<LoginMessage> {
   

        @Override
        public String encode(LoginMessage message) throws EncodeException {
            StringWriter writer = new StringWriter();
            try (JsonWriter jsonWrite = Json.createWriter(writer)) {
                JsonObjectBuilder builder = Json.createObjectBuilder();
                builder.add("type", LoginMessage.TYPE)
                        .add("userName", message.getUserName())
                        .add("displayName", message.getDisplayName());

                jsonWrite.writeObject(builder.build());
            }
            return writer.toString();
        }

        @Override
        public void init(EndpointConfig config) {
        }

        @Override
        public void destroy() {
        }
}

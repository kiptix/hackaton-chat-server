package com.btc.hackaton.chat;

import java.io.StringWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class OutMessageEncoder implements Encoder.Text<OutMessage> {

    @Override
    public String encode(OutMessage message) throws EncodeException {
        StringWriter writer = new StringWriter();
        try (JsonWriter jsonWrite = Json.createWriter(writer)) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("type", OutMessage.TYPE)
                    .add("userName", message.getSender().getUserName())
                    .add("displayName", message.getSender().getDisplayName())
                    .add("color", message.getSender().getColor())
                    .add("date", LocalTime.now().toString())
                    .add("message", message.getMessage());
            if (message.getRecipient() != null) {
                builder.add("recipient", message.getRecipient());
            }

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

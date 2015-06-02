package com.btc.hackaton.chat;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class InMessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String string) throws DecodeException {
        JsonObject json = Json.createReader(new StringReader(string)).readObject();
        String recipient = json.getString("recipient", null);
        if (recipient != null && recipient.length() == 0) {
            recipient = null;
        }
        String message = json.getString("message");

        return new InMessage(recipient, message);
    }

    @Override
    public boolean willDecode(String string) {
        JsonObject json = Json.createReader(new StringReader(string)).readObject();
        return InMessage.TYPE.equals(json.getString("type"));
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}

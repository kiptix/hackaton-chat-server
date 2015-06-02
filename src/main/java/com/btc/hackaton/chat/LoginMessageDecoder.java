package com.btc.hackaton.chat;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class LoginMessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String string) throws DecodeException {
        JsonObject json = Json.createReader(new StringReader(string)).readObject();
        String sender = json.getString("userName");
        String displayName = json.getString("displayName");
        return new LoginMessage(sender, displayName);
    }

    @Override
    public boolean willDecode(String string) {
        JsonObject json = Json.createReader(new StringReader(string)).readObject();
        return LoginMessage.TYPE.equals(json.getString("type"));
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}

package com.btc.hackaton.chat;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatEndpoint {
    
    private static Map<String, Session> user = new HashMap<String, Session>();

    @OnMessage
    public void message(String message, Session client) throws IOException, EncodeException {
        System.out.println("message: " + message);
               
        JsonObject json = Json.createReader(new StringReader(message)).readObject();
        String sender = json.getString("sender");
        String receiver = json.getString("receiver", "");
        String body = json.getString("message");
        
        if ("join".equals(body)){
            if (user.containsKey(sender)) {
                if (!user.get(sender).equals(client)) {
                    client.getBasicRemote().sendText("Username already in use");
                }
            } else if (user.containsValue(client)){ 
              client.getBasicRemote().sendText("You are already logged in as " + getUsernameBySession(client));  
            } else {
                user.put(sender, client);
            }
        } else {
            Session receiverSession = user.get(receiver);
            if (receiverSession != null) {
                receiverSession.getBasicRemote().sendText(sender +": "+ body);
            } else {
                client.getBasicRemote().sendText(receiver + " is offline");
            }
        }
    }

    private String getUsernameBySession(Session client) {
        for (Map.Entry<String, Session> entry : user.entrySet()) {
            if (entry.getValue().equals(client)) {
              return entry.getKey();
            }
        }
        return null;
    }
}

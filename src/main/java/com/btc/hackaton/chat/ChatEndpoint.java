package com.btc.hackaton.chat;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat",
        encoders = {OutMessageEncoder.class, LoginMessageEncoder.class},
        decoders = {LoginMessageDecoder.class, InMessageDecoder.class})
public class ChatEndpoint {

    private static final Map<User, Session> loggedInUser = new HashMap<>();

    @OnMessage
    public void message(Message message, Session client) throws IOException, EncodeException {
        if (!loggedInUser.containsValue(client)) {
//            throw new Exception("You have to be logged in to send messages");
        }

        if (message instanceof LoginMessage) {
            boolean successful = login((LoginMessage) message, client);
            if (successful) {
                for (User user : loggedInUser.keySet()) {
                    if (!client.equals(getSessionByUserName(user.getUserName()))) {
                        LoginMessage loginMessage = new LoginMessage(user.getUserName(), user.getDisplayName());
                        client.getBasicRemote().sendObject(loginMessage);   
                    }
                   
                }
                broadcastMessage(message);
            }
        } else {
            OutMessage out = buildOutMessage(message, client);
            sendMessage(out, client);
        }
    }

    private void sendMessage(OutMessage out, Session sender) throws IOException, EncodeException {
        if (out.getRecipient() != null) {
            Session session = getSessionByUserName(out.getRecipient());
            if (session != null) {
                session.getBasicRemote().sendObject(out);
                sender.getBasicRemote().sendObject(out);
            } else {
                String message = out.getRecipient() + " is offline";
                OutMessage msg = new OutMessage(new User("system"), 
                        out.getSender().getDisplayName(), LocalDate.now(), message);
                sender.getBasicRemote().sendObject(out);
            }
        } else {
            broadcastMessage(out);
        }
    }

    private void broadcastMessage(Object out) throws IOException, EncodeException {
        for (Session session : loggedInUser.values()) {
            session.getBasicRemote().sendObject(out);
        }
    }

    private OutMessage buildOutMessage(Message message, Session client) {
        InMessage in = (InMessage) message;
        User sender = getUserBySession(client);
        return new OutMessage(sender, in.getRecipient(), LocalDate.now(), in.getMessage());
    }

    private boolean login(LoginMessage message, Session client) throws IOException, EncodeException {
        System.out.println(message.getUserName() + " logged in");
        User user = new User(message.getUserName());
        user.setColor(randomHtmlColorCode());
        user.setDisplayName(message.getDisplayName());

        if (loggedInUser.containsKey(user)) {
            if (!loggedInUser.get(user).equals(client)) {
                throw new RuntimeException("Username already in use");
            }
        } else if (loggedInUser.containsValue(client)) {
            client.getBasicRemote().sendText("You are already logged in as " + getUserBySession(client));
        } else {
            loggedInUser.put(user, client);
            return true;
        }
        return false;
    }

    private String randomHtmlColorCode() {
        Random random = new Random();
        Color color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
        return "#" + String.format("%06x", color.getRGB() & 0x00FFFFFF);
    }

    @OnClose
    public void close(Session client) {
        loggedInUser.remove(getUserBySession(client));
    }

    @OnOpen
    public void open(Session client) {
        System.out.println("connected " + client.getId());
    }

    private User getUserBySession(Session client) {
        for (Map.Entry<User, Session> entry : loggedInUser.entrySet()) {
            if (entry.getValue().equals(client)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private Session getSessionByUserName(String userName) {
        return loggedInUser.get(new User(userName));
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}

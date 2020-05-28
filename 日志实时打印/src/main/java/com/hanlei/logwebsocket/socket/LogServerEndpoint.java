package com.hanlei.logwebsocket.socket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @ClassName LogServerEndpoint
 * @Description TODO
 * @Author hanlei
 * @Date 2020/5/25 2:22 下午
 * @Version 1.0
 */

public class LogServerEndpoint {

    static volatile List<Session> sessionList = new ArrayList<Session>();

    private Logger logger = Logger.getLogger(this.getClass().getName());

    MessageSender messageSender = new MessageSender();

    @OnOpen
    public void onOpen(Session session) {

        try {
            logger.info("add session");
            sessionList.add(session);
            logger.info("Connected ... " + session.getId());

            messageSender.addSession(session.getId(), session);
            logger.info("current session list:" + sessionList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        logger.info("on message:" + message);
        switch (message) {
            case "quit":
                try {
                    session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Game ended"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
        return message;
    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }
}

package pe.farmaciasperuanas.izipaywebsocket.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

public class CommunicationHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(CommunicationHandler.class);

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();

    /*@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions
    }*/
}

package pe.farmaciasperuanas.izipaywebsocket.controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import pe.farmaciasperuanas.izipaywebsocket.dto.ChatMessage;
import pe.farmaciasperuanas.izipaywebsocket.dto.Greeting;
import pe.farmaciasperuanas.izipaywebsocket.dto.HelloMessage;

@Controller
public class WebSocketController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) {
        System.out.println(message.getMessage());
        return new ChatMessage(message.getMessage(), message.getUser());
    }
}

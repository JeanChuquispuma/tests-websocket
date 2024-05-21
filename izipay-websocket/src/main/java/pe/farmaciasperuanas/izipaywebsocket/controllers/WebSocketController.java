package pe.farmaciasperuanas.izipaywebsocket.controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pe.farmaciasperuanas.izipaywebsocket.dto.ChatMessage;
import pe.farmaciasperuanas.izipaywebsocket.dto.OrderMessageDto;
import pe.farmaciasperuanas.izipaywebsocket.dto.ResponseMessageDto;

@Controller
public class WebSocketController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) {
        System.out.println(message.getMessage());
        return new ChatMessage(message.getMessage(), message.getUser());
    }
    
    // Método para recibir el pedido y transmitirlo a un topic específico
    @MessageMapping("/order/{roomId}")
    @SendTo("/topic/order/{roomId}")
    public OrderMessageDto receiveOrder(@DestinationVariable String roomId, OrderMessageDto order) {
        System.out.println("Received order: " + order.getCodFPago());
        return order;
    }

    // Método para transmitir la respuesta del pedido a un topic específico
    @MessageMapping("/response/{roomId}")
    @SendTo("/topic/response/{roomId}")
    public ResponseMessageDto sendResponse(@DestinationVariable String roomId, ResponseMessageDto response) {
        System.out.println("Sending response: " + response.getCode());
        return response;
    }
}

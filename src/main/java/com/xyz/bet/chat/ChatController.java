package com.xyz.bet.chat;

import com.xyz.bet.chat.response.MessageRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/chat/{roomId}/sendMessage")
    @SendTo("/topic/{roomId}")
    public MessageRecord processMessageFromClient(
            @DestinationVariable String roomId,
            HelloMessage message,
            SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.name);
        headerAccessor.getSessionAttributes().put("roomId", roomId);
        return new MessageRecord("Hello, " + HtmlUtils.htmlEscape(message.name) + "!");
    }

    public record HelloMessage(String name) {}
}

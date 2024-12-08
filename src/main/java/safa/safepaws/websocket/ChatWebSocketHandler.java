package safa.safepaws.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import safa.safepaws.model.ChatMessage;
import safa.safepaws.model.ChatRoom;
import safa.safepaws.model.User;
import safa.safepaws.service.ChatMessageService;
import safa.safepaws.service.ChatRoomService;
import safa.safepaws.service.RequestService;
import safa.safepaws.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSecurityService webSocketSecurityService;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final RequestService requestService;
    private final Map<String, List<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomCode = getRoomCodeFromSession(session);
        UserDetails user = getAuthenticatedUser(session);
        boolean hasAccess = true;

        if (!hasAccess) {
            session.close();
            return;
        }

        ChatRoom chatRoom = chatRoomService.findByCode(roomCode);
        List<ChatMessage> chatMessages = chatMessageService.findByChatRoomId(chatRoom.getId());

        if (!chatMessages.isEmpty()) {
            session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(new WebSocketMessage("server", "Mensajes Anteriores"))));
        }

        // Enviar mensajes anteriores
        for (ChatMessage chatMessage : chatMessages) {
            WebSocketMessage message = new WebSocketMessage(
                    chatMessage.getUserId().equals(userService.findByUsername(user.getUsername()).getId()) ? "me" : "other",
                    chatMessage.getMessage()
            );
            session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(message)));
        }

        roomSessions.computeIfAbsent(roomCode, k -> new ArrayList<>()).add(session);

        String message = user.getUsername() + " se ha conectado";
        notifyRoom(roomCode, new WebSocketMessage("server", message));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomCode = getRoomCodeFromSession(session);
        ObjectMapper objectMapper = new ObjectMapper();
        WebSocketMessage chatMessage = objectMapper.readValue(message.getPayload(), WebSocketMessage.class);

        UserDetails user = getAuthenticatedUser(session);
        User authenticatedUser = userService.findByUsername(user.getUsername());
        ChatRoom chatRoom = chatRoomService.findByCode(roomCode);

        if (chatRoom == null) return;

        ChatMessage chatMessageEntity = new ChatMessage();
        chatMessageEntity.setChatRoom(chatRoom);
        chatMessageEntity.setUserId(authenticatedUser.getId());
        chatMessageEntity.setMessage(chatMessage.getContent());
        chatMessageEntity.setCreatedAt(LocalDateTime.now());

        chatMessageService.save(chatMessageEntity);

        broadcastMessageToRoom(roomCode, chatMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomCode = getRoomCodeFromSession(session);
        UserDetails user = getAuthenticatedUser(session);
        List<WebSocketSession> sessions = roomSessions.get(roomCode);

        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                roomSessions.remove(roomCode);
            }
        }

        if (session.getAttributes().get("notify") != null && (boolean) session.getAttributes().get("notify")) {
            String message = user.getUsername() + " se ha desconectado";
            notifyRoom(roomCode, new WebSocketMessage("server", message));
        }

        SecurityContextHolder.clearContext();
        super.afterConnectionClosed(session, status);
    }

    private String getRoomCodeFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery().split("roomCode=")[1];
        return query.split("&")[0];
    }

    private UserDetails getAuthenticatedUser(WebSocketSession session) {
        String token = session.getUri().getQuery().split("token=")[1];
        webSocketSecurityService.setSecurityContext(token);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal());
        }
        return null;
    }

    private void broadcastMessageToRoom(String roomCode, WebSocketMessage message) throws Exception {
        List<WebSocketSession> sessions = roomSessions.get(roomCode);
        if (sessions != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String messageJson = objectMapper.writeValueAsString(message);

            for (WebSocketSession s : sessions) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(messageJson));
                }
            }
        }
    }

    private void notifyRoom(String roomCode, WebSocketMessage message) throws Exception {
        broadcastMessageToRoom(roomCode, message);
    }
}

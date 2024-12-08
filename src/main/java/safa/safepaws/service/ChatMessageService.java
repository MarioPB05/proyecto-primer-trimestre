package safa.safepaws.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import safa.safepaws.model.ChatMessage;
import safa.safepaws.repository.ChatMessageRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findByChatRoomId(Integer chatRoomId) {
        return chatMessageRepository.findByChatRoomIdOrderByCreatedAt(chatRoomId);
    }

}

package safa.safepaws.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import safa.safepaws.model.ChatRoom;
import safa.safepaws.model.Post;
import safa.safepaws.repository.ChatRoomRepository;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    /**
     * Create a chat room
     *
     * @param ownerId User id of the pet owner
     * @param adopterId User id of the pet adopter
     * @return Chat room code
     */
    public String createRoom(Integer ownerId, Integer adopterId, Post post) {
        ChatRoom chatRoom = new ChatRoom();

        chatRoom.setCode(NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 20).toUpperCase());
        chatRoom.setUserOwnerId(ownerId);
        chatRoom.setUserAdopterId(adopterId);
        chatRoom.setPost(post);

        return chatRoomRepository.save(chatRoom).getCode();
    }

    public String getChatRoomCode(Integer postId) {
        ChatRoom chatRoom = chatRoomRepository.findByPostId(postId).orElse(null);
        return chatRoom != null ? chatRoom.getCode() : null;
    }

    public ChatRoom findByCode(String code) {
        return chatRoomRepository.findByCode(code).orElse(null);
    }

}

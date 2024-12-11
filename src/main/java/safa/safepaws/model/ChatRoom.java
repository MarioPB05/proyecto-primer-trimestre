package safa.safepaws.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "chat_room", schema = "public", catalog = "safe_paws")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "user_owner_id", nullable = false)
    private Integer userOwnerId;

    @Column(name = "user_adopter_id", nullable = false)
    private Integer userAdopterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

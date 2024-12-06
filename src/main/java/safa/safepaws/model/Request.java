package safa.safepaws.model;

import jakarta.persistence.*;
import lombok.*;
import safa.safepaws.enums.RequestStatus;

import java.time.LocalDateTime;

@Entity
@Table(name="request", schema = "public", catalog = "safe_paws")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name="creation_date")
    private LocalDateTime creationDate;

    @Column(name="status")
    @Enumerated(EnumType.ORDINAL)
    private RequestStatus status;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}

package safa.safepaws.model;

import jakarta.persistence.*;
import lombok.*;
import safa.safepaws.enums.AnimalType;
import safa.safepaws.enums.PostStatus;

import java.time.LocalDate;

@Entity

@Table(name = "post" , schema = "public", catalog = "safe_paws")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "photo", nullable = false)
    private String photo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PostStatus status;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AnimalType type;

    @Column(name = "urgent", nullable = false)
    private boolean urgent;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}

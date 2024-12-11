package safa.safepaws.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "adoption_contract", schema = "public", catalog = "safe_paws")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AdoptionContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "document", nullable = false, length = Integer.MAX_VALUE)
    private String document;

    @Column(name = "document_sha256", length = Integer.MAX_VALUE)
    private String documentSha256;

    @Column(name = "owner_signature", length = Integer.MAX_VALUE)
    private String ownerSignature;

    @Column(name = "owner_signature_date")
    private LocalDateTime ownerSignatureDate;

    @Column(name = "adopter_signature", length = Integer.MAX_VALUE)
    private String adopterSignature;

    @Column(name = "adopter_signature_date")
    private LocalDateTime adopterSignatureDate;

}

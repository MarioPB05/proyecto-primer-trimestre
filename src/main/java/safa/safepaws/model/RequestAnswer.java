package safa.safepaws.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "request_answer", schema = "public", catalog = "safe_paws")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RequestAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "answer")
    private String answer;

    @OneToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;
}

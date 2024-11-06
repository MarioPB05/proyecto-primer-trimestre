package safa.safepaws.model;

import jakarta.persistence.*;
import lombok.*;
import safa.safepaws.enums.QuestionType;

@Entity
@Table(name = "question", schema = "public", catalog = "safe_paws")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "question")
    private String question;

    @Column(name = "type")
    private QuestionType type;

    @Column(name = "required")
    private Boolean required;

    @ManyToOne
    @JoinColumn(name = "required_question")
    private Question requiredQuestion;
}

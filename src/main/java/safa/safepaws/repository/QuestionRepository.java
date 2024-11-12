package safa.safepaws.repository;

import org.springframework.stereotype.Repository;
import safa.safepaws.model.Question;

import java.util.Optional;

@Repository
public interface QuestionRepository {
    Optional<Question> findQuestionById(Integer id);
}

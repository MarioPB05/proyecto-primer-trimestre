package safa.safepaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import safa.safepaws.model.Question;

import java.util.List;
import java.util.Optional;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("SELECT q FROM Question q WHERE q.deleted = false ORDER BY q.id")
    List<Question> findUndeletedQuestion();

    Optional<Question> findById(Integer questionId);
  
}

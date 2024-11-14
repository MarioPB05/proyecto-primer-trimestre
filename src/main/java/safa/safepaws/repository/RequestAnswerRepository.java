package safa.safepaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import safa.safepaws.model.RequestAnswer;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestAnswerRepository extends JpaRepository<RequestAnswer, Integer> {
    List<RequestAnswer> findAllByRequestId(Integer requestId);
}

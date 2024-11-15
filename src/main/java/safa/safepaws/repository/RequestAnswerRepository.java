package safa.safepaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import safa.safepaws.model.RequestAnswer;

import java.util.List;

public interface RequestAnswerRepository extends JpaRepository<RequestAnswer, Integer> {

    List<RequestAnswer> findByRequestId(Integer requestId);

}

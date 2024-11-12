package safa.safepaws.repository;

import org.springframework.stereotype.Repository;
import safa.safepaws.model.RequestAnswer;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestAnswerRepository {
    List<RequestAnswer> findAllByRequestId(Integer requestId);
}

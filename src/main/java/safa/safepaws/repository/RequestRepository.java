package safa.safepaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import safa.safepaws.model.Request;
import java.util.Optional;

public interface RequestRepository  extends JpaRepository<Request, Integer> {

    Optional<Request> findAllById(Integer id);

}

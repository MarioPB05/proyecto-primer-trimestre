package safa.safepaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import safa.safepaws.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository  extends JpaRepository<Request, Integer> {

    Optional<Request> findAllById(Integer id);

    @Query("SELECT r FROM Request r WHERE r.client.id = :clientId AND r.deleted = false")
    List<Request> findAllByClientIdSent(@Param("clientId") Integer clientId);

    @Query("SELECT r FROM Request r WHERE r.post.client.id = :clientId AND r.deleted = false")
    List<Request> findAllByClientIdReceived(@Param("clientId") Integer clientId);
}

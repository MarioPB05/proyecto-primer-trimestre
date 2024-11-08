package safa.safepaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import safa.safepaws.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}

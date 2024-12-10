package safa.safepaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import safa.safepaws.model.AdoptionContract;

import java.util.Optional;

@Repository
public interface AdoptionContractRepository extends JpaRepository<AdoptionContract, Integer> {

    Optional<AdoptionContract> findByPostId(Integer postId);

}

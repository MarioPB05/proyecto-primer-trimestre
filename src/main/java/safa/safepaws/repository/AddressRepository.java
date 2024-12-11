package safa.safepaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import safa.safepaws.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}

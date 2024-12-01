package safa.safepaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import safa.safepaws.model.Post;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findTopById(Integer integer);

    @Query("select p from Post p where p.deleted = false and p.status = 0")
    List<Post> findAvailableAdoptions();

    @Query("SELECT p FROM Post p WHERE p.deleted = false AND p.status = 0 AND p.type IN :types")
    List<Post> findPendingPostsByTypes(@Param("types") List<Integer> types);

    List<Post> findAllByClientIdAndDeletedFalseOrderByStatus(Integer clientId);

    @Query("SELECT p FROM Post p WHERE p.deleted = false AND p.status = 0 " +
            "AND p.address.coordinateY BETWEEN :southLat AND :northLat " +
            "AND p.address.coordinateX BETWEEN :southLng AND :northLng")
    List<Post> findWithinBounds(@Param("southLat") Double southLat, @Param("southLng") Double southLng,
                                @Param("northLat") Double northLat, @Param("northLng") Double northLng);
}

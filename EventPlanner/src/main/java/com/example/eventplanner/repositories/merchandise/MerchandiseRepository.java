package com.example.eventplanner.repositories.merchandise;

import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Integer> {
    @Query("SELECT m FROM Merchandise m WHERE m.category.id = :categoryId")
    List<Merchandise> findMerchandiseByCategory(@Param("categoryId") int categoryId);
    Merchandise findByReviewsContaining(Review review); // To find merchandise containing the review

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Merchandise m JOIN m.photos p WHERE p.id = :photoId")
    boolean existsByPhotoId(@Param("photoId") int photoId);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Merchandise m JOIN m.photos p WHERE p.id = :photoId AND m.id = :merchandiseId")
    boolean existsByMerchandiseIdAndPhotoId(@Param("merchandiseId") int merchandiseId, @Param("photoId") int photoId);
}

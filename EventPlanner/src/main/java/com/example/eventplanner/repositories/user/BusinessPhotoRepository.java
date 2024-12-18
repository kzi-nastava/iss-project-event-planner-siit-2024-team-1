package com.example.eventplanner.repositories.user;

import com.example.eventplanner.model.user.BusinessPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BusinessPhotoRepository extends JpaRepository<BusinessPhoto, Integer> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END " +
            "FROM ServiceProvider m JOIN m.photos p WHERE p.id = :photoId")
    boolean existsByPhotoId(@Param("photoId") int photoId);

}

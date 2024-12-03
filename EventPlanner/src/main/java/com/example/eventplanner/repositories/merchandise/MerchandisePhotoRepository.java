package com.example.eventplanner.repositories.merchandise;

import com.example.eventplanner.model.merchandise.MerchandisePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchandisePhotoRepository extends JpaRepository<MerchandisePhoto, Integer> {

}

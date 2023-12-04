package org.guuproject.application.repositories;

import org.guuproject.application.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findImageById(Long id);
}

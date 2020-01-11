package com.kirill.pimenov.repositories;

import com.kirill.pimenov.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    Image findByName(String name);
}

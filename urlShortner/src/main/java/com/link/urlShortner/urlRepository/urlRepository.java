package com.link.urlShortner.urlRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.link.urlShortner.UrlEntity.UrlEntity;

@Repository
public interface urlRepository extends  JpaRepository<UrlEntity, String>{

	Optional<UrlEntity> findByInputUrl(String inputUrl);
    
    Optional<UrlEntity> findById(String shortendurl);

    List<UrlEntity> findByExpirationTimeBefore(LocalDateTime now);

}

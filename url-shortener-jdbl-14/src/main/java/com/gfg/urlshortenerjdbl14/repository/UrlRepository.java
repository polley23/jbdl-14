package com.gfg.urlshortenerjdbl14.repository;

import com.gfg.urlshortenerjdbl14.entity.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends CrudRepository<Url,Long> {
    Optional<Url> findByUrl(String url);
}

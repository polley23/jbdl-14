package com.gfg.urlshortenerjdbl14.repository;

import com.gfg.urlshortenerjdbl14.entity.UrlRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<UrlRedis, String> {
}

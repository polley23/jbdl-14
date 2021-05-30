package com.gfg.urlshortenerjdbl14.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UrlRedis {
    @Id
    private String id;
    private String longUrl;
}

package com.gfg.urlshortenerjdbl14.service;

import com.gfg.urlshortenerjdbl14.entity.Client;
import com.gfg.urlshortenerjdbl14.entity.Url;
import com.gfg.urlshortenerjdbl14.entity.UrlRedis;
import com.gfg.urlshortenerjdbl14.exception.NotFoundException;
import com.gfg.urlshortenerjdbl14.model.UrlRequest;
import com.gfg.urlshortenerjdbl14.repository.ClientRepository;
import com.gfg.urlshortenerjdbl14.repository.RedisRepository;
import com.gfg.urlshortenerjdbl14.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService{
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UrlRepository urlRepository;
    @Override
    public String createShortUrl(UrlRequest urlRequest) {
        Client client = clientRepository.findByClientName(urlRequest.getClientName())
                .orElse(Client.builder().clientName(urlRequest.getClientName()).domain(urlRequest.getDomain())
                        .urls(new HashSet())
                        .build());
        Boolean isPresent = false;
        Long id = null;

        for(Url url : client.getUrls()){
            if(url.getUrl().equals(urlRequest.getUrl())){
                isPresent = true;
                id = url.getId();
                break;
            }
        }
        if(!isPresent){
            Date createdAt = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(createdAt);
            calendar.add(Calendar.DATE,1);
            Date expiredAt = calendar.getTime();
            client.getUrls().add(Url.builder()
                    .url(urlRequest.getUrl())
                    .createdAt(createdAt)
                    .expiryDateTime(expiredAt)
                    .build()
            );
            client = clientRepository.save(client);
            for(Url url : client.getUrls()){
                if(url.getUrl().equals(urlRequest.getUrl())){
                    id = url.getId();
                    break;
                }
            }
            String encryptedId  = encrypt(id);
            return "http://"+client.getDomain()+"/"+encryptedId;
        } else {
            String encryptedId  = encrypt(id);
            return "http://"+client.getDomain()+"/"+encryptedId;
        }
    }

    private String encrypt(Long id){
        String key = String.valueOf(id);
        return Base64.getEncoder().encodeToString(key.getBytes());
    }
    /*
    We will be first searching in cache. And if there is a cache miss, we will search in mysql.
    If it's present in mysql we will store in the cache server.
     */
    @Override
    public String getLongUrl(String encryptedId) throws UnsupportedEncodingException, NotFoundException {
        //fetch it from redis
        Optional<UrlRedis> urlRedis = redisRepository.findById(encryptedId);
        if(urlRedis.isPresent()){
            return urlRedis.get().getLongUrl();
        }
        Long id = decrypt(encryptedId);

        Url url =  urlRepository.findById(id).orElseThrow(()->
                new NotFoundException("Url not present"));
        UrlRedis urlRedisToPersist =  UrlRedis.builder()
                .longUrl(url.getUrl())
                .id(encryptedId)
                .build();
        redisRepository.save(urlRedisToPersist);
        return url.getUrl();
    }

    @Override
    public void deleteExpiredUrl() {
        List<Url> urls= (List<Url>) urlRepository.findAll();
        for(Url url : urls){
            if(url.getExpiryDateTime().before(new Date())){
                urlRepository.delete(url);
                String key = encrypt(url.getId());
                Optional<UrlRedis> urlRedis = redisRepository.findById(key);
                if(urlRedis.isPresent()){
                    redisRepository.delete(urlRedis.get());

                }
            }
        }
    }

    private Long decrypt(String encryptedId) throws UnsupportedEncodingException {
        return Long.parseLong(new String(Base64.getDecoder().decode(encryptedId),"UTF-8"));
    }


}

package com.gfg.urlshortenerjdbl14.service;

import com.gfg.urlshortenerjdbl14.entity.Client;
import com.gfg.urlshortenerjdbl14.entity.Url;
import com.gfg.urlshortenerjdbl14.exception.NotFoundException;
import com.gfg.urlshortenerjdbl14.model.UrlRequest;
import com.gfg.urlshortenerjdbl14.repository.ClientRepository;
import com.gfg.urlshortenerjdbl14.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService{
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

    @Override
    public String getLongUrl(String encryptedId) throws UnsupportedEncodingException, NotFoundException {
        Long id = decrypt(encryptedId);
        Url url =  urlRepository.findById(id).orElseThrow(()->
                new NotFoundException("Url not present"));
        return url.getUrl();
    }

    @Override
    public void deleteExpiredUrl() {
        List<Url> urls= (List<Url>) urlRepository.findAll();
        for(Url url : urls){
            if(url.getExpiryDateTime().before(new Date())){
                urlRepository.delete(url);
            }
        }
    }

    private Long decrypt(String encryptedId) throws UnsupportedEncodingException {
        return Long.parseLong(new String(Base64.getDecoder().decode(encryptedId),"UTF-8"));
    }


}

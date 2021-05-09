package com.gfg.urlshortenerjdbl14.service;

import com.gfg.urlshortenerjdbl14.exception.NotFoundException;
import com.gfg.urlshortenerjdbl14.model.UrlRequest;

import java.io.UnsupportedEncodingException;

public interface UrlShortenerService {
    String createShortUrl(UrlRequest urlRequest);
    String getLongUrl(String encryptedId) throws UnsupportedEncodingException, NotFoundException;
    void deleteExpiredUrl();
}

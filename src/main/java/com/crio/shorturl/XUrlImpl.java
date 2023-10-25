package com.crio.shorturl;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class XUrlImpl implements XUrl {
    private Map<String, String> longToShortUrlMap;
    private Map<String, String> shortToLongUrlMap;
    private Map<String, Integer> hitCountMap;

    public XUrlImpl() {
        longToShortUrlMap = new HashMap<>();
        shortToLongUrlMap = new HashMap<>();
        hitCountMap = new HashMap<>();
    }

    @Override
    public String registerNewUrl(String longUrl) {
        return registerNewUrl(longUrl, generateShortUrl());
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if (shortToLongUrlMap.containsKey(shortUrl)) {
            return null; 
        }

        if (longToShortUrlMap.containsKey(longUrl)) {
            return longToShortUrlMap.get(longUrl); 
        }

        longToShortUrlMap.put(longUrl, shortUrl);
        shortToLongUrlMap.put(shortUrl, longUrl);
        hitCountMap.put(longUrl, 0); 

        return "http://short.url/" + shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        if (shortToLongUrlMap.containsKey(shortUrl)) {
            String longUrl = shortToLongUrlMap.get(shortUrl);
            int hitCount = hitCountMap.getOrDefault(longUrl, 0);
            hitCountMap.put(longUrl, hitCount + 1); 
            return longUrl;
        }
        return null; 
    }

    @Override
    public String delete(String longUrl) {
        if (longToShortUrlMap.containsKey(longUrl)) {
            String shortUrl = longToShortUrlMap.get(longUrl);
            longToShortUrlMap.remove(longUrl);
            shortToLongUrlMap.remove(shortUrl);
        
            return longUrl;
        }
        return null; 
    }
    

    public Integer getHitCount(String longUrl) {
        return hitCountMap.getOrDefault(longUrl, 0);
    }


    private String generateShortUrl() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortUrl = new StringBuilder(9);
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            int index = rand.nextInt(characters.length());
            shortUrl.append(characters.charAt(index));
        }
        return shortUrl.toString();
    }
}


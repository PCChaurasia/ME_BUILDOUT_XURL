package com.crio.shorturl;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class XUrlImpl implements XUrl {
    private Map<String, String> longToShortUrl;
    private Map<String, String> shortToLongUrl;
    private Map<String, Integer> hitCountMap;

    public XUrlImpl() {
        longToShortUrl = new HashMap<>();
        shortToLongUrl = new HashMap<>();
        hitCountMap = new HashMap<>();
    }

    @Override
    public String registerNewUrl(String longUrl) {
        return registerNewUrl(longUrl, generateShortUrl());
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if (shortToLongUrl.containsKey(shortUrl)) {
            return null; 
        }

        if (longToShortUrl.containsKey(longUrl)) {
            return longToShortUrl.get(longUrl); 
        }

        longToShortUrl.put(longUrl, shortUrl);
        shortToLongUrl.put(shortUrl, longUrl);
        hitCountMap.put(longUrl, 0); 

        return "http://short.url/" + shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        if (shortToLongUrl.containsKey(shortUrl)) {
            String longUrl = shortToLongUrl.get(shortUrl);
            int hitCount = hitCountMap.getOrDefault(longUrl, 0);
            hitCountMap.put(longUrl, hitCount + 1); 
            return longUrl;
        }
        return null; 
    }

    @Override
    public String delete(String longUrl) {
        if (longToShortUrl.containsKey(longUrl)) {
            String shortUrl = longToShortUrl.get(longUrl);
            longToShortUrl.remove(longUrl);
            shortToLongUrl.remove(shortUrl);
        
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


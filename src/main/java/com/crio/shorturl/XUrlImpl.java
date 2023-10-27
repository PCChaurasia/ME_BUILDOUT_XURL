package com.crio.shorturl;

import java.util.HashMap;
import java.util.Random;

public class XUrlImpl implements XUrl {

    HashMap<String, String> shortToLong;
    HashMap<String, pair<String, Integer>> longToShort;
    SlugGenerator slugGenerator;
    private static final String URL = "http://short.url/";
    public XUrlImpl(){
        shortToLong = new HashMap<>();
        longToShort = new HashMap<>();
        slugGenerator = new SlugGenerator();
    }

     @Override
    public
    String registerNewUrl(String longUrl){
        if(!longToShort.containsKey(longUrl)){
            String slug = slugGenerator.generatorRandomSlug();
            shortToLong.put(URL+slug, longUrl);
            longToShort.put(longUrl, new pair<>(URL+slug, 0));

        }
        return longToShort.get(longUrl).getKey();

    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl){
        if(shortToLong.containsKey(shortUrl)){
            return null;
        }
        else{
            shortToLong.put(shortUrl, longUrl);
            longToShort.put(longUrl, new pair<String, Integer> (shortUrl, 0 ));
            return longToShort.get(longUrl).getKey();
        }

    }

    @Override
    public String getUrl(String shortUrl){
        if(shortToLong.containsKey(shortUrl)){
            String longUrl = shortToLong.get(shortUrl);
            longToShort.get(longUrl).setValue(longToShort.get(longUrl).getValue()+1);
            return shortToLong.get(shortUrl);
        }
        else{
            return null;
        }
       
    }
    @Override
    public Integer getHitCount(String longUrl){

        if(longToShort.containsKey(longUrl)){
            return longToShort.get(longUrl).getValue();
        }
        return 0;
    }
    @Override
    public String delete(String longUrl){
        shortToLong.remove(longToShort.get(longUrl).getKey());
        return null;

    }



    
    public class pair<T, T1> {
        T key;
        T1 value;

        public pair(T key, T1 value) {
            this.key = key;
            this.value = value;
        }

        public T getKey() {
            return key;
        }

        public T1 getValue() {
            return value;
        }

        public void setKey(T key) {
            this.key = key;
        }

        public void setValue(T1 value) {
            this.value = value;
        }
    }

    public class SlugGenerator {
        private static final int slug_no = 9;
        private static final String Alphabet =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        private Random random;

    public SlugGenerator(){
        random = new Random();
    }

        public String generatorRandomSlug() {
            char[] result = new char[slug_no];
            for (int i = 0; i < slug_no; i++) {
                int randomIndex = random.nextInt(Alphabet.length() - 1);
                result[i] = Alphabet.charAt(randomIndex);
            }
            return new String(result);

        }
    }

   

}

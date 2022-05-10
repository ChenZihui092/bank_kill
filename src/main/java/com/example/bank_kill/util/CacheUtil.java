package com.example.bank_kill.util;

public class CacheUtil {
    public static final String generateCacheKey(String...keys){
        String res = "bank:";
        for(String i : keys){
            res +=res + i +":";
        }
        return res;
    }
    public static final String getCacheKey(String prefix,String i){
        return generateCacheKey(prefix,i);
    }
}

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

    public static String generateKey(String ... arg){
        StringBuffer stringBuffer = new StringBuffer("bank_kill");
        for (String s : arg){
            stringBuffer.append(":"+s);
        }
        return stringBuffer.toString();
    }
}

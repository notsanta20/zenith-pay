package com.santa.account_service.utlis;

import java.util.UUID;

public class LongNumberGenerator {
    public static String getID(int n){
        return UUID.randomUUID().toString().replace("-", "").substring(0,n).toUpperCase();
    }
}


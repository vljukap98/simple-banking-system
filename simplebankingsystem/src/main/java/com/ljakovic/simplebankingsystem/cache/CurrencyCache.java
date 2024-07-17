package com.ljakovic.simplebankingsystem.cache;

import com.ljakovic.simplebankingsystem.hnb.dto.HnbRateDto;

import java.util.HashMap;
import java.util.Map;

public class CurrencyCache {

    private Map<String, HnbRateDto> currencyMap = new HashMap<>();

    private static CurrencyCache instance;

    private CurrencyCache() {
    }

    public static synchronized CurrencyCache getInstance() {
        if (instance == null) {
            instance = new CurrencyCache();
        }
        return instance;
    }

    public Map<String, HnbRateDto> getCurrencyMap() {
        return currencyMap;
    }
}

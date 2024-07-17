package com.ljakovic.simplebankingsystem.service.scheduler;

import com.ljakovic.simplebankingsystem.cache.CurrencyCache;
import com.ljakovic.simplebankingsystem.hnb.HnbClient;
import com.ljakovic.simplebankingsystem.hnb.dto.HnbRateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@EnableScheduling
public class CurrencyScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(CurrencyScheduler.class);

    private final HnbClient hnbClient;

    public CurrencyScheduler(HnbClient hnbClient) {
        this.hnbClient = hnbClient;
    }

    @Scheduled(cron = "0 0 12 * * 1-5") // start this scheduler every week day at 12
    public void startScheduler() {
        LOG.info("Starting currency-scheduler");
        this.getCurrencies();
        LOG.info("Finished currency-scheduler");
    }

    private void getCurrencies() {
        final CurrencyCache cache = CurrencyCache.getInstance();
        final List<HnbRateDto> hnbRatesDtoList = Arrays.stream(hnbClient.getRates()).toList();
        hnbRatesDtoList.forEach(rate -> cache.getCurrencyMap().put(rate.getCurrency(), rate));
    }
}

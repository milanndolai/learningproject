package com.link.urlShortner.sheduled;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.link.urlShortner.UrlEntity.*;
import com.link.urlShortner.urlRepository.*;
import com.link.urlShortner.urlService.*;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CleanupScheduler {
    @Autowired
    private  urlRepository urlrepository;
    @Autowired
    private redisService redisservice;

    @Scheduled(cron = "0 0 * * * *") // Runs every hour
    public void cleanUpExpiredUrls() {
        LocalDateTime now = LocalDateTime.now();
        List<UrlEntity> expiredUrls = urlrepository.findByExpirationTimeBefore(now);
        for (UrlEntity expiredUrl : expiredUrls) {
            urlrepository.delete(expiredUrl);
            redisservice.deleteUrl(expiredUrl.getShortendUrl());
        }
    }
}

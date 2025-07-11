package com.link.urlShortner.urlService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.link.urlShortner.UrlEntity.UrlEntity;
import com.link.urlShortner.urlRepository.urlRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UrlShortnerService {
   
	@Autowired
	private redisService redisservice;
	
	@Autowired
	private urlRepository urlrepository;
	
	private static final long TTL_SECOND=86400;
	
	public Optional<String> getoriginalUrl(String shortendUrl){
		Optional<String> cachedurl=redisservice.getUrl(shortendUrl);
		if(cachedurl.isPresent()) {
			System.out.print("redi");
			return cachedurl;
		}
		
		Optional<UrlEntity> urldb=urlrepository.findById(shortendUrl);
		if(urldb.isPresent()) {
			UrlEntity entity=urldb.get();
			redisservice.saveUrl(shortendUrl, entity.getInputUrl(), TTL_SECOND);
			System.out.print("db");
			return Optional.of(entity.getInputUrl());
		}
		System.out.print("none");
		return Optional.empty();
	}
	
	public void deleteUrl(String shortendUrl) {
		redisservice.deleteUrl(shortendUrl);
	}
	
	public String urlShort(String inputUrl) {
		Optional<UrlEntity> existsurl=urlrepository.findByInputUrl(inputUrl);
		
		if(existsurl.isPresent()) {
			System.out.println("hi im redis");
			return existsurl.get().getShortendUrl();
		}
		String shortUrl=Short(inputUrl);
		
		UrlEntity url = UrlEntity.builder()
			    .inputUrl(inputUrl)  
			    .shortendUrl(shortUrl)  
			    .expirationTime(LocalDateTime.now().plusDays(7))
			    .build();

		urlrepository.save(url);
		redisservice.saveUrl(shortUrl, inputUrl, TTL_SECOND);
		
		return shortUrl;
	}

	private String Short(String originalUrl) {
	    return UUID.nameUUIDFromBytes(originalUrl.getBytes()).toString().replace("-", "").substring(0, 6);
	}

}

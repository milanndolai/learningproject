package com.link.urlShortner.urlService;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.link.urlShortner.urlRepository.urlRepository;

@Service
public class redisService {
    @Autowired
	private StringRedisTemplate redisTemplate;
    @Autowired
    private urlRepository urlrepository;

	public void saveUrl(String shortendUrl, String inputUrl, long ttl) {
		
		ValueOperations<String, String> ops=redisTemplate.opsForValue();
		
		ops.set(shortendUrl, inputUrl, Duration.ofSeconds(ttl));
	}
	
	public Optional<String> getUrl(String inputUrl){
		
		ValueOperations<String, String> ops=redisTemplate.opsForValue();
		String url =ops.get(inputUrl);
		
		return Optional.ofNullable(url);
	}
	
	public void deleteUrl(String shortendUrl) {
		urlrepository.deleteById(shortendUrl);
		redisTemplate.delete(shortendUrl);
	}
    
    
}

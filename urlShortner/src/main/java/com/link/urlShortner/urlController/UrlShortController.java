package com.link.urlShortner.urlController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.link.urlShortner.UrlEntity.UrlEntity;
import com.link.urlShortner.urlService.UrlShortnerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/url")
public class UrlShortController {
	
    @Autowired
	private UrlShortnerService urlshortservice;
    
    @PostMapping("/short")
    public ResponseEntity<String> shortUrl(@RequestBody UrlEntity request){
    	
    	String shorturl=urlshortservice.urlShort(request.getInputUrl());
    	return ResponseEntity.ok(shorturl);
    }
    
    @GetMapping("/originalUrl")
    public ResponseEntity<String> getOriginalUrl(@RequestParam String shortendUrl){
    	
    	Optional<String>url=urlshortservice.getoriginalUrl(shortendUrl);
    	if(url.isPresent()) {
    		return ResponseEntity.ok(url.get());
    	}
    	
    	return ResponseEntity.notFound().build();
    	
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUrl(@RequestParam String shortendUrl) {
    	
    	urlshortservice.deleteUrl(shortendUrl);
    	return ResponseEntity.ok("URL DELETED");
    }
}

package com.link.urlShortner.UrlEntity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Table(name="url_mapping",schema="urlentity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlEntity {
	
	@Id
	String shortendUrl;
	
    @Column(nullable=false, unique=true)
	String inputUrl;
    
	@Column(nullable=true)
    LocalDateTime expirationTime;
    
    
}

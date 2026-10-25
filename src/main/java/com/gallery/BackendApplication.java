package com.gallery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@MapperScan({
	"com.gallery.api.codes.mapper",
	"com.gallery.api.artist.mapper",
	"com.gallery.api.member.mapper",
	"com.gallery.api.role.mapper",
	"com.gallery.api.file.mapper",
	"com.gallery.api.ntcn.mapper",
	"com.gallery.api.jdgmn.mapper",
	"com.gallery.api.prdct.mapper"
})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}

package com.gallery.commn.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadProperties {
    private String rootPath;
    
}

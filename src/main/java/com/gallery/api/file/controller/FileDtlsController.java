package com.gallery.api.file.controller;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gallery.api.file.dto.out.FileDtlsOutDto;
import com.gallery.api.file.service.FileDtlsService;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.config.FileUploadProperties;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileDtlsController {
    private final FileDtlsService fileDtlsService;

    private final FileUploadProperties fileUploadProperties;

    private static Logger logger = LoggerFactory.getLogger(FileDtlsController.class);

    /**
     * 파일 Resoure 정보와 Content Type 조회
     * @param fileNo
     * @param sn
     * @return
     */
    @GetMapping("/{fileNo}/{sn}")
    public ResponseEntity<Resource> displayFile(@PathVariable String fileNo, @PathVariable Integer sn) {
        /******************************
         * 파일번호와 순번으로 파일 내역 조회
         ******************************/
        FileDtlsOutDto fileDtlsOutDto = fileDtlsService.getFileDtlsByFileNoAndSn(fileNo, sn);

        StringBuffer sb = new StringBuffer();
        sb.append(fileDtlsOutDto.getStreFileNm());
        sb.append(".");
        sb.append(fileDtlsOutDto.getFileExtsn());
        
        Path path = Path.of(fileUploadProperties.getRootPath()
                        , fileDtlsOutDto.getFileLc()
                        , sb.toString());

        Resource resource = new FileSystemResource(path);
        if(!resource.exists()) {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileDtlsOutDto.getCntntsTyCode())).body(resource);
    }
}

package com.gallery.commn.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gallery.api.codes.dto.out.CmmnCdOutDto;
import com.gallery.api.codes.service.CmmnCdService;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
    private final CmmnCdService cmmnCdService;

    /**
     * 확장자 및 컨텐트 유형 검증
     * @param multipartFile
     * @param groupCode
     */
    public void vrifyCntntsTyExtsn(MultipartFile multipartFile, String groupCode) {
        if(multipartFile == null || multipartFile.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_NULL_ISEMPTY);
        }

        /******************************
         * groupCd 의 코드 내용들 모두 출력
         ******************************/
        List<CmmnCdOutDto> cmmnCdOutDtoList = cmmnCdService.getCmmnCdList(groupCode);
        if(cmmnCdOutDtoList.size() == 0) {
            throw new CustomException(ErrorCode.GROUP_CD_INFO_NOT_FOUND);
        }

        /******************************
         * 컨텐트 유형 검증
         ******************************/
        String contentType = multipartFile.getContentType();
        boolean vrify = cmmnCdOutDtoList.stream().anyMatch((dto) -> dto.getRefer1() != null && dto.getRefer1().equalsIgnoreCase(contentType));
        if(vrify == false) {
            throw new CustomException(ErrorCode.CONTENT_TYPE_ERROR);
        }

        /******************************
         * 파일 확장자 검증
         ******************************/
        String fileName = multipartFile.getOriginalFilename();
        String extsn = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        vrify = cmmnCdOutDtoList.stream().anyMatch((dto) -> dto.getCode().toLowerCase().equals(extsn));
        if(vrify == false) {
            throw new CustomException(ErrorCode.EXTSN_ERROR);
        }

        BufferedImage image;
        try {
            image = ImageIO.read(multipartFile.getInputStream());

            if(image == null) {
                throw new CustomException(ErrorCode.IMAGE_BAD_REQUEST);
            }
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IMAGE_BAD_REQUEST);
        }
    }
}

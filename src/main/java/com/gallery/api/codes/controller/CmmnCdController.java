package com.gallery.api.codes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gallery.api.codes.dto.out.CmmnCdOutDto;
import com.gallery.api.codes.service.CmmnCdService;
import com.gallery.commn.ApiResponse;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CmmnCdController {
    private static Logger logger = LoggerFactory.getLogger(CmmnCdController.class);

    // private final CmmnCdMemLoaderService cmmnCdMemLoaderService;

    private final CmmnCdService cmmnCdService;

    /**
     * groupCd 의 코드 내용 모두 리턴
     * @param groupCode
     * @return
     */
    @GetMapping("/{groupCode}")
    public ResponseEntity<ApiResponse<List<CmmnCdOutDto>>> getCmmnCdList(@PathVariable String groupCode) {
        if(groupCode == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        // logger.info("시작: " + cmmnCdMemLoaderService.getCmmnCdList(groupCode));

        /******************************
         * groupCd 의 코드 내용들 모두 출력
         ******************************/
        // return ResponseEntity.ok(ApiResponse.success(cmmnCdMemLoaderService.getCmmnCdList(groupCode)));
        return ResponseEntity.ok(ApiResponse.success(cmmnCdService.getCmmnCdList(groupCode)));
    }
}

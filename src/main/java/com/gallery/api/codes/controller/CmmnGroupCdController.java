package com.gallery.api.codes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gallery.api.codes.dto.CmmnGroupCdDto;
import com.gallery.api.codes.service.CmmnGroupCdService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CmmnGroupCdController {
    private final CmmnGroupCdService cmmnGroupCdService;

    @RequestMapping("/allcmmngroupcd")
    public String getAllCmmnGroupCd() {
        List<CmmnGroupCdDto> cmmnGroupCdDtoList = cmmnGroupCdService.getAllCmmnGroupCd();
        String groupCd = null;

        if(cmmnGroupCdDtoList.size() > 0) {
            groupCd = cmmnGroupCdDtoList.get(0).getGroupCode();
        }
        return groupCd;
    }
}

package com.gallery.api.codes.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gallery.api.codes.dto.out.CmmnCdOutDto;
import com.gallery.api.codes.mapper.CmmnCdMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CmmnCdService {
    private final CmmnCdMapper cmmnCdMapper;

    /**
     * groupCd 의 코드 내용들 모두 출력
     * @param groupCode
     * @return
     */
    @Cacheable(
        value = "commonCodes",
        key = "#groupCode"
    )
    public List<CmmnCdOutDto> getCmmnCdList(String groupCode) {
        return cmmnCdMapper.selectCmmnCdList(groupCode);
    }
}

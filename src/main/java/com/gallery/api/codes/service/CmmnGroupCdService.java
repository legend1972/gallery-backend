package com.gallery.api.codes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gallery.api.codes.dto.CmmnGroupCdDto;
import com.gallery.api.codes.entity.CmmnGroupCdEntity;
import com.gallery.api.codes.mapper.CmmnGroupCdMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CmmnGroupCdService {
    private final CmmnGroupCdMapper commGroupCdMapper;

    public List<CmmnGroupCdDto> getAllCmmnGroupCd() {
        List<CmmnGroupCdDto> cmmnGroupCdDtoList = new ArrayList<>();

        List<CmmnGroupCdEntity> cmmnGroupCdEntityList =  commGroupCdMapper.selectAllCmmnGroupCd();
        if(cmmnGroupCdEntityList.size() > 0) {
            for(CmmnGroupCdEntity cmmnGroupCdEntity : cmmnGroupCdEntityList) {
                cmmnGroupCdDtoList.add(CmmnGroupCdDto
                    .builder()
                        .groupCode(cmmnGroupCdEntity.getGroupCode())
                        .groupNm(cmmnGroupCdEntity.getGroupNm())
                        .columnId(cmmnGroupCdEntity.getColumnId())
                        .rm(cmmnGroupCdEntity.getRm())
                    .build());
            }
        }

        return cmmnGroupCdDtoList;
    }

}

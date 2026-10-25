package com.gallery.api.prdct.service;

import org.springframework.stereotype.Service;

import com.gallery.api.prdct.dto.in.PrdctActInDto;
import com.gallery.api.prdct.dto.out.PrdctActOutDto;
import com.gallery.api.prdct.entity.PrdctActEntity;
import com.gallery.api.prdct.mapper.PrdctActMapper;

import lombok.RequiredArgsConstructor;

/**
 * 작품활동서비스
 * PrdctActService
 */
@Service
@RequiredArgsConstructor
public class PrdctActService {
    /**
     * 작품활동Mapper
     */
    public final PrdctActMapper prdctActMapper;

    /**
     * 작품활동등록 및 작품활동번호 조회
     * @param prdctActInDto
     * @return
     */
    public String createPrdctAct(PrdctActInDto prdctActInDto) {
        PrdctActEntity prdctActEntity = PrdctActEntity
            .builder()
                .prdctSn(prdctActInDto.getPrdctSn())
                .mberNo(prdctActInDto.getMberNo())
                .actTyCode(prdctActInDto.getActTyCode())
                .actNm(prdctActInDto.getActNm())
                .actBgnde(prdctActInDto.getActBgnde())
                .actEndde(prdctActInDto.getActEndde())
                .actPlace(prdctActInDto.getActPlace())
                .reprsntPrdctFileNo(prdctActInDto.getReprsntPrdctFileNo())
                .actIntrcn(prdctActInDto.getActIntrcn())
                .othbcAt(prdctActInDto.getOthbcAt())
                .useAt(prdctActInDto.getUseAt())
                .creatMberNo(prdctActInDto.getCreatMberNo())
                .changeMberNo(prdctActInDto.getChangeMberNo())
            .build();

        prdctActMapper.insertPrdctAct(prdctActEntity);

        return prdctActEntity.getPrdctActNo();
    }

    /**
     * 다음작품순번 및 작품활동번호 조회
     * @param mberNo
     * @param useAt
     * @return
     */
    public PrdctActOutDto getNextPrdctSnByMberNo(String mberNo) {
        PrdctActEntity prdctActEntity = prdctActMapper.selectNextPrdctSnByMberNo(mberNo);
        if(prdctActEntity == null) {
            return null;
        }

        return PrdctActOutDto
                .builder()
                    .prdctActNo(prdctActEntity.getPrdctActNo())
                    .prdctSn(prdctActEntity.getPrdctSn())
                .build();
    }

    /**
     * 작품활동등록
     * @param prdctActInDto
     */
    public void createNextPrdctAct(PrdctActInDto prdctActInDto) {
        prdctActMapper.insertNextPrdctAct(PrdctActEntity
            .builder()
                .prdctActNo(prdctActInDto.getPrdctActNo())
                .prdctSn(prdctActInDto.getPrdctSn())
                .mberNo(prdctActInDto.getMberNo())
                .actTyCode(prdctActInDto.getActTyCode())
                .actNm(prdctActInDto.getActNm())
                .actBgnde(prdctActInDto.getActBgnde())
                .actEndde(prdctActInDto.getActEndde())
                .actPlace(prdctActInDto.getActPlace())
                .reprsntPrdctFileNo(prdctActInDto.getReprsntPrdctFileNo())
                .actIntrcn(prdctActInDto.getActIntrcn())
                .othbcAt(prdctActInDto.getOthbcAt())
                .useAt(prdctActInDto.getUseAt())
                .creatMberNo(prdctActInDto.getCreatMberNo())
                .changeMberNo(prdctActInDto.getChangeMberNo())
            .build());
    }
}

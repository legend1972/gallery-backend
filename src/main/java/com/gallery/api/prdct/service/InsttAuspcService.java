package com.gallery.api.prdct.service;

import org.springframework.stereotype.Service;

import com.gallery.api.prdct.dto.in.InsttAuspcInDto;
import com.gallery.api.prdct.dto.out.InsttAuspcOutDto;
import com.gallery.api.prdct.entity.InsttAuspcEntity;
import com.gallery.api.prdct.mapper.InsttAuspcMapper;

import lombok.RequiredArgsConstructor;

/**
 * 기관주최서비스
 * InsttAuspcService
 */
@Service
@RequiredArgsConstructor
public class InsttAuspcService {
    private final InsttAuspcMapper insttAuspcMapper;

    /**
     * 기관주최등록 및 기관주최번호 조회
     * @param insttAuspcInDto
     * @return
     */
    public String createInsttAuspc(InsttAuspcInDto insttAuspcInDto) {
        InsttAuspcEntity insttAuspcEntity = InsttAuspcEntity
            .builder()
                .sn(insttAuspcInDto.getSn())
                .prdctActNo(insttAuspcInDto.getPrdctActNo())
                .prdctSn(insttAuspcInDto.getPrdctSn())
                .mberNo(insttAuspcInDto.getMberNo())
                .insttAuspcNm(insttAuspcInDto.getInsttAuspcNm())
                .roleCode(insttAuspcInDto.getRoleCode())
                .partcptnBgnde(insttAuspcInDto.getPartcptnBgnde())
                .partcptnEndde(insttAuspcInDto.getPartcptnEndde())
                .insttAuspcIntrcn(insttAuspcInDto.getInsttAuspcIntrcn())
                .reprsntFileNo(insttAuspcInDto.getReprsntFileNo())
                .adres(insttAuspcInDto.getAdres())
                .telno(insttAuspcInDto.getTelno())
                .hmpg(insttAuspcInDto.getHmpg())
                .creatMberNo(insttAuspcInDto.getCreatMberNo())
                .changeMberNo(insttAuspcInDto.getChangeMberNo())
            .build();
        
        insttAuspcMapper.insertInsttAuspc(insttAuspcEntity);

        return insttAuspcEntity.getInsttAuspcNo();
    }

    /**
     * 다음순번 및 기관주최번호 조회
     * @param prdctActNo
     * @param prdctSn
     * @param mberNo
     * @return
     */
    public InsttAuspcOutDto getNextSnByMberNo(String prdctActNo, Integer prdctSn, String mberNo) {
        InsttAuspcEntity insttAuspcEntity = insttAuspcMapper.selectNextSnByMberNo(prdctActNo, prdctSn, mberNo);
        if(insttAuspcEntity == null) {
            return null;
        }

        return InsttAuspcOutDto
                .builder()
                    .insttAuspcNo(insttAuspcEntity.getInsttAuspcNo())
                    .sn(insttAuspcEntity.getSn())
                .build();
    }

    /**
     * 기관주최등록
     * @param insttAuspcInDto
     */
    public void createNextInsttAuspc(InsttAuspcInDto insttAuspcInDto) {
        insttAuspcMapper.insertNextInsttAuspc(InsttAuspcEntity
            .builder()
                .insttAuspcNo(insttAuspcInDto.getInsttAuspcNo())
                .sn(insttAuspcInDto.getSn())
                .prdctActNo(insttAuspcInDto.getPrdctActNo())
                .prdctSn(insttAuspcInDto.getPrdctSn())
                .mberNo(insttAuspcInDto.getMberNo())
                .insttAuspcNm(insttAuspcInDto.getInsttAuspcNm())
                .roleCode(insttAuspcInDto.getRoleCode())
                .partcptnBgnde(insttAuspcInDto.getPartcptnBgnde())
                .partcptnEndde(insttAuspcInDto.getPartcptnEndde())
                .insttAuspcIntrcn(insttAuspcInDto.getInsttAuspcIntrcn())
                .reprsntFileNo(insttAuspcInDto.getReprsntFileNo())
                .adres(insttAuspcInDto.getAdres())
                .telno(insttAuspcInDto.getTelno())
                .hmpg(insttAuspcInDto.getHmpg())
                .creatMberNo(insttAuspcInDto.getCreatMberNo())
                .changeMberNo(insttAuspcInDto.getChangeMberNo())
            .build());
    }
}

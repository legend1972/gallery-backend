package com.gallery.api.member.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gallery.api.artist.controller.WritrInfoController;
import com.gallery.api.member.dto.MberNtcnIemSetupBindingDto;
import com.gallery.api.member.dto.in.MberNtcnIemSetupInDto;
import com.gallery.api.member.dto.out.MberNtcnIemSetupOutDto;
import com.gallery.api.member.entity.MberNtcnIemSetupEntity;
import com.gallery.api.member.mapper.MberNtcnIemSetupMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MberNtcnIemSetupService {
    private final MberNtcnIemSetupMapper mberNtcnIemSetupMapper;

    private static Logger logger = LoggerFactory.getLogger(MberNtcnIemSetupService.class);

    /**
     * 회원알림항목설정 등록
     * @param mberNtcnIemSetupInDto
     * @return
     */
    public int createListMberNtcnIemSetup(MberNtcnIemSetupInDto mberNtcnIemSetupInDto, String roleId) {
        int cnt = mberNtcnIemSetupMapper.insertMberNtcnIemSetupList(MberNtcnIemSetupEntity
            .builder()
                .mberNo(mberNtcnIemSetupInDto.getMberNo())
                .aplctnRecptnAt(mberNtcnIemSetupInDto.getAplctnRecptnAt())
                .emailRecptnAt(mberNtcnIemSetupInDto.getEmailRecptnAt())
                .smsRecptnAt(mberNtcnIemSetupInDto.getSmsRecptnAt())
                .creatMberNo(mberNtcnIemSetupInDto.getCreatMberNo())
                .changeMberNo(mberNtcnIemSetupInDto.getChangeMberNo())
            .build(),
            roleId
        );

        if(cnt == 0) {
            throw new CustomException(ErrorCode.NTCN_SETUP_IEM_ROLE_NOT_FOUND);
        }

        return cnt;
    }

    /**
     * 회원알림항목설정 조회
     * @param mberNo
     * @return
     */
    public List<MberNtcnIemSetupOutDto> getMberNtcnIemSetupByMberNo(String mberNo) {
        List<MberNtcnIemSetupEntity> mberNtcnIemSetupEntityList = mberNtcnIemSetupMapper.selectMberNtcnIemSetupByMberNo(mberNo);
        if(mberNtcnIemSetupEntityList.size() == 0) {
            throw new CustomException(ErrorCode.NTCN_IEM_SETUP_NOT_FOUND);
        }
        
        List<MberNtcnIemSetupOutDto> list = new ArrayList<>();
        for(MberNtcnIemSetupEntity mberNtcnIemSetupEntity: mberNtcnIemSetupEntityList) {
            list.add(MberNtcnIemSetupOutDto
                .builder()
                    .mberNo(mberNtcnIemSetupEntity.getMberNo())
                    .ntcnSetupCode(mberNtcnIemSetupEntity.getNtcnSetupCode())
                    .aplctnRecptnAt(mberNtcnIemSetupEntity.getAplctnRecptnAt())
                    .emailRecptnAt(mberNtcnIemSetupEntity.getEmailRecptnAt())
                    .smsRecptnAt(mberNtcnIemSetupEntity.getSmsRecptnAt())
                    .creatMberNo(mberNtcnIemSetupEntity.getCreatMberNo())
                    .creatDt(mberNtcnIemSetupEntity.getCreatDt())
                    .changeMberNo(mberNtcnIemSetupEntity.getChangeMberNo())
                    .changeDt(mberNtcnIemSetupEntity.getChangeDt())
                .build());
        }

        return list;
    }

    /**
     * 회원알림항목설정 및 알림설정항목 조회
     * @param mberNo
     * @param roleId
     * @return
     */
    public List<MberNtcnIemSetupBindingDto> getListMberNtcnIemSetupByMberNoAndRoldId(String mberNo, String roleId) {
        List<MberNtcnIemSetupBindingDto> mberNtcnIemSetupBindingDtoList = mberNtcnIemSetupMapper.selectListMberNtcnIemSetupByMberNoAndRoldId(mberNo, roleId);
        if(mberNtcnIemSetupBindingDtoList.size() == 0) {
            throw new CustomException(ErrorCode.NTCN_IEM_SETUP_NOT_FOUND);
        }

        return mberNtcnIemSetupBindingDtoList;
    }

    /**
     * 회원알림항목설정 변경 혹은 등록
     * @param mberNtcnIemSetupInDto
     */
    public void createMberNtcnIemSetup(MberNtcnIemSetupInDto mberNtcnIemSetupInDto) {
        int cnt = mberNtcnIemSetupMapper.updateMberNtcnIemSetupByMberNoAndNtcnSetupCode(MberNtcnIemSetupEntity
            .builder()
                .mberNo(mberNtcnIemSetupInDto.getMberNo())
                .ntcnSetupCode(mberNtcnIemSetupInDto.getNtcnSetupCode())
                .aplctnRecptnAt(mberNtcnIemSetupInDto.getAplctnRecptnAt())
                .emailRecptnAt(mberNtcnIemSetupInDto.getEmailRecptnAt())
                .smsRecptnAt(mberNtcnIemSetupInDto.getSmsRecptnAt())
                .creatMberNo(mberNtcnIemSetupInDto.getCreatMberNo())
                .changeMberNo(mberNtcnIemSetupInDto.getChangeMberNo())
            .build());

        if(cnt == 0) {
            mberNtcnIemSetupMapper.insertMberNtcnIemSetup(MberNtcnIemSetupEntity
            .builder()
                .mberNo(mberNtcnIemSetupInDto.getMberNo())
                .ntcnSetupCode(mberNtcnIemSetupInDto.getNtcnSetupCode())
                .aplctnRecptnAt(mberNtcnIemSetupInDto.getAplctnRecptnAt())
                .emailRecptnAt(mberNtcnIemSetupInDto.getEmailRecptnAt())
                .smsRecptnAt(mberNtcnIemSetupInDto.getSmsRecptnAt())
                .creatMberNo(mberNtcnIemSetupInDto.getCreatMberNo())
                .changeMberNo(mberNtcnIemSetupInDto.getChangeMberNo())
            .build());
        }
    }
}

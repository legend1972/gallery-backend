package com.gallery.api.member.service;

import org.springframework.stereotype.Service;

import com.gallery.api.member.dto.in.MberNtcnSetupInDto;
import com.gallery.api.member.dto.out.MberNtcnSetupOutDto;
import com.gallery.api.member.entity.MberNtcnSetupEntity;
import com.gallery.api.member.mapper.MberNtcnSetupMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MberNtcnSetupService {
    private final MberNtcnSetupMapper mberNtcnSetupMapper;

    /**
     * 회원알림설정 등록
     * @param mberNtcnSetupInDto
     * @return
     */
    public int createMberNtcnSetup(MberNtcnSetupInDto mberNtcnSetupInDto) {
        return mberNtcnSetupMapper.insertMberNtcnSetup(MberNtcnSetupEntity
            .builder()
                .mberNo(mberNtcnSetupInDto.getMberNo())
                .allRecptnAt(mberNtcnSetupInDto.getAllRecptnAt())
                .aplctnRecptnAt(mberNtcnSetupInDto.getAplctnRecptnAt())
                .emailRecptnAt(mberNtcnSetupInDto.getEmailRecptnAt())
                .smsRecptnAt(mberNtcnSetupInDto.getSmsRecptnAt())
                .ntcnTimeLmttAt(mberNtcnSetupInDto.getNtcnTimeLmttAt())
                .ntcnBeginTime(mberNtcnSetupInDto.getNtcnBeginTime())
                .ntcnEndTime(mberNtcnSetupInDto.getNtcnEndTime())
                .creatMberNo(mberNtcnSetupInDto.getCreatMberNo())
                .changeMberNo(mberNtcnSetupInDto.getChangeMberNo())
            .build()
        );
    }

    /**
     * 회원알림설정 조회
     * @param mberNo
     * @return
     */
    public MberNtcnSetupOutDto getMberNtcnSetupByMberNo(String mberNo) {
        MberNtcnSetupEntity mberNtcnSetupEntity = mberNtcnSetupMapper.selectMberNtcnSetupByMberNo(mberNo);
        if(mberNtcnSetupEntity == null) {
            throw new CustomException(ErrorCode.NTCN_SETUP_NOT_FOUND);
        }

        return MberNtcnSetupOutDto
                .builder()
                    .mberNo(mberNtcnSetupEntity.getMberNo())
                    .allRecptnAt(mberNtcnSetupEntity.getAllRecptnAt())
                    .aplctnRecptnAt(mberNtcnSetupEntity.getAplctnRecptnAt())
                    .emailRecptnAt(mberNtcnSetupEntity.getEmailRecptnAt())
                    .smsRecptnAt(mberNtcnSetupEntity.getSmsRecptnAt())
                    .ntcnTimeLmttAt(mberNtcnSetupEntity.getNtcnTimeLmttAt())
                    .ntcnBeginTime(mberNtcnSetupEntity.getNtcnBeginTime())
                    .ntcnEndTime(mberNtcnSetupEntity.getNtcnEndTime())
                    .creatMberNo(mberNtcnSetupEntity.getCreatMberNo())
                    .creatDt(mberNtcnSetupEntity.getCreatDt())
                    .changeMberNo(mberNtcnSetupEntity.getChangeMberNo())
                    .changeDt(mberNtcnSetupEntity.getChangeDt())
                .build();
    }

    /**
     * 회원알림설정 변경
     * @param mberNtcnSetupInDto
     */
    public void changeMberNtcnSetupByMberNo(MberNtcnSetupInDto mberNtcnSetupInDto) {
        int cnt = mberNtcnSetupMapper.updateMberNtcnSetupByMberNo(MberNtcnSetupEntity
            .builder()
                .mberNo(mberNtcnSetupInDto.getMberNo())
                .allRecptnAt(mberNtcnSetupInDto.getAllRecptnAt())
                .aplctnRecptnAt(mberNtcnSetupInDto.getAplctnRecptnAt())
                .emailRecptnAt(mberNtcnSetupInDto.getEmailRecptnAt())
                .smsRecptnAt(mberNtcnSetupInDto.getSmsRecptnAt())
                .ntcnTimeLmttAt(mberNtcnSetupInDto.getNtcnTimeLmttAt())
                .ntcnBeginTime(mberNtcnSetupInDto.getNtcnBeginTime())
                .ntcnEndTime(mberNtcnSetupInDto.getNtcnEndTime())
                .changeMberNo(mberNtcnSetupInDto.getChangeMberNo())
            .build());
        if(cnt == 0) {
            throw new CustomException(ErrorCode.NTCN_SETUP_NOT_FOUND);
        }
    }
}

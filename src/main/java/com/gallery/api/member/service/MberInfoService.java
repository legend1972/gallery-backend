package com.gallery.api.member.service;

import org.springframework.stereotype.Service;

import com.gallery.api.member.dto.in.MberInfoInDto;
import com.gallery.api.member.dto.out.MberInfoOutDto;
import com.gallery.api.member.entity.MberInfoEntity;
import com.gallery.api.member.mapper.MberInfoMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MberInfoService {
    private final MberInfoMapper mberInfoMapper;

    /**
     * 이메일로 가입한 회원이 있는지 확인
     * @param mberInfoDto
     * @return
     */
    public MberInfoOutDto getMberInfoByEmail(MberInfoInDto mberInfoInDto) {
        MberInfoEntity rMberInfoEntity = mberInfoMapper.selectMberInfoByEmail(MberInfoEntity
            .builder()
                .email(mberInfoInDto.getEmail())
            .build());

        if(rMberInfoEntity == null) {
            return null;
        }

        return MberInfoOutDto
                .builder()
                    .mberNo(rMberInfoEntity.getMberNo())
                    .ncnm(rMberInfoEntity.getNcnm())
                    .email(rMberInfoEntity.getEmail())
                    .secretNo(rMberInfoEntity.getSecretNo())
                    .stplatAgreAt(rMberInfoEntity.getStplatAgreAt())
                    .indvdlinfoColctAgreAt(rMberInfoEntity.getIndvdlinfoColctAgreAt())
                    .marktColctAgreAt(rMberInfoEntity.getMarktColctAgreAt())
                    .emailColctAgreAt(rMberInfoEntity.getEmailColctAgreAt())
                    .smsColctAgreAt(rMberInfoEntity.getSmsColctAgreAt())
                    .sbscrbDt(rMberInfoEntity.getSbscrbDt())
                    .lastConectDt(rMberInfoEntity.getLastConectDt())
                    .mberSttusCode(rMberInfoEntity.getMberSttusCode())
                    .sttusChangeDt(rMberInfoEntity.getSttusChangeDt())
                    .mberSbscrbTyCode(rMberInfoEntity.getMberSbscrbTyCode())
                .build();

    }

    /**
     * 일반회원 등록
     * @param mberInfoInDto
     * @return
     */
    public String createMberInfo(MberInfoInDto mberInfoInDto) {
        MberInfoEntity mberInfoEntity = new MberInfoEntity();
        mberInfoEntity.setNcnm(mberInfoInDto.getNcnm());
        mberInfoEntity.setEmail(mberInfoInDto.getEmail());
        mberInfoEntity.setSecretNo(mberInfoInDto.getSecretNo());
        mberInfoEntity.setStplatAgreAt(mberInfoInDto.getStplatAgreAt());
        mberInfoEntity.setIndvdlinfoColctAgreAt(mberInfoInDto.getIndvdlinfoColctAgreAt());
        mberInfoEntity.setMarktColctAgreAt(mberInfoInDto.getMarktColctAgreAt());
        mberInfoEntity.setEmailColctAgreAt(mberInfoInDto.getEmailColctAgreAt());
        mberInfoEntity.setSmsColctAgreAt(mberInfoInDto.getSmsColctAgreAt());
        mberInfoEntity.setMberSttusCode(mberInfoInDto.getMberSttusCode());
        mberInfoEntity.setMberSbscrbTyCode(mberInfoInDto.getMberSbscrbTyCode());
        mberInfoEntity.setCreatMberNo(mberInfoInDto.getCreatMberNo());
        mberInfoEntity.setChangeMberNo(mberInfoInDto.getChangeMberNo());

        mberInfoMapper.insertMberInfo(mberInfoEntity);

        return mberInfoEntity.getMberNo();
    }

    /**
     * 마지막접속일시 변경
     * @param mberNo
     * @return
     */
    public int changeMberInfoByMberNo(String mberNo, String changeMberNo) {
        return mberInfoMapper.updateMberInfoByMberNo(mberNo, changeMberNo);
    }

    /**
     * 회원가입유형 변경
     * @param mberNo
     * @param changeMberNo
     * @param mberSbscrbTyCode
     * @return
     */
    public int changeMberInfoSbscrbTyByMberNo(String mberNo, String changeMberNo, String mberSbscrbTyCode) {
        return mberInfoMapper.updataMberInfoSbscrbTyByMberNo(mberNo, changeMberNo, mberSbscrbTyCode);
    }

    /**
     * 회원번호로 회원정보 조회
     * @param mberNo
     * @return
     */
    public MberInfoOutDto getMberInfoByMberNo(String mberNo) {
        MberInfoEntity rMberInfoEntity = mberInfoMapper.selectMberInfoByMberNo(mberNo);

        if(rMberInfoEntity == null) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return MberInfoOutDto
                .builder()
                    .mberNo(rMberInfoEntity.getMberNo())
                    .ncnm(rMberInfoEntity.getNcnm())
                    .email(rMberInfoEntity.getEmail())
                    .secretNo(rMberInfoEntity.getSecretNo())
                    .stplatAgreAt(rMberInfoEntity.getStplatAgreAt())
                    .indvdlinfoColctAgreAt(rMberInfoEntity.getIndvdlinfoColctAgreAt())
                    .marktColctAgreAt(rMberInfoEntity.getMarktColctAgreAt())
                    .emailColctAgreAt(rMberInfoEntity.getEmailColctAgreAt())
                    .smsColctAgreAt(rMberInfoEntity.getSmsColctAgreAt())
                    .sbscrbDt(rMberInfoEntity.getSbscrbDt())
                    .lastConectDt(rMberInfoEntity.getLastConectDt())
                    .mberSttusCode(rMberInfoEntity.getMberSttusCode())
                    .sttusChangeDt(rMberInfoEntity.getSttusChangeDt())
                    .mberSbscrbTyCode(rMberInfoEntity.getMberSbscrbTyCode())
                .build();

    }
}

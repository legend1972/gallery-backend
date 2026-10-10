package com.gallery.api.ntcn.service;

import org.springframework.stereotype.Service;

import com.gallery.api.ntcn.dto.out.NtcnTyOutDto;
import com.gallery.api.ntcn.entity.NtcnTyEntity;
import com.gallery.api.ntcn.mapper.NtcnTyMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NtcnTyService {
    private final NtcnTyMapper ntcnTyMapper;

    /**
     * 알림유형 조회
     * @param ntcnTyCode
     * @param ntcnSetupCode
     * @return
     */
    public NtcnTyOutDto getNtcnTyByNtcnTyCode(String ntcnTyCode, String ntcnSetupCode) {
        NtcnTyEntity ntcnTyEntity = ntcnTyMapper.selectNtcnTyByNtcnTyCode(ntcnTyCode, ntcnSetupCode);
        if(ntcnTyEntity == null) {
            throw new CustomException(ErrorCode.NTCN_TY_NOT_FOUND);
        }

        return NtcnTyOutDto
                .builder()
                    .ntcnTyCode(ntcnTyEntity.getNtcnTyCode())
                    .ntcnSetupCode(ntcnTyEntity.getNtcnSetupCode())
                    .ntcnTyNm(ntcnTyEntity.getNtcnTyNm())
                    .eventTyCode(ntcnTyEntity.getEventTyCode())
                    .sttusCode(ntcnTyEntity.getSttusCode())
                    .recptnTrgetTyCode(ntcnTyEntity.getRecptnTrgetTyCode())
                    .sjTmpl(ntcnTyEntity.getSjTmpl())
                    .cnTmpl(ntcnTyEntity.getCnTmpl())
                    .trgetTyCode(ntcnTyEntity.getTrgetTyCode())
                    .aplctnRecptnUseAt(ntcnTyEntity.getAplctnRecptnUseAt())
                    .emailRecptnUseAt(ntcnTyEntity.getEmailRecptnUseAt())
                    .smsRecptnUseAt(ntcnTyEntity.getSmsRecptnUseAt())
                    .ntcnPd(ntcnTyEntity.getNtcnPd())
                    .menuUrl(ntcnTyEntity.getMenuUrl())
                    .useAt(ntcnTyEntity.getUseAt())
                    .creatMberNo(ntcnTyEntity.getCreatMberNo())
                    .creatDt(ntcnTyEntity.getCreatDt())
                    .changeMberNo(ntcnTyEntity.getChangeMberNo())
                    .changeDt(ntcnTyEntity.getChangeDt())
                .build();
    }
}

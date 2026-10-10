package com.gallery.api.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gallery.api.member.dto.MberNtcnBindingDto;
import com.gallery.api.member.dto.in.MberNtcnInDto;
import com.gallery.api.member.entity.MberNtcnEntity;
import com.gallery.api.member.mapper.MberNtcnMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MberNtcnService {
    private final MberNtcnMapper mberNtcnMapper;

    /**
     * 회원알림 등록
     * @param mberNtcnInDto
     */
    public void createMberNtcn(MberNtcnInDto mberNtcnInDto) {
        mberNtcnMapper.insertMberNtcn(MberNtcnEntity
            .builder()
                .mberNo(mberNtcnInDto.getMberNo())
                .ntcnTyCode(mberNtcnInDto.getNtcnTyCode())
                .sj(mberNtcnInDto.getSj())
                .cn(mberNtcnInDto.getCn())
                .trgetTyCode(mberNtcnInDto.getTrgetTyCode())
                .trgetNo(mberNtcnInDto.getTrgetNo())
                .redngAt(mberNtcnInDto.getRedngAt())
                .redngDt(null)
                .ntcnEndDt(mberNtcnInDto.getNtcnEndDt())
                .useAt(mberNtcnInDto.getUseAt())
                .creatMberNo(mberNtcnInDto.getCreatMberNo())
                .changeMberNo(mberNtcnInDto.getChangeMberNo())
            .build()
        );
    }

    /**
     * 회원알림 건수 조회
     * @param mberNo
     * @param redngAt
     * @return
     */
    public int getCntMberNtcnByMberNo(String mberNo, String redngAt) {
        return mberNtcnMapper.selectCntMberNtcnByMberNo(mberNo, redngAt);
    }

    /**
     * 알림 리스트 정보 조회
     * @param mberNo
     * @param ntcnCtgryCode
     * @param size
     * @param offset
     * @return
     */
    public List<MberNtcnBindingDto> getListMberNtcnByMberNo(String mberNo, String ntcnCtgryCode, Integer size, Integer offset) {
        return mberNtcnMapper.selectListMberNtcnByMberNo(mberNo, ntcnCtgryCode, size, offset);
    }

    /**
     * 회원알림 읽음 처리
     * @param mberNo
     * @param changeMberNo
     * @param redngAt
     */
    public void changeMberNtcnByMberNtcnNo(String mberNtcnNo, String changeMberNo, String redngAt) {
        int cnt = mberNtcnMapper.updateMberNtcnByMberNtcnNo(mberNtcnNo, changeMberNo, redngAt);
        if(cnt == 0) {
            throw new CustomException(ErrorCode.MEMBER_NTCN_NOT_FOUND);
        }
    }
}

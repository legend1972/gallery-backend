package com.gallery.api.artist.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gallery.api.artist.dto.WritrJdgmnInfoBindingDto;
import com.gallery.api.artist.dto.in.WritrInfoInDto;
import com.gallery.api.artist.dto.out.WritrInfoOutDto;
import com.gallery.api.artist.entity.WritrInfoEntity;
import com.gallery.api.artist.mapper.WritrInfoMapper;
import com.gallery.api.member.dto.in.MberNtcnInDto;
import com.gallery.api.member.service.MberNtcnService;
import com.gallery.api.ntcn.dto.out.NtcnTyOutDto;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WritrInfoService {
    private final WritrInfoMapper writrInfoMapper;

    private final MberNtcnService mberNtcnService;

    /**
     * 작가정보등록 및 작가회원번호 조회
     * @param writrInfoInDto
     * @return
     */
    public String createWritrInfo(WritrInfoInDto writrInfoInDto) {
        WritrInfoEntity writrInfoEntity = WritrInfoEntity
            .builder()
                .gnrlMberNo(writrInfoInDto.getGnrlMberNo())
                .nm(writrInfoInDto.getNm())
                .actCode(writrInfoInDto.getActCode())
                .actArea(writrInfoInDto.getActArea())
                .telno(writrInfoInDto.getTelno())
                .selfIntrcn(writrInfoInDto.getSelfIntrcn())
                .writrFileNo(writrInfoInDto.getWritrFileNo())
                .reprsntPrdctFileNo(writrInfoInDto.getReprsntPrdctFileNo())
                .prdctNm(writrInfoInDto.getPrdctNm())
                .mnfctYear(writrInfoInDto.getMnfctYear())
                .prdctDc(writrInfoInDto.getPrdctDc())
                .rqstdt(writrInfoInDto.getRqstdt())
                .jdgmnSttusCode(writrInfoInDto.getJdgmnSttusCode())
                .jdgmnSttusChangeDt(writrInfoInDto.getJdgmnSttusChangeDt())
                .jdgmnNo(writrInfoInDto.getJdgmnNo())
                .jdgmnOpinion(writrInfoInDto.getJdgmnOpinion())
                .jdgmnSumry(writrInfoInDto.getJdgmnSumry())
                .mberSttusCode(writrInfoInDto.getMberSttusCode())
                .creatMberNo(writrInfoInDto.getCreatMberNo())
                .changeMberNo(writrInfoInDto.getChangeMberNo())
            .build();

        writrInfoMapper.insertWritrInfo(writrInfoEntity);

        return writrInfoEntity.getMberNo();
    }

    /**
     * 작가 파일 번호 및 대표 작품 파일 번호 변경
     * @param writrInfoInDto
     * @return
     */
    public int chgWritrInfoByMberNo(WritrInfoInDto writrInfoInDto) {
        return writrInfoMapper.updateWritrInfoByMberNo(WritrInfoEntity
                .builder()
                    .mberNo(writrInfoInDto.getMberNo())
                    .writrFileNo(writrInfoInDto.getWritrFileNo())
                    .reprsntPrdctFileNo(writrInfoInDto.getReprsntPrdctFileNo())
                    .jdgmnNo(writrInfoInDto.getJdgmnNo())
                    .changeMberNo(writrInfoInDto.getChangeMberNo())
                .build());
    }

    /**
     * 회원번호로 작가 정보가 있는지 확인
     * @param gnrlMberNo
     * @return
     */
    public WritrInfoOutDto getWritrInfoByGnrlMberNo(String gnrlMberNo) {
        WritrInfoEntity writrInfoEntity = writrInfoMapper.selectWritrInfoByGnrlMberNo(gnrlMberNo);
        if(writrInfoEntity == null) {
            return null;
        }

        return WritrInfoOutDto
                .builder()
                    .mberNo(writrInfoEntity.getMberNo())
                    .gnrlMberNo(writrInfoEntity.getGnrlMberNo())
                    .nm(writrInfoEntity.getNm())
                    .actCode(writrInfoEntity.getActCode())
                    .actArea(writrInfoEntity.getActArea())
                    .telno(writrInfoEntity.getTelno())
                    .selfIntrcn(writrInfoEntity.getSelfIntrcn())
                    .writrFileNo(writrInfoEntity.getWritrFileNo())
                    .reprsntPrdctFileNo(writrInfoEntity.getReprsntPrdctFileNo())
                    .prdctNm(writrInfoEntity.getPrdctNm())
                    .mnfctYear(writrInfoEntity.getMnfctYear())
                    .prdctDc(writrInfoEntity.getPrdctDc())
                    .rqstdt(writrInfoEntity.getRqstdt())
                    .jdgmnSttusCode(writrInfoEntity.getJdgmnSttusCode())
                    .jdgmnSttusChangeDt(writrInfoEntity.getJdgmnSttusChangeDt())
                    .jdgmnNo(writrInfoEntity.getJdgmnNo())
                    .jdgmnOpinion(writrInfoEntity.getJdgmnOpinion())
                    .jdgmnSumry(writrInfoEntity.getJdgmnSumry())
                    .mberSttusCode(writrInfoEntity.getMberSttusCode())
                    .creatMberNo(writrInfoEntity.getCreatMberNo())
                    .changeMberNo(writrInfoEntity.getChangeMberNo())
                .build();
    }

    /**
     * 작가정보 변경
     * @param writrInfoInDto
     */
    public void chgWritrBassInfoByMberNo(WritrInfoInDto writrInfoInDto) {
        int cnt = writrInfoMapper.updateWritrBassInfoByMberNo(WritrInfoEntity
                .builder()
                    .mberNo(writrInfoInDto.getMberNo())
                    .gnrlMberNo(writrInfoInDto.getGnrlMberNo())
                    .nm(writrInfoInDto.getNm())
                    .actCode(writrInfoInDto.getActCode())
                    .actArea(writrInfoInDto.getActArea())
                    .telno(writrInfoInDto.getTelno())
                    .selfIntrcn(writrInfoInDto.getSelfIntrcn())
                    .writrFileNo(writrInfoInDto.getWritrFileNo())
                    .reprsntPrdctFileNo(writrInfoInDto.getReprsntPrdctFileNo())
                    .prdctNm(writrInfoInDto.getPrdctNm())
                    .mnfctYear(writrInfoInDto.getMnfctYear())
                    .prdctDc(writrInfoInDto.getPrdctDc())
                    .rqstdt(writrInfoInDto.getRqstdt())
                    .jdgmnSttusCode(writrInfoInDto.getJdgmnSttusCode())
                    .jdgmnNo(writrInfoInDto.getJdgmnNo())
                    .jdgmnOpinion(writrInfoInDto.getJdgmnOpinion())
                    .jdgmnSumry(writrInfoInDto.getJdgmnSumry())
                    .mberSttusCode(writrInfoInDto.getMberSttusCode())
                    .creatMberNo(writrInfoInDto.getCreatMberNo())
                    .changeMberNo(writrInfoInDto.getChangeMberNo())
                .build());

        if(cnt == 0) {
            throw new CustomException(ErrorCode.WRITR_NOT_FOUND);
        }
    }

    /**
     * 심사할 작가 정보 조회
     * @param nm
     * @param mberSttusCode
     * @return
     */
    public List<WritrJdgmnInfoBindingDto> getListWritrJdgmnInfoByNm(Integer size, Integer offset, String nm, String mberSttusCode, String jdgmnSttusCode) {
        return writrInfoMapper.selectListWritrJdgmnInfoByNm(size, offset, nm, mberSttusCode, jdgmnSttusCode);
    }

    /**
     * 작가 정보 조회
     * @param mberNo
     * @param mberSttusCode
     * @return
     */
    public WritrInfoOutDto getWritrInfoByMberNo(String mberNo, String mberSttusCode) {
        WritrInfoEntity writrInfoEntity = writrInfoMapper.selectWritrInfoByMberNo(mberNo, mberSttusCode);
        if(writrInfoEntity == null) {
            throw new CustomException(ErrorCode.WRITR_NOT_FOUND);
        }

        return WritrInfoOutDto
                .builder()
                    .mberNo(writrInfoEntity.getMberNo())
                    .gnrlMberNo(writrInfoEntity.getGnrlMberNo())
                    .nm(writrInfoEntity.getNm())
                    .actCode(writrInfoEntity.getActCode())
                    .actArea(writrInfoEntity.getActArea())
                    .telno(writrInfoEntity.getTelno())
                    .selfIntrcn(writrInfoEntity.getSelfIntrcn())
                    .writrFileNo(writrInfoEntity.getWritrFileNo())
                    .reprsntPrdctFileNo(writrInfoEntity.getReprsntPrdctFileNo())
                    .prdctNm(writrInfoEntity.getPrdctNm())
                    .mnfctYear(writrInfoEntity.getMnfctYear())
                    .prdctDc(writrInfoEntity.getPrdctDc())
                    .rqstdt(writrInfoEntity.getRqstdt())
                    .jdgmnSttusCode(writrInfoEntity.getJdgmnSttusCode())
                    .jdgmnSttusChangeDt(writrInfoEntity.getJdgmnSttusChangeDt())
                    .jdgmnNo(writrInfoEntity.getJdgmnNo())
                    .jdgmnOpinion(writrInfoEntity.getJdgmnOpinion())
                    .jdgmnSumry(writrInfoEntity.getJdgmnSumry())
                    .mberSttusCode(writrInfoEntity.getMberSttusCode())
                    .creatMberNo(writrInfoEntity.getCreatMberNo())
                    .changeMberNo(writrInfoEntity.getChangeMberNo())
                .build();
    }

    /**
     * 작가 심사 정보 변경
     * @param changeJdgmnSttusCode
     * @param writrInfoInDto
     */
    public void chgWritrJdgmnInfoByMberNo(String changeJdgmnSttusCode, WritrInfoInDto writrInfoInDto) {
        int cnt = writrInfoMapper.updateWritrJdgmnInfoByMberNo(changeJdgmnSttusCode, WritrInfoEntity
                .builder()
                    .mberNo(writrInfoInDto.getMberNo())
                    .gnrlMberNo(writrInfoInDto.getGnrlMberNo())
                    .nm(writrInfoInDto.getNm())
                    .actCode(writrInfoInDto.getActCode())
                    .actArea(writrInfoInDto.getActArea())
                    .telno(writrInfoInDto.getTelno())
                    .selfIntrcn(writrInfoInDto.getSelfIntrcn())
                    .writrFileNo(writrInfoInDto.getWritrFileNo())
                    .reprsntPrdctFileNo(writrInfoInDto.getReprsntPrdctFileNo())
                    .prdctNm(writrInfoInDto.getPrdctNm())
                    .mnfctYear(writrInfoInDto.getMnfctYear())
                    .prdctDc(writrInfoInDto.getPrdctDc())
                    .rqstdt(writrInfoInDto.getRqstdt())
                    .jdgmnSttusCode(writrInfoInDto.getJdgmnSttusCode())
                    .jdgmnNo(writrInfoInDto.getJdgmnNo())
                    .jdgmnOpinion(writrInfoInDto.getJdgmnOpinion())
                    .jdgmnSumry(writrInfoInDto.getJdgmnSumry())
                    .mberSttusCode(writrInfoInDto.getMberSttusCode())
                    .creatMberNo(writrInfoInDto.getCreatMberNo())
                    .changeMberNo(writrInfoInDto.getChangeMberNo())
                .build());

        if(cnt == 0) {
            throw new CustomException(ErrorCode.WRITR_JDGMN_STTUS_NOT_FOUND);
        }
    }

    /**
     * 회원알림 등록
     * @param mberNo
     * @param cn
     * @param trgetNo
     * @param changeMberNo
     * @param ntcnTyOutDto
     */
    public void createMberNtcn(String mberNo, String cn, String trgetNo, String changeMberNo, NtcnTyOutDto ntcnTyOutDto) {
        mberNtcnService.createMberNtcn(MberNtcnInDto
            .builder()
                .mberNo(mberNo)
                .ntcnTyCode(ntcnTyOutDto.getNtcnTyCode())
                .sj(ntcnTyOutDto.getSjTmpl())
                .cn(cn)
                .trgetTyCode(ntcnTyOutDto.getTrgetTyCode())
                .trgetNo(trgetNo)
                .redngAt("N")
                .ntcnEndDt(ntcnTyOutDto.getNtcnPd() != null ? LocalDateTime.now().plusDays(ntcnTyOutDto.getNtcnPd()) : LocalDateTime.of(9999, 12, 31, 23, 59, 59))
                .useAt("Y")
                .creatMberNo(changeMberNo)
                .changeMberNo(changeMberNo)
            .build()
        );
    }
}

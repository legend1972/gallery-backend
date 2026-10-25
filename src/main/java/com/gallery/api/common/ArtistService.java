package com.gallery.api.common;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gallery.api.artist.dto.out.WritrInfoOutDto;
import com.gallery.api.artist.service.WritrInfoService;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.StcCommVari;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

/**
 * 작가서비스
 * ArtistService
 */
@Service
@RequiredArgsConstructor
public class ArtistService {
    private final WritrInfoService writrInfoService;

    /**
     * 작가회원인 경우 작가회원번호 리턴, 그렇지 않으면 일반회원번호 리턴
     * @param mberNo
     * @param mberSbscrbTyCode
     * @return
     */
    public String getMberNo(String gnrlMberNo, String mberSbscrbTyCode) {
        String mberNo = "";
        if(ObjectUtils.nullSafeEquals(StcCommVari.MBER_SBSCRB_TY_ARTS, mberSbscrbTyCode)) {
            mberNo = Optional.ofNullable(writrInfoService.getWritrInfoByGnrlMberNo(gnrlMberNo)).orElseGet(WritrInfoOutDto::new).getMberNo();
            if(mberNo == null) {
                throw new CustomException(ErrorCode.WRITR_NOT_FOUND);
            }
        } else {
            mberNo = gnrlMberNo;
        }
        return mberNo;
    }
}

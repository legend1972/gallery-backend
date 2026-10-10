package com.gallery.api.artist.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gallery.api.artist.dto.WritrUrlBindingDto;
import com.gallery.api.artist.dto.in.WritrUrlInDto;
import com.gallery.api.artist.dto.out.WritrUrlOutDto;
import com.gallery.api.artist.entity.WritrUrlEntity;
import com.gallery.api.artist.mapper.WritrUrlMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WritrUrlService {
    private final WritrUrlMapper writrUrlMapper;

    /**
     * 작가 URL 등록 및 URL 번호 조회
     * @param List
     * @return
     */
    public String creatListWritrUrl(List<WritrUrlInDto> List) {
        WritrUrlBindingDto writrUrlBindingDto = WritrUrlBindingDto
            .builder()
                .urlList(List)
            .build();

        writrUrlMapper.insertListWritrUrl(writrUrlBindingDto);

        return writrUrlBindingDto.getUrlNo();
    }

    /**
     * 작가URL 정보 리스트 조회
     * @param mberNo
     * @return
     */
    public List<WritrUrlOutDto> getListWritrUrlByMberNo(String mberNo) {
        List<WritrUrlEntity> writrUrlEntityList = writrUrlMapper.selectListWritrUrlByMberNo(mberNo);
        if(writrUrlEntityList.size() == 0) {
            throw new CustomException(null);
        }

        List<WritrUrlOutDto> list = new ArrayList<>();
        for(WritrUrlEntity writrUrlEntity : writrUrlEntityList) {
            list.add(WritrUrlOutDto
                .builder()
                    .urlNo(writrUrlEntity.getUrlNo())
                    .sn(writrUrlEntity.getSn())
                    .mberNo(writrUrlEntity.getMberNo())
                    .url(writrUrlEntity.getUrl())
                    .siteCode(writrUrlEntity.getSiteCode())
                    .creatMberNo(writrUrlEntity.getCreatMberNo())
                    .creatDt(writrUrlEntity.getCreatDt())
                    .changeMberNo(writrUrlEntity.getChangeMberNo())
                    .changeDt(writrUrlEntity.getChangeDt())
                .build());
        }

        return list;
    }

    /**
     * 작가 URL 삭제
     * @param mberNo
     */
    public void delWritrUrlByMberNo(String mberNo) {
        int cnt = writrUrlMapper.deleteWritrUrlByMberNo(mberNo);
        if(cnt == 0) {
            throw new CustomException(ErrorCode.URL_NOT_FOUND);
        }
    }
}

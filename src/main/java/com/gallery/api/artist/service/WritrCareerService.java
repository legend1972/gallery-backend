package com.gallery.api.artist.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gallery.api.artist.dto.WritrCareerBindingDto;
import com.gallery.api.artist.dto.in.WritrCareerInDto;
import com.gallery.api.artist.dto.out.WritrCareerOutDto;
import com.gallery.api.artist.entity.WritrCareerEntity;
import com.gallery.api.artist.mapper.WritrCareerMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WritrCareerService {
    private final WritrCareerMapper writrCareerMapper;

    /**
     * 작가경력 등록 및 경력번호 조회
     * @param writrCareerInDtoList
     * @return
     */
    public String createWritrCarrer(List<WritrCareerInDto> writrCareerInDtoList) {
        WritrCareerBindingDto writrCareerBindingDto = WritrCareerBindingDto
            .builder()
                .careerList(writrCareerInDtoList)
            .build();

        writrCareerMapper.insertListWritrCareer(writrCareerBindingDto);
        return writrCareerBindingDto.getCareerNo();
    }

    /**
     * 경력 정보 리스트 조회
     * @param mberNo
     * @return
     */
    public List<WritrCareerOutDto> getListWritrCareerByMberNo(String mberNo) {
        List<WritrCareerEntity> writrCareerEntityList = writrCareerMapper.selectListWritrCareerByMberNo(mberNo);
        if(writrCareerEntityList.size() == 0) {
            throw new CustomException(ErrorCode.CAREER_NOT_FOUND);
        }

        List<WritrCareerOutDto> list = new ArrayList<>();
        for(WritrCareerEntity writrCareerEntity : writrCareerEntityList) {
            list.add(WritrCareerOutDto
                .builder()
                    .careerNo(writrCareerEntity.getCareerNo())
                    .sn(writrCareerEntity.getSn())
                    .mberNo(writrCareerEntity.getMberNo())
                    .careerYear(writrCareerEntity.getCareerYear())
                    .careerCn(writrCareerEntity.getCareerCn())
                    .careerSeCode(writrCareerEntity.getCareerSeCode())
                    .creatMberNo(writrCareerEntity.getCreatMberNo())
                    .creatDt(writrCareerEntity.getCreatDt())
                    .changeMberNo(writrCareerEntity.getChangeMberNo())
                    .changeDt(writrCareerEntity.getChangeDt())
                .build());
        }

        return list;
    }

    /**
     * 작가경력 삭제
     * @param mberNo
     */
    public void delWritrCareerByMberNo(String mberNo) {
        int cnt = writrCareerMapper.deleteWritrCareerByMberNo(mberNo);
        if(cnt == 0) {
            throw new CustomException(ErrorCode.CAREER_NOT_FOUND);
        }
    }
}

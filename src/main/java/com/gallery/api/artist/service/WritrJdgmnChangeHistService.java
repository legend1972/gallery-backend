package com.gallery.api.artist.service;

import org.springframework.stereotype.Service;

import com.gallery.api.artist.dto.in.WritrJdgmnChangeHistInDto;
import com.gallery.api.artist.entity.WritrJdgmnChangeHistEntity;
import com.gallery.api.artist.mapper.WritrJdgmnChangeHistMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WritrJdgmnChangeHistService {
    private final WritrJdgmnChangeHistMapper writrJdgmnChangeHistMapper;

    /**
     * 작가심사변경이력 등록
     * @param writrJdgmnChangeHistInDto
     */
    public void createWritrJdgmnChangeHist(WritrJdgmnChangeHistInDto writrJdgmnChangeHistInDto) {
        writrJdgmnChangeHistMapper.insertWritrJdgmnChangeHist(WritrJdgmnChangeHistEntity
            .builder()
                .mberNo(writrJdgmnChangeHistInDto.getMberNo())
                .endDt(writrJdgmnChangeHistInDto.getEndDt())
                .jdgmnSttusCode(writrJdgmnChangeHistInDto.getJdgmnSttusCode())
                .creatMberNo(writrJdgmnChangeHistInDto.getCreatMberNo())
                .changeMberNo(writrJdgmnChangeHistInDto.getChangeMberNo())
            .build());
    }

    /**
     * 작가심사변경이력 변경
     * @param writrJdgmnChangeHistInDto
     */
    public void chgWritrJdgmnChangeHist(WritrJdgmnChangeHistInDto writrJdgmnChangeHistInDto) {
        int cnt = writrJdgmnChangeHistMapper.updateWritrJdgmnChangeHist(WritrJdgmnChangeHistEntity
            .builder()
                .mberNo(writrJdgmnChangeHistInDto.getMberNo())
                .jdgmnSttusCode(writrJdgmnChangeHistInDto.getJdgmnSttusCode())
                .changeMberNo(writrJdgmnChangeHistInDto.getChangeMberNo())
            .build());
        //SJH
        // if(cnt == 0) {
        //     throw new CustomException(ErrorCode.WRITR_JDGMN_CHANGE_NOT_FOUND);
        // }
    }
}

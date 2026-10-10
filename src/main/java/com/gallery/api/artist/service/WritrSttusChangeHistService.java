package com.gallery.api.artist.service;

import org.springframework.stereotype.Service;

import com.gallery.api.artist.dto.in.WritrSttusChangeHistInDto;
import com.gallery.api.artist.entity.WritrSttusChangeHistEntity;
import com.gallery.api.artist.mapper.WritrSttusChangeHistMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WritrSttusChangeHistService {
    private final WritrSttusChangeHistMapper writrSttusChangeHistMapper;

    /**
     * 작가상태변경이력 등록
     * @param writrSttusChangeHistInDto
     * @return
     */
    public int createWritrSttusChangeHist(WritrSttusChangeHistInDto writrSttusChangeHistInDto) {
        return writrSttusChangeHistMapper.insertWritrSttusChangeHist(WritrSttusChangeHistEntity
                .builder()
                    .mberNo(writrSttusChangeHistInDto.getMberNo())
                    .endDt(writrSttusChangeHistInDto.getEndDt())
                    .writrSttusCode(writrSttusChangeHistInDto.getWritrSttusCode())
                    .writrChangeResnCode(writrSttusChangeHistInDto.getWritrChangeResnCode())
                    .resnDetail(writrSttusChangeHistInDto.getResnDetail())
                    .creatMberNo(writrSttusChangeHistInDto.getCreatMberNo())
                    .changeMberNo(writrSttusChangeHistInDto.getChangeMberNo())
                .build());
    }

    /**
     * 작가상태변경이력 변경
     * @param mberNo
     * @param changeMberNo
     */
    public void changeWritrSttusChangeHistByMberNo(String mberNo, String changeMberNo) {
        int cnt = writrSttusChangeHistMapper.updateWritrSttusChangeHistByMberNo(mberNo, changeMberNo);
        if(cnt == 0) {
            throw new CustomException(ErrorCode.WRITR_STTUS_CHANGE_NOT_FOUND);
        }
    }
}

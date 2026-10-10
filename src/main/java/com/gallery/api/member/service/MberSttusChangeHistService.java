package com.gallery.api.member.service;

import org.springframework.stereotype.Service;

import com.gallery.api.member.dto.in.MberSttusChangeHistInDto;
import com.gallery.api.member.entity.MberSttusChangeHistEntity;
import com.gallery.api.member.mapper.MberSttusChangeHistMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MberSttusChangeHistService {
    private final MberSttusChangeHistMapper mberSttusChangeHistMapper;

    /**
     * 회원상태변경이력 등록
     * @param mberSttusChangeHistInDto
     * @return
     */
    public int createMberSttusChangeHist(MberSttusChangeHistInDto mberSttusChangeHistInDto) {
        return mberSttusChangeHistMapper.insertMberSttusChangeHist(MberSttusChangeHistEntity
            .builder()
                .mberNo(mberSttusChangeHistInDto.getMberNo())
                .endDt(mberSttusChangeHistInDto.getEndDt())
                .mberSttusCode(mberSttusChangeHistInDto.getMberSttusCode())
                .mberChangeResnCode(mberSttusChangeHistInDto.getMberChangeResnCode())
                .resnDetail(mberSttusChangeHistInDto.getResnDetail())
                .creatMberNo(mberSttusChangeHistInDto.getCreatMberNo())
                .changeMberNo(mberSttusChangeHistInDto.getChangeMberNo())
            .build());
    }
}

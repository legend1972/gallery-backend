package com.gallery.api.jdgmn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gallery.api.jdgmn.dto.JdgmnCeckDetailBindingDto;
import com.gallery.api.jdgmn.dto.in.JdgmnCeckDetailInDto;
import com.gallery.api.jdgmn.dto.out.JdgmnCeckDetailOutDto;
import com.gallery.api.jdgmn.entity.JdgmnCeckDetailEntity;
import com.gallery.api.jdgmn.mapper.JdgmnCeckDetailMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JdgmnCeckDetailService {
    private final JdgmnCeckDetailMapper jdgmnCeckDetailMapper;

    /**
     * 심사체크상세 등록 및 심사번호 조회
     * @param jdgmnCeckDetailInDtoList
     * @return
     */
    public String createListJdgmnCeckDetail(List<JdgmnCeckDetailInDto> jdgmnCeckDetailList) {
        JdgmnCeckDetailBindingDto jdgmnCeckDetailBindingDto = JdgmnCeckDetailBindingDto
            .builder()
                .jdgmnCeckDetailList(jdgmnCeckDetailList)
            .build();

        jdgmnCeckDetailMapper.insertListJdgmnCeckDetail(jdgmnCeckDetailBindingDto);

        return jdgmnCeckDetailBindingDto.getJdgmnNo();
    }

    /**
     * 심사체크리스트 조회
     * @param jdgmnNo
     * @return
     */
    public List<JdgmnCeckDetailOutDto> getListJdgmnCeckDetailByJdgmnNo(String jdgmnNo) {
        List<JdgmnCeckDetailOutDto> list = new ArrayList<>();
        
        List<JdgmnCeckDetailEntity> jdgmnCeckDetailEntityList = jdgmnCeckDetailMapper.selectListJdgmnCeckDetailByJdgmnNo(jdgmnNo);
        for(JdgmnCeckDetailEntity jdgmnCeckDetailEntity: jdgmnCeckDetailEntityList) {
            list.add(JdgmnCeckDetailOutDto
                .builder()
                    .jdgmnNo(jdgmnCeckDetailEntity.getJdgmnNo())
                    .jdgmnCeckCode(jdgmnCeckDetailEntity.getJdgmnCeckCode())
                    .mberNo(jdgmnCeckDetailEntity.getMberNo())
                    .ceckAt(jdgmnCeckDetailEntity.getCeckAt())
                .build());
        }

        return list;
    }

    /**
     * 심사체크상세 삭제
     * @param jdgmnNo
     */
    public void delListJdgmnCeckDetailByJdgmnNo(String jdgmnNo) {
        int cnt = jdgmnCeckDetailMapper.deleteListJdgmnCeckDetailByJdgmnNo(jdgmnNo);
        if(cnt == 0) {
            throw new CustomException(ErrorCode.JDGMN_CECK_DETAIL_NOT_FOUND);
        }
    }
}

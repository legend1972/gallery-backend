package com.gallery.api.role.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gallery.api.role.dto.MberRoleBindingDto;
import com.gallery.api.role.dto.in.MberRoleInDto;
import com.gallery.api.role.dto.out.MberRoleOutDto;
import com.gallery.api.role.entity.MberRoleEntity;
import com.gallery.api.role.mapper.MberRoleMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MberRoleService {
    private final MberRoleMapper mberRoleMapper;

    /**
     * 회원역할 등록
     * @param mberRoleInDto
     * @return
     */
    public int createMberRole(MberRoleInDto mberRoleInDto) {
        return mberRoleMapper.insertMberRole(MberRoleEntity
            .builder()
                .mberNo(mberRoleInDto.getMberNo())
                .roleId(mberRoleInDto.getRoleId())
                .alwncMberNo(mberRoleInDto.getAlwncMberNo())
                .endDt(mberRoleInDto.getEndDt())
                .creatMberNo(mberRoleInDto.getCreatMberNo())
                .changeMberNo(mberRoleInDto.getChangeMberNo())
            .build());
    }

    /**
     * 회원역할 종료일자 만료 처리
     * @param mberRoleInDto
     * @return
     */
    public int chgMberRoleByMberNoAndRoleId(MberRoleInDto mberRoleInDto) {
       return mberRoleMapper.updateMberRoleByMberNoAndRoleId(MberRoleEntity
                .builder()
                    .mberNo(mberRoleInDto.getMberNo())
                    .roleId(mberRoleInDto.getRoleId())
                    .alwncDt(mberRoleInDto.getAlwncDt())
                    .alwncMberNo(mberRoleInDto.getAlwncMberNo())
                    .endDt(mberRoleInDto.getEndDt())
                    .creatMberNo(mberRoleInDto.getCreatMberNo())
                    .changeMberNo(mberRoleInDto.getChangeMberNo())
                .build());
    }

    /**
     * 회원번호 조건으로 회원역할 조회
     * @param mberNo
     * @return
     */
    public MberRoleOutDto getMberRoleByMberNo(String mberNo) {
        MberRoleEntity mberRoleEntity = mberRoleMapper.selectMberRoleByMberNo(mberNo);
        if(mberRoleEntity == null) {
            throw new CustomException(ErrorCode.MBER_ROLE_INFO_NOT_FOUND);
        }
        
        return MberRoleOutDto
                .builder()
                    .mberNo(mberRoleEntity.getMberNo())
                    .roleId(mberRoleEntity.getRoleId())
                    .alwncDt(mberRoleEntity.getAlwncDt())
                    .alwncMberNo(mberRoleEntity.getAlwncMberNo())
                    .endDt(mberRoleEntity.getEndDt())
                    .creatMberNo(mberRoleEntity.getCreatMberNo())
                    .creatDt(mberRoleEntity.getCreatDt())
                    .changeMberNo(mberRoleEntity.getChangeMberNo())
                    .changeDt(mberRoleEntity.getChangeDt())
                .build();
    }

    /**
     * 전체 메뉴 조회
     * @param roleId
     * @param mberNo
     * @return
     */
    public List<MberRoleBindingDto> getListAllMenuByMberNoAndRole(String roleId, String mberNo, String nm) {
        List<MberRoleBindingDto> mberRoleBindingDtoList = mberRoleMapper.selectListAllMenuByMberNoAndRole(roleId, mberNo, nm);
        if(mberRoleBindingDtoList.size() == 0) {
            throw new CustomException(ErrorCode.MENU_ERROR);
        }
        return mberRoleBindingDtoList;
    }

}

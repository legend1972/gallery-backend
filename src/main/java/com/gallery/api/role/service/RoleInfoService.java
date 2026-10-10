package com.gallery.api.role.service;

import org.springframework.stereotype.Service;

import com.gallery.api.role.dto.in.RoleInfoInDto;
import com.gallery.api.role.dto.out.RoleInfoOutDto;
import com.gallery.api.role.entity.RoleInfoEntity;
import com.gallery.api.role.mapper.RoleInfoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleInfoService {
    private final RoleInfoMapper roleInfoMapper;

    /**
     * 역할정보 조회
     * @param RoleInfoInDto
     * @return
     */
    public RoleInfoOutDto getRoleInfo(RoleInfoInDto roleInfoInDto) {
        RoleInfoEntity roleInfoEntity = roleInfoMapper.selectRoleInfoByBassRoleCode(roleInfoInDto.getBassRoleCode());
        if(roleInfoMapper == null) {
            return null;
        }

        return RoleInfoOutDto
                .builder()
                    .roleId(roleInfoEntity.getRoleId())
                    .bassRoleCode(roleInfoEntity.getBassRoleCode())
                    .roleNm(roleInfoEntity.getRoleNm())
                    .useAt(roleInfoEntity.getUseAt())
                    .creatMberNo(roleInfoEntity.getCreatMberNo())
                    .creatDt(roleInfoEntity.getCreatDt())
                    .changeMberNo(roleInfoEntity.getChangeMberNo())
                    .changeDt(roleInfoEntity.getChangeDt())
                .build();
    }
}

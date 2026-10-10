package com.gallery.api.role.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gallery.api.role.entity.RoleInfoEntity;

@Mapper
public interface RoleInfoMapper {
    RoleInfoEntity selectRoleInfoByBassRoleCode(String bassRoleCode);
}

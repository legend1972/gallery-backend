package com.gallery.api.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.role.entity.MenuEntity;

@Mapper
public interface MenuMapper {
    List<MenuEntity> selectLstMenuByMNoAndRCode(@Param("roleId") String roleId, @Param("mberNo") String mberNo, @Param("bassRoleCode") String bassRoleCode);
}

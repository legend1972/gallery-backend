package com.gallery.api.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.role.entity.MberRoleEntity;

@Mapper
public interface MberRoleMapper {
    int insertMberRole(MberRoleEntity mberRoleEntity);

    int updateMberRoleByMberNoAndRoleId(MberRoleEntity mberRoleEntity);

    MberRoleEntity selectMberRoleByMberNo(@Param("mberNo") String mberNo);
}

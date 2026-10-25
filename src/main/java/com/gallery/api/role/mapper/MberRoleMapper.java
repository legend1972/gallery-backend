package com.gallery.api.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.role.dto.MberRoleBindingDto;
import com.gallery.api.role.entity.MberRoleEntity;

@Mapper
public interface MberRoleMapper {
    int insertMberRole(MberRoleEntity mberRoleEntity);

    int updateMberRoleByMberNoAndRoleId(MberRoleEntity mberRoleEntity);

    MberRoleEntity selectMberRoleByMberNo(@Param("mberNo") String mberNo);

    public List<MberRoleBindingDto> selectListAllMenuByMberNoAndRole(@Param("roleId") String roleId, @Param("mberNo") String mberNo, @Param("nm") String nm);
}

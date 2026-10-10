package com.gallery.api.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.member.dto.MberNtcnIemSetupBindingDto;
import com.gallery.api.member.entity.MberNtcnIemSetupEntity;

@Mapper
public interface MberNtcnIemSetupMapper {
    public int insertMberNtcnIemSetupList(@Param("mberNtcnIemSetup") MberNtcnIemSetupEntity mberNtcnIemSetupEntity, @Param("roleId") String roleId);

    public List<MberNtcnIemSetupEntity> selectMberNtcnIemSetupByMberNo(String mberNo);

    public List<MberNtcnIemSetupBindingDto> selectListMberNtcnIemSetupByMberNoAndRoldId(@Param("mberNo") String mberNo, @Param("roleId") String roleId);

    public int updateMberNtcnIemSetupByMberNoAndNtcnSetupCode(MberNtcnIemSetupEntity mberNtcnIemSetupEntity);

    public int insertMberNtcnIemSetup(MberNtcnIemSetupEntity mberNtcnIemSetupEntity);
}

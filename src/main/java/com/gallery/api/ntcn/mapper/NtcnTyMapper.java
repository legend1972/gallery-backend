package com.gallery.api.ntcn.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.ntcn.entity.NtcnTyEntity;

@Mapper
public interface NtcnTyMapper {
    public NtcnTyEntity selectNtcnTyByNtcnTyCode(@Param("ntcnTyCode") String ntcnTyCode, @Param("ntcnSetupCode") String ntcnSetupCode);
}

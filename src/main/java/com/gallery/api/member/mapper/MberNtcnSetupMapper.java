package com.gallery.api.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gallery.api.member.entity.MberNtcnSetupEntity;

@Mapper
public interface MberNtcnSetupMapper {
    public int insertMberNtcnSetup(MberNtcnSetupEntity mberNtcnSetupEntity);

    public MberNtcnSetupEntity selectMberNtcnSetupByMberNo(String mberNo);

    public int updateMberNtcnSetupByMberNo(MberNtcnSetupEntity mberNtcnSetupEntity);
}

package com.gallery.api.codes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gallery.api.codes.entity.CmmnGroupCdEntity;

@Mapper
public interface CmmnGroupCdMapper {
    public abstract List<CmmnGroupCdEntity> selectAllCmmnGroupCd();
}

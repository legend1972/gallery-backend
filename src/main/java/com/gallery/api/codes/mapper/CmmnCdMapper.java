package com.gallery.api.codes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.codes.dto.out.CmmnCdOutDto;

@Mapper
public interface CmmnCdMapper {
    List<CmmnCdOutDto> selectAllCmmnCd();

    List<CmmnCdOutDto> selectCmmnCdList(@Param("groupCode") String groupCode);
}

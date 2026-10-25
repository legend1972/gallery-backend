package com.gallery.api.prdct.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.prdct.entity.PrdctActEntity;

/**
 * 작품활동Mapper
 * PrdctActMapper
 */
@Mapper
public interface PrdctActMapper {
    public int insertPrdctAct(PrdctActEntity prdctActEntity);

    public PrdctActEntity selectNextPrdctSnByMberNo(@Param("mberNo") String mberNo);

    public int insertNextPrdctAct(PrdctActEntity prdctActEntity);
}

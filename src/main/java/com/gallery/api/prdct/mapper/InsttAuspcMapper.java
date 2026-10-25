package com.gallery.api.prdct.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.prdct.entity.InsttAuspcEntity;

@Mapper
public interface InsttAuspcMapper {
    public int insertInsttAuspc(InsttAuspcEntity insttAuspcEntity);

    public InsttAuspcEntity selectNextSnByMberNo(@Param("prdctActNo") String prdctActNo, @Param("prdctSn") Integer prdctSn, @Param("mberNo") String mberNo);

    public int insertNextInsttAuspc(InsttAuspcEntity insttAuspcEntity);
}

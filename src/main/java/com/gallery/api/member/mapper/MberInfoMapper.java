package com.gallery.api.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.member.entity.MberInfoEntity;

@Mapper
public interface MberInfoMapper {
    MberInfoEntity selectMberInfoByEmail(MberInfoEntity mberInfoEntity);

    int insertMberInfo(MberInfoEntity mberInfoEntity);

    int updateMberInfoByMberNo(@Param("mberNo") String mberNo, @Param("changeMberNo") String changeMberNo);

    int updataMberInfoSbscrbTyByMberNo(@Param("mberNo") String mberNo, @Param("changeMberNo") String changeMberNo, @Param("mberSbscrbTyCode") String mberSbscrbTyCode);

    MberInfoEntity selectMberInfoByMberNo(String mberNo);
}

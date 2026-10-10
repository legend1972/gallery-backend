package com.gallery.api.artist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.artist.dto.WritrJdgmnInfoBindingDto;
import com.gallery.api.artist.entity.WritrInfoEntity;

@Mapper
public interface WritrInfoMapper {
    int insertWritrInfo(WritrInfoEntity writrInfoEntity);

    int updateWritrInfoByMberNo(WritrInfoEntity writrInfoEntity);

    WritrInfoEntity selectWritrInfoByGnrlMberNo(String gnrlMberNo);

    int updateWritrBassInfoByMberNo(WritrInfoEntity writrInfoEntity);

    List<WritrJdgmnInfoBindingDto> selectListWritrJdgmnInfoByNm(@Param("size") Integer size, @Param("offset") Integer offset, @Param("nm") String nm, @Param("mberSttusCode") String mberSttusCode, @Param("jdgmnSttusCode") String jdgmnSttusCode);

    WritrInfoEntity selectWritrInfoByMberNo(@Param("mberNo") String mberNo, @Param("mberSttusCode") String mberSttusCode);

    int updateWritrJdgmnInfoByMberNo(@Param("changeJdgmnSttusCode") String changeJdgmnSttusCode, @Param("writrInfoEntity") WritrInfoEntity writrInfoEntity);
}

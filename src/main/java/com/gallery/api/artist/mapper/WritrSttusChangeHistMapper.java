package com.gallery.api.artist.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.artist.entity.WritrSttusChangeHistEntity;

@Mapper
public interface WritrSttusChangeHistMapper {
    int insertWritrSttusChangeHist(WritrSttusChangeHistEntity writrSttusChangeHistEntity);

    int updateWritrSttusChangeHistByMberNo(@Param("mberNo") String mberNo, @Param("changeMberNo") String changeMberNo);
}

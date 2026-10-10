package com.gallery.api.artist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.artist.dto.WritrUrlBindingDto;
import com.gallery.api.artist.entity.WritrUrlEntity;

@Mapper
public interface WritrUrlMapper {
    int insertListWritrUrl(WritrUrlBindingDto writrUrlBindingDto);

    List<WritrUrlEntity> selectListWritrUrlByMberNo(@Param("mberNo") String mberNo);

    int deleteWritrUrlByMberNo(@Param("mberNo") String mberNo);
}

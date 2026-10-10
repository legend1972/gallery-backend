package com.gallery.api.artist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.artist.dto.WritrCareerBindingDto;
import com.gallery.api.artist.entity.WritrCareerEntity;

@Mapper
public interface WritrCareerMapper {
    int insertListWritrCareer(WritrCareerBindingDto writrCareerBindingDto);

    List<WritrCareerEntity> selectListWritrCareerByMberNo(@Param("mberNo") String mberNo);

    int deleteWritrCareerByMberNo(@Param("mberNo") String mberNo);
}

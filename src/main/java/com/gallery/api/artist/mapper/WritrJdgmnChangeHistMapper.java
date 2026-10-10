package com.gallery.api.artist.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gallery.api.artist.entity.WritrJdgmnChangeHistEntity;

@Mapper 
public interface WritrJdgmnChangeHistMapper {
    public int insertWritrJdgmnChangeHist(WritrJdgmnChangeHistEntity writrJdgmnChangeHistEntity);

    public int updateWritrJdgmnChangeHist(WritrJdgmnChangeHistEntity writrJdgmnChangeHistEntity);
}

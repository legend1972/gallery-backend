package com.gallery.api.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gallery.api.member.entity.MberSttusChangeHistEntity;

@Mapper
public interface MberSttusChangeHistMapper {
    int insertMberSttusChangeHist(MberSttusChangeHistEntity mberSttusChangeHistEntity);
}

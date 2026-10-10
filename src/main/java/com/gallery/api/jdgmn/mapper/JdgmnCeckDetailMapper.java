package com.gallery.api.jdgmn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gallery.api.jdgmn.dto.JdgmnCeckDetailBindingDto;
import com.gallery.api.jdgmn.entity.JdgmnCeckDetailEntity;

@Mapper
public interface JdgmnCeckDetailMapper {
    public int insertListJdgmnCeckDetail(JdgmnCeckDetailBindingDto jdgmnCeckDetailBindingDto);

    public List<JdgmnCeckDetailEntity> selectListJdgmnCeckDetailByJdgmnNo(String jdgmnNo);

    public int deleteListJdgmnCeckDetailByJdgmnNo(String jdgmnNo);
}

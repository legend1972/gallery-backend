package com.gallery.api.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.file.dto.FileDtlsBindingDto;
import com.gallery.api.file.entity.FileDtlsEntity;

@Mapper
public interface FileDtlsMapper {
    int insertListFileDtls(FileDtlsBindingDto fileDtlsBindingDto);

    List<FileDtlsEntity> selectListFileDtlsByFileNo(@Param("fileNo") String fileNo);

    FileDtlsEntity selectFileDtlsByFileNoAndSn(@Param("fileNo") String fileNo, @Param("sn") Integer sn);
}

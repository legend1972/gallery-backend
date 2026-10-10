package com.gallery.api.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gallery.api.member.dto.MberNtcnBindingDto;
import com.gallery.api.member.entity.MberNtcnEntity;

@Mapper
public interface MberNtcnMapper {
    public int insertMberNtcn(MberNtcnEntity mberNtcnEntity);

    public int selectCntMberNtcnByMberNo(@Param("mberNo") String mberNo, @Param("redngAt") String redngAt);

    public List<MberNtcnBindingDto> selectListMberNtcnByMberNo(@Param("mberNo") String mberNo, @Param("ntcnCtgryCode") String ntcnCtgryCode, @Param("size") Integer size, @Param("offset") Integer offset);
    
    public int updateMberNtcnByMberNtcnNo(@Param("mberNtcnNo") String mberNtcnNo, @Param("changeMberNo") String changeMberNo, @Param("redngAt") String redngAt);
}

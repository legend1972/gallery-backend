package com.gallery.api.jdgmn.dto;

import java.util.List;

import com.gallery.api.jdgmn.dto.in.JdgmnCeckDetailInDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JdgmnCeckDetailBindingDto {
    private String jdgmnNo;
    private List<JdgmnCeckDetailInDto> jdgmnCeckDetailList;
}

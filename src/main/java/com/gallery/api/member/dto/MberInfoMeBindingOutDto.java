package com.gallery.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MberInfoMeBindingOutDto {
    private String mberNo; //작가회원번호
    private String gnrlMberNo; //일반회원번호
    private String mberSbscrbTyCode;
    private String ncnm;
    private String roleId;
}

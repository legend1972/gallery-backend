package com.gallery.api.member.dto;

import java.util.List;

import com.gallery.api.member.dto.out.MberNtcnCntDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MberNtcnBindingOutDto {
    private List<MberNtcnBindingDto> mberNtcnList;
    private MberNtcnCntDto mberNtcnCntDto;
}

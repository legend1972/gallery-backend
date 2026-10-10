package com.gallery.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MberNtcnSetupBindingInDto {
    private String mberNo;
    private String code;
    private String recptnTy;
    private String value;
}

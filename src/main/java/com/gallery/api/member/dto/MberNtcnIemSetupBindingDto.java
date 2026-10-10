package com.gallery.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MberNtcnIemSetupBindingDto {
    private String mberNo;
    private String ntcnSetupCode;
    private String ntcnSetupNm;
    private String ntcnSetupCn;
    private String ntcnCtgryCode;
    private String iconCode;
    private String aplctnRecptnAt;
    private String emailRecptnAt;
    private String smsRecptnAt;
}

package com.gallery.api.ntcn.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NtcnTyEntity {
    private String ntcnTyCode;
    private String ntcnSetupCode;
    private String ntcnTyNm;
    private String eventTyCode;
    private String sttusCode;
    private String recptnTrgetTyCode;
    private String sjTmpl;
    private String cnTmpl;
    private String trgetTyCode;
    private String aplctnRecptnUseAt;
    private String emailRecptnUseAt;
    private String smsRecptnUseAt;
    private Integer ntcnPd;
    private String menuUrl;
    private String useAt;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

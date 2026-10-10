package com.gallery.api.member.dto.in;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MberInfoInDto {
    private String mberNo;
    private String ncnm;
    private String email;
    private String secretNo;
    private String stplatAgreAt;
    private String indvdlinfoColctAgreAt;
    private String marktColctAgreAt;
    private String emailColctAgreAt;
    private String smsColctAgreAt;
    private LocalDateTime sbscrbDt;
    private LocalDateTime lastConectDt;
    private String mberSttusCode;
    private LocalDateTime sttusChangeDt;
    private String mberSbscrbTyCode;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

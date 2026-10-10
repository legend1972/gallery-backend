package com.gallery.api.member.dto.in;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MberNtcnSetupInDto {
    private String mberNo;
    private String allRecptnAt;
    private String aplctnRecptnAt;
    private String emailRecptnAt;
    private String smsRecptnAt;
    private String ntcnTimeLmttAt;
    private String ntcnBeginTime;
    private String ntcnEndTime;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

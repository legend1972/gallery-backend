package com.gallery.api.member.dto.out;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MberNtcnIemSetupOutDto {
    private String mberNo;
    private String ntcnSetupCode;
    private String aplctnRecptnAt;
    private String emailRecptnAt;
    private String smsRecptnAt;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

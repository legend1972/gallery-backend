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
public class MberNtcnInDto {
    private String mberNtcnNo;
    private String mberNo;
    private String ntcnTyCode;
    private String sj;
    private String cn;
    private String trgetTyCode;
    private String trgetNo;
    private String redngAt;
    private LocalDateTime redngDt;
    private LocalDateTime ntcnEndDt;
    private String useAt;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

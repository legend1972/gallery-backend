package com.gallery.api.member.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MberNtcnBindingDto {
    private String mberNtcnNo;
    private String mberNo;
    private String sj;
    private String cn;
    private String ntcnTyCode;
    private String trgetTyCode;
    private String trgetNo;
    private String redngAt;
    private String menuUrl;
    private String ntcnCtgryCode;
    private String iconCode;
    private LocalDateTime registDt;
    private String fileUrl;
    private String email;
    private Integer allCnt;
    private Integer noread;
    private Integer artwork;
    private Integer exhibition;
    private Integer inquiry;
    private Integer etc;
}

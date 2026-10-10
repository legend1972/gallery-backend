package com.gallery.api.artist.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritrInfoEntity {
    private String mberNo;
    private String gnrlMberNo;
    private String nm;
    private String actCode;
    private String actArea;
    private String telno;
    private String selfIntrcn;
    private String writrFileNo;
    private String reprsntPrdctFileNo;
    private String prdctNm;
    private String mnfctYear;
    private String prdctDc;
    private LocalDate rqstdt;
    private String jdgmnSttusCode;
    private LocalDateTime jdgmnSttusChangeDt;
    private String jdgmnNo;
    private String jdgmnOpinion;
    private String jdgmnSumry;
    private String mberSttusCode;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}
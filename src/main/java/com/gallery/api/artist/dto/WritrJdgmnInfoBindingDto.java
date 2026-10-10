package com.gallery.api.artist.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritrJdgmnInfoBindingDto {
    private String mberNo;
    private LocalDate rqstdt;
    private String nm;
    private String email;
    private String jdgmnSttusNm;
    private String jdgmnRefer1Nm;
    private String jdgmnSumry;
    private String writrFileNo;
    private Integer writrSn;
    private String writrFileUrl;
    private String reprsntPrdctFileNo;
    private Integer reprsntPrdctSn;
    private String reprsntPrdctFileUrl;
    private String jdgmnSttusCode;
    private Integer allCnt;
    private Integer pend;
    private Integer revw;
    private Integer cmpl;
    private Integer rejt;
    private Integer rchg;
}

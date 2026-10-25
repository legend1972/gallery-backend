package com.gallery.api.prdct.entity;

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
public class PrdctActEntity {
    // 작품활동번호
    private String prdctActNo;
    // 작품순번
    private Integer prdctSn;
    // 회원번호
    private String mberNo;
    // 활동유형코드
    private String actTyCode;
    // 활동명
    private String actNm;
    // 활동시작일자
    private LocalDate actBgnde;
    // 활동종료일자
    private LocalDate actEndde;
    // 활동장소
    private String actPlace;
    // 대표작품파일번호
    private String reprsntPrdctFileNo;
    // 활동소개
    private String actIntrcn;
    // 공개여부
    private String othbcAt;
    // 사용여부
    private String useAt;
    // 생성회원번호
    private String creatMberNo;
    // 생성일시
    private LocalDateTime creatDt;
    // 변경회원번호
    private String changeMberNo;
    // 변경일시
    private LocalDateTime changeDt;
}

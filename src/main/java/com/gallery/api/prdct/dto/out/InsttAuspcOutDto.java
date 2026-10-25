package com.gallery.api.prdct.dto.out;

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
public class InsttAuspcOutDto {
    // 기관주최번호
    private String insttAuspcNo;

    // 순번
    private Integer sn;

    // 작품활동번호
    private String prdctActNo;

    // 작품순번
    private Integer prdctSn;

    // 회원번호
    private String mberNo;

    // 기관주최명
    private String insttAuspcNm;

    // 역할코드
    private String roleCode;

    // 참여시작일자
    private LocalDate partcptnBgnde;

    // 참여종료일자
    private LocalDate partcptnEndde;

    // 기관주최소개
    private String insttAuspcIntrcn;

    // 대표파일번호
    private String reprsntFileNo;

    // 주소
    private String adres;

    // 전화번호
    private String telno;

    // 홈페이지
    private String hmpg;

    // 생성회원번호
    private String creatMberNo;

    // 생성일시
    private LocalDateTime creatDt;

    // 변경회원번호
    private String changeMberNo;

    // 변경일시
    private LocalDateTime changeDt;
}

package com.gallery.commn;

public class StcCommVari {
    /**
     * 회원상태
     */
    public static final String MBER_STTUS_ACTV = "ACTV"; //활성

    /**
     * 작가상태
     */
    public static final String WRITR_STTUS_ACTV = "ACTV"; //활성
    public static final String WRITR_STTUS_SUSP = "SUSP"; //활동 정지
    public static final String WRITR_STTUS_BANN = "BANN"; //무기한 정지
    public static final String WRITR_STTUS_WDRW = "WDRW"; //작가 탈퇴
    public static final String WRITR_STTUS_REJT = "REJT"; //거절
    
    /**
     * 회원가입유형
     */
    public static final String MBER_SBSCRB_TY_GRNL = "GRNL"; //일반회원
    public static final String MBER_SBSCRB_TY_ARTS = "ARTS"; //작가회원
    public static final String MBER_SBSCRB_TY_OPER = "OPER"; //운영담당자
    public static final String MBER_SBSCRB_TY_ADMN = "ADMN"; //일반관리자
    public static final String MBER_SBSCRB_TY_SUAD = "SUAD"; //슈퍼권한자
    public static final String MBER_SBSCRB_TY_GUST = "GUST"; //비회원

    /**
     * 심사상태
     */
    public static final String JDGMN_STTUS_PEND = "PEND"; //승인 대기
    public static final String JDGMN_STTUS_RE_PEND = "RE_PEND"; //재승인 대기. 공통코드에서는 관리하지 않음
    public static final String JDGMN_STTUS_CHNG = "CHNG"; //리뷰일 때 임시 저장 시 필요. 공통코드에서는 관리하지 않음
    public static final String JDGMN_STTUS_REVW = "REVW"; //리뷰
    public static final String JDGMN_STTUS_CMPL = "CMPL"; //승인 완료
    public static final String JDGMN_STTUS_REJT = "REJT"; //승인 거절
    public static final String JDGMN_STTUS_RCHG = "RCHG"; //수정 요청

    public static final String JDGMN_ADMIN_MBER_NO = "MBN000000031"; //심사관리자회원번호

    /**
     * 알림유형
     */
    public static final String NTCN_TY_WRITR_JDGMN_ADMIN_RE_REQ = "WRITR_JDGMN_ADMIN_RE_REQ";
    public static final String NTCN_TY_EXBT_JDGMN_PEND = "EXBT_JDGMN_PEND";
    public static final String NTCN_TY_EXBT_JDGMN_REVW = "EXBT_JDGMN_REVW";
    public static final String NTCN_TY_EXBT_JDGMN_RCHG = "EXBT_JDGMN_RCHG";
    public static final String NTCN_TY_EXBT_JDGMN_RE_PEND = "EXBT_JDGMN_RE_PEND";
    public static final String NTCN_TY_EXBT_JDGMN_CMPL = "EXBT_JDGMN_CMPL";
    public static final String NTCN_TY_EXBT_JDGMN_REJT = "EXBT_JDGMN_REJT";
    public static final String NTCN_TY_EXBT_JDGMN_ADMIN_REQ = "EXBT_JDGMN_ADMIN_REQ";
    public static final String NTCN_TY_EXBT_JDGMN_ADMIN_RE_REQ = "EXBT_JDGMN_ADMIN_RE_REQ";
    public static final String NTCN_TY_WRITR_JDGMN_PEND = "WRITR_JDGMN_PEND";
    public static final String NTCN_TY_WRITR_JDGMN_REVW = "WRITR_JDGMN_REVW";
    public static final String NTCN_TY_WRITR_JDGMN_RCHG = "WRITR_JDGMN_RCHG";
    public static final String NTCN_TY_WRITR_JDGMN_RE_PEND = "WRITR_JDGMN_RE_PEND";
    public static final String NTCN_TY_WRITR_JDGMN_CMPL = "WRITR_JDGMN_CMPL";
    public static final String NTCN_TY_WRITR_JDGMN_REJT = "WRITR_JDGMN_REJT";
    public static final String NTCN_TY_WRITR_JDGMN_ADMIN_REQ = "WRITR_JDGMN_ADMIN_REQ";

    /**
     * 알림설정
     */
    public static final String NTCN_SETUP_WRITR_JDGMN_ADMIN = "WRITR_JDGMN_ADMIN"; //관리자 작가 심사 요청
    public static final String NTCN_SETUP_WRITR_JDGMN = "WRITR_JDGMN"; //작사 심사
    public static final Integer NTCN_END_WRITR_JDGMN_ADMIN_DAY = 3;

    public static final String MNGR_MBER_NO = "MNG000000001"; //매니저회원번호

    public static final String GROUP_CD_IMAGE_EXT = "IMAGE_EXT"; //이미지 확장자

    /**
     * 역할아이디
     */
    public static final String ROLE_ID_R0001 = "R0001";
    public static final String ROLE_ID_R0002 = "R0002";
    public static final String ROLE_ID_R0003 = "R0003";
    public static final String ROLE_ID_R0004 = "R0004";
    public static final String ROLE_ID_R0005 = "R0005";
    public static final String ROLE_ID_R0006 = "R0006";
}

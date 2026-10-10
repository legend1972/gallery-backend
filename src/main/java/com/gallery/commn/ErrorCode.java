package com.gallery.commn;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    MEMBER_FOUND(HttpStatus.FOUND, "이메일로 등록된 회원이 있습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원번호가 없습니다. 확인하세요."),
    MEMBER_NTCN_NOT_FOUND(HttpStatus.NOT_FOUND, "회원알림 정보가 없습니다. 확인하세요."),
    WRITR_FOUND(HttpStatus.FOUND, "등록된 작가 정보가 있습니다. 확인하세요."),
    WRITR_NOT_FOUND(HttpStatus.FOUND, "등록된 작가 정보가 없습니다. 확인하세요."),
    WRITR_JDGMN_STTUS_NOT_FOUND(HttpStatus.NOT_FOUND, "심사 상태가 변경이 되었습니다. 확인하세요."),
    WRITR_JDGMN_STTUS_NO(HttpStatus.NOT_FOUND, "알수 없는 심사 상태 코드입니다. 확인하세요."),
    WRITR_JDGMN_CHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, "작가심사변경이력 정보가 없습니다. 확인하세요."),
    JDGMN_CECK_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "심사체크상세 정보가 없습니다. 확인하세요."),
    WRITR_STTUS_CHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, "작가상태변경이력 정보가 없습니다. 확인하세요."),
    MEMBER_WRITR_NO_CONFLICT(HttpStatus.CONFLICT, "작가의 일반회원번호와 로그인한 회원번호가 일치하지 않습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "요청 본문에 값이 비어서 왔습니다. 확인하세요."),
    IMAGE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "정상적인 이미지 파일이 아닙니다. 확인하세요."),
    ROLE_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "역할 정보에 조회되는 대상이 없습니다. 확인하세요."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입에 실패했습니다. 확인하세요."),
    INFO_ERROR(HttpStatus.NOT_FOUND, "이메일 정보 혹은 패스워드가 잘못되었습니다. 확인하세요."),
    SESSION_ERROR(HttpStatus.UNAUTHORIZED, "세션 정보가 없습니다. 다시 로그인하세요."),
    MENU_ERROR(HttpStatus.NOT_FOUND, "메뉴 정보가 없습니다. 확인하세요."),
    FILE_NULL_ISEMPTY(HttpStatus.NOT_FOUND, "파일 정보가 없거나 빈 파일입니다. 확인하세요."),
    CONTENT_TYPE_ERROR(HttpStatus.BAD_REQUEST, "MIME 타입이 요청한 정보와 맞지 않습니다. 확인하세요."),
    EXTSN_ERROR(HttpStatus.BAD_REQUEST, "확장자가 요청한 정보와 맞지 않습니다. 확인하세요."),
    GROUP_CD_INFO_NOT_FOUND(HttpStatus.NOT_FOUND,  "요청한 공통 코드가 없습니다. 확인하세요."),
    DIRECTORY_CREAT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "디렉토리 생성 오류입니다. 확인하세요."),
    FILE_COPY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 복사 오류입니다. 확인하세요."),
    MBER_ROLE_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "회원역할 정보에 조회되는 대상이 없습니다. 확인하세요."),
    //STTUS_CONFLICT(HttpStatus.CONFLICT, "승인 단계가 신청대기 혹은 정보수정 외에 신청정보는 수정할 수 없습니다."),
    CAREER_NOT_FOUND(HttpStatus.NOT_FOUND, "경력정보가 없습니다. 확인하세요."),
    URL_NOT_FOUND(HttpStatus.NOT_FOUND, "URL정보가 없습니다. 확인하세요."),
    FILE_DTLS_NOT_FOUND(HttpStatus.NOT_FOUND, "파일내역이 없습니다. 확인하세요."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일이 없습니다. 확인하세요."),
    NTCN_SETUP_IEM_ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "알림설정항목역할에 조회되는 대상이 없습니다. 확인하세요."),
    NTCN_IEM_SETUP_NOT_FOUND(HttpStatus.NOT_FOUND, "회원알림항목설정에 조회되는 대상이 없습니다. 확인하세요."),
    NTCN_SETUP_NOT_FOUND(HttpStatus.NOT_FOUND, "회원알림설정에 조회되는 대상이 없습니다. 확인하세요."),
    NTCN_SETUP_ERROR(HttpStatus.NOT_FOUND, "알림유형오류입니다. 확인하세요."),
    NTCN_TY_NOT_FOUND(HttpStatus.NOT_FOUND, "알림유형에 조회되는 대상이 없습니다. 확인하세요.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus geHttpStatus() {
        return this.httpStatus;
    }

    public String getMessage() {
        return this.message;
    }
}

package com.gallery.api.member.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gallery.api.artist.dto.out.WritrInfoOutDto;
import com.gallery.api.artist.service.WritrInfoService;
import com.gallery.api.member.dto.MberInfoMeBindingOutDto;
import com.gallery.api.member.dto.in.MberInfoInDto;
import com.gallery.api.member.dto.in.MberNtcnIemSetupInDto;
import com.gallery.api.member.dto.in.MberNtcnSetupInDto;
import com.gallery.api.member.dto.in.MberSttusChangeHistInDto;
import com.gallery.api.member.dto.out.MberInfoOutDto;
import com.gallery.api.member.service.MberInfoService;
import com.gallery.api.member.service.MberNtcnIemSetupService;
import com.gallery.api.member.service.MberNtcnSetupService;
import com.gallery.api.member.service.MberSttusChangeHistService;
import com.gallery.api.role.dto.in.MberRoleInDto;
import com.gallery.api.role.dto.in.RoleInfoInDto;
import com.gallery.api.role.dto.out.MberRoleOutDto;
import com.gallery.api.role.dto.out.RoleInfoOutDto;
import com.gallery.api.role.service.MberRoleService;
import com.gallery.api.role.service.RoleInfoService;
import com.gallery.commn.ApiResponse;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.StcCommVari;
import com.gallery.commn.exception.CustomException;
import com.gallery.commn.utils.PwUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MberInfoController {
    private final MberInfoService mberInfoService;

    private final MberSttusChangeHistService mberSttusChangeHistService;

    private final RoleInfoService roleInfoService;

    private final MberRoleService mberRoleService;

    private final MberNtcnSetupService mberNtcnSetupService;

    private final MberNtcnIemSetupService mberNtcnIemSetupService;

    private final WritrInfoService writrInfoService;

    private static Logger logger = LoggerFactory.getLogger(MberInfoController.class);

    /**
     * 이메일로 가입한 회원이 있는지 확인
     * @param mberInfoInDto
     * @return
     */
    @PostMapping("/dplct")
    public ResponseEntity<ApiResponse<Void>> getMberInfoByEmail(@RequestBody MberInfoInDto mberInfoInDto ) {
        if(mberInfoInDto == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        //logger.info("mberInfInDto.getStplatAgreAt(): " + mberInfoInDto.getStplatAgreAt());
        
        /******************************
         * 이메일로 가입한 회원이 있는지 확인
         ******************************/
        MberInfoOutDto mberInfoOutDto = mberInfoService.getMberInfoByEmail(mberInfoInDto);
        if(mberInfoOutDto != null) {
            throw new CustomException(ErrorCode.MEMBER_FOUND);
        }

        return ResponseEntity.ok(ApiResponse.success("요청한 이메일로 등록된 회원이 없습니다. 회원 가입 진행하세요.", null));
    }

    /**
     * 일반 회원 가입
     * @param mberInfoInDto
     * @return
     */
    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<ApiResponse<Void>> createMberInfo(@RequestBody MberInfoInDto mberInfoInDto) {
        if(mberInfoInDto == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        mberInfoInDto.setSecretNo(PwUtils.hashPw(mberInfoInDto.getSecretNo()));
        mberInfoInDto.setMberSttusCode(StcCommVari.MBER_STTUS_ACTV);
        mberInfoInDto.setMberSbscrbTyCode(StcCommVari.MBER_SBSCRB_TY_GRNL);
        mberInfoInDto.setCreatMberNo(StcCommVari.MNGR_MBER_NO);
        mberInfoInDto.setChangeMberNo(StcCommVari.MNGR_MBER_NO);

        /******************************
         * 일반회원 등록
         ******************************/
        String mberNo = mberInfoService.createMberInfo(mberInfoInDto);
        if(mberNo == null) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        /******************************
         * 회원상태변경이력 등록
         ******************************/
        mberSttusChangeHistService.createMberSttusChangeHist(MberSttusChangeHistInDto
            .builder()
                .mberNo(mberNo)
                .endDt(LocalDateTime.of(9999, 12, 31, 23, 59, 59))
                .mberSttusCode(StcCommVari.MBER_STTUS_ACTV)
                .mberChangeResnCode(null)
                .resnDetail(null)
                .creatMberNo(StcCommVari.MNGR_MBER_NO)
                .changeMberNo(StcCommVari.MNGR_MBER_NO)
            .build());

        /******************************
         * 역할정보 조회
         ******************************/
        RoleInfoOutDto roleInfoOutDto = roleInfoService.getRoleInfo(RoleInfoInDto
            .builder()
                .bassRoleCode(StcCommVari.MBER_SBSCRB_TY_GRNL)
            .build())
            ;

        if(roleInfoOutDto == null) {
            throw new CustomException(ErrorCode.ROLE_INFO_NOT_FOUND);
        }

        /******************************
         * 회원역할 등록
         ******************************/
        mberRoleService.createMberRole(MberRoleInDto
            .builder()
                .mberNo(mberNo)
                .roleId(roleInfoOutDto.getRoleId())
                .alwncMberNo(StcCommVari.MNGR_MBER_NO)
                .endDt(LocalDateTime.of(9999, 12, 31, 23, 59, 59))
                .creatMberNo(StcCommVari.MNGR_MBER_NO)
                .changeMberNo(StcCommVari.MNGR_MBER_NO)
            .build());

        /******************************
         * 회원알림설정 등록
         ******************************/
        mberNtcnSetupService.createMberNtcnSetup(MberNtcnSetupInDto
            .builder()
                .mberNo(mberNo)
                .allRecptnAt("Y")
                .aplctnRecptnAt("Y")
                .emailRecptnAt("Y")
                .smsRecptnAt("N")
                .ntcnTimeLmttAt("Y")
                .ntcnBeginTime("090000")
                .ntcnEndTime("220000")
                .creatMberNo(StcCommVari.MNGR_MBER_NO)
                .changeMberNo(StcCommVari.MNGR_MBER_NO)
            .build()
        );

        /******************************
         * 회원알림항목설정 등록
         ******************************/
        mberNtcnIemSetupService.createListMberNtcnIemSetup(MberNtcnIemSetupInDto
            .builder()
                .mberNo(mberNo)
                .aplctnRecptnAt("Y")
                .emailRecptnAt("Y")
                .smsRecptnAt("N")
                .creatMberNo(StcCommVari.MNGR_MBER_NO)
                .changeMberNo(StcCommVari.MNGR_MBER_NO)
            .build()
            ,roleInfoOutDto.getRoleId()
        );

        return ResponseEntity.ok(ApiResponse.success("회원가입에 성공했습니다.", null));
    }

    /**
     * 로그인 처리
     * @param mberInfoInDto
     * @return
     */
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<ApiResponse<Void>> getMberInfoByEmailAndSecretNo(@RequestBody MberInfoInDto mberInfoInDto, HttpSession session) {
        boolean pw = false;

        if(mberInfoInDto == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
        
        /******************************
         * 이메일로 가입한 회원이 있는지 확인
         ******************************/
        MberInfoOutDto mberInfoOutDto = mberInfoService.getMberInfoByEmail(mberInfoInDto);
        if(mberInfoOutDto != null) {
            /*******************
             * 로그인 시 입력한 패스워드와 테이블에 등록된 패스워드가 같은지 확인
             *******************/
            pw = PwUtils.checkPw(mberInfoInDto.getSecretNo(), mberInfoOutDto.getSecretNo());
        }

        // logger.info(mberInfoInDto.getSecretNo() + ":" + mberInfoOutDto.getSecretNo());

        if(mberInfoOutDto == null || !pw) {
            throw new CustomException(ErrorCode.INFO_ERROR);
        }

        /******************************
         * 회원번호로 마지막접속일시 변경
         ******************************/
        mberInfoService.changeMberInfoByMberNo(mberInfoOutDto.getMberNo(), mberInfoOutDto.getMberNo());

        /*******************
         * 세션 정보 세팅
         *******************/
        session.setAttribute("loginMember", mberInfoOutDto);

        // map.put("success", true);
        // map.put("message", "로그인에 성공했습니다.");

        return ResponseEntity.ok(ApiResponse.success("로그인에 성공했습니다.", null));
    }

    /**
     * 사용자 세션 정보 조회
     * @param session
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MberInfoMeBindingOutDto>> getLoginMember(HttpSession session) {
        logger.debug("사용자 세션 정보 조회");
        /******************************
         * 세션 정보 취득
         ******************************/
        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto) session.getAttribute("loginMember");
        if(mberInfoOutDto == null) {
            // throw new CustomException(ErrorCode.SESSION_ERROR);
            logger.debug("mberInfoOutDto 값이 null 입니다.");
            return ResponseEntity.ok(ApiResponse.success(null));
        }

        String mberNo = "";
        MberRoleOutDto mberRoleOutDto;

        /******************************
         * 회원번호로 작가 정보가 있는지 확인
         ******************************/
        WritrInfoOutDto writrInfoOutDto = writrInfoService.getWritrInfoByGnrlMberNo(mberInfoOutDto.getMberNo());
        /**
         * 심사 상태가 승인이 난 견우와 아닌 경우
         */
        if(writrInfoOutDto != null && ObjectUtils.nullSafeEquals(StcCommVari.JDGMN_STTUS_CMPL, writrInfoOutDto.getJdgmnSttusCode())) {
            mberNo = writrInfoOutDto.getMberNo();
            /******************************
             * 회원번호 조건으로 회원역할 조회
             ******************************/
            mberRoleOutDto = mberRoleService.getMberRoleByMberNo(mberNo);
        } else {
            /******************************
             * 회원번호 조건으로 회원역할 조회
             ******************************/
            mberRoleOutDto = mberRoleService.getMberRoleByMberNo(mberInfoOutDto.getMberNo());
        }

        return ResponseEntity.ok(ApiResponse.success(MberInfoMeBindingOutDto
            .builder()
                .mberNo(mberNo)
                .gnrlMberNo(mberInfoOutDto.getMberNo())
                .mberSbscrbTyCode(mberInfoOutDto.getMberSbscrbTyCode())
                .ncnm(mberInfoOutDto.getNcnm())
                .roleId(mberRoleOutDto.getRoleId())
            .build()));
    }

    /**
     * 로그아웃 처리
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok(ApiResponse.success("성공적으로 로그아웃되었습니다.", null));
    }
}

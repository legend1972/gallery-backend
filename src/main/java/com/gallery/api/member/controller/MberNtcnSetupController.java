package com.gallery.api.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gallery.api.member.dto.MberNtcnSetupBindingInDto;
import com.gallery.api.member.dto.in.MberNtcnSetupInDto;
import com.gallery.api.member.dto.out.MberInfoOutDto;
import com.gallery.api.member.dto.out.MberNtcnSetupOutDto;
import com.gallery.api.member.service.MberNtcnSetupService;
import com.gallery.commn.ApiResponse;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@RequestMapping("/api/ntcn/setup")
@RestController
@AllArgsConstructor
public class MberNtcnSetupController {
    private final MberNtcnSetupService mberNtcnSetupService;

    /**
     * 회원알림설정 조회
     * @param mberNo
     * @return
     */
    @GetMapping("/inqire")
    public ResponseEntity<ApiResponse<MberNtcnSetupOutDto>> getMberNtcnSetup(@RequestParam String mberNo) {
        if(mberNo == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        /******************************
         * 회원알림설정 조회
         ******************************/
        MberNtcnSetupOutDto mberNtcnSetupOutDto = mberNtcnSetupService.getMberNtcnSetupByMberNo(mberNo);

        return ResponseEntity.ok(ApiResponse.success("회원알림설정을 성공적으로 조회했습니다.", mberNtcnSetupOutDto));
    }

    /**
     * 회원알림설정 변경
     * @param mberNtcnSetupBindingInDto
     * @param session
     * @return
     */
    @PostMapping("/change")
    public ResponseEntity<ApiResponse<Void>> changeMberNtcnSetup(@RequestBody MberNtcnSetupBindingInDto mberNtcnSetupBindingInDto, HttpSession session) {
        if(mberNtcnSetupBindingInDto == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        /******************************
         * 세션 정보 취득
         ******************************/
        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto) session.getAttribute("loginMember");
        if(mberInfoOutDto == null) {
            throw new CustomException(ErrorCode.SESSION_ERROR);
        }

        String mberNo = mberInfoOutDto.getMberNo();

        MberNtcnSetupInDto mberNtcnSetupInDto = new MberNtcnSetupInDto();
        mberNtcnSetupInDto.setMberNo(mberNtcnSetupBindingInDto.getMberNo());
        mberNtcnSetupInDto.setChangeMberNo(mberNo);

        String value = mberNtcnSetupBindingInDto.getValue();
        String recptnTy = mberNtcnSetupBindingInDto.getRecptnTy();
        if(ObjectUtils.nullSafeEquals("ALL", recptnTy)) {
            mberNtcnSetupInDto.setAllRecptnAt(value);
        } else if(ObjectUtils.nullSafeEquals("APLCTN", recptnTy)) {
            mberNtcnSetupInDto.setAplctnRecptnAt(value);
        } else if(ObjectUtils.nullSafeEquals("EMAIL", recptnTy)) {
            mberNtcnSetupInDto.setEmailRecptnAt(value);
        } else if(ObjectUtils.nullSafeEquals("SMS", recptnTy)) {
            mberNtcnSetupInDto.setSmsRecptnAt(value);
        } else if(ObjectUtils.nullSafeEquals("LMTT", recptnTy)) {
            mberNtcnSetupInDto.setNtcnTimeLmttAt(value);
        } else if(ObjectUtils.nullSafeEquals("BEGIN", recptnTy)) {
            mberNtcnSetupInDto.setNtcnBeginTime(value);
        } else if(ObjectUtils.nullSafeEquals("END", recptnTy)) {
            mberNtcnSetupInDto.setNtcnEndTime(value);
        } else {
            throw new CustomException(ErrorCode.NTCN_SETUP_ERROR);
        }

        /******************************
         * 회원알림설정 변경
         ******************************/
        mberNtcnSetupService.changeMberNtcnSetupByMberNo(mberNtcnSetupInDto);
        
        return ResponseEntity.ok(ApiResponse.success("회원알림설정 변경을 성공했습니다.", null));
    }
}

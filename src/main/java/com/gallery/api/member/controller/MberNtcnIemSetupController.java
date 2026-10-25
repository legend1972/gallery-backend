package com.gallery.api.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gallery.api.member.dto.MberNtcnIemSetupBindingDto;
import com.gallery.api.member.dto.MberNtcnSetupBindingInDto;
import com.gallery.api.member.dto.in.MberNtcnIemSetupInDto;
import com.gallery.api.member.dto.out.MberInfoOutDto;
import com.gallery.api.member.service.MberNtcnIemSetupService;
import com.gallery.commn.ApiResponse;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/ntcn/iem/setup")
@RestController
@RequiredArgsConstructor
public class MberNtcnIemSetupController {
    private final MberNtcnIemSetupService mberNtcnIemSetupService;

    /**
     * 회원알림항목설정 및 알림설정항목 조회
     * @param mberNo
     * @param roleId
     * @return
     */
    @GetMapping("/inqire")
    public ResponseEntity<ApiResponse<List<MberNtcnIemSetupBindingDto>>> getListMberNtcnIemSetup(@RequestParam String mberNo, @RequestParam String roleId) {
        if(mberNo == null || roleId == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        List<MberNtcnIemSetupBindingDto> mberNtcnIemSetupBindingDtoList = mberNtcnIemSetupService.getListMberNtcnIemSetupByMberNoAndRoldId(mberNo, roleId);

        return ResponseEntity.ok(ApiResponse.success("회원알림항목설정 및 알림설정항목을 성공적으로 조회했습니다.", mberNtcnIemSetupBindingDtoList));
    }

    /**
     * 회원알림항목설정 변경
     * @param mberNtcnSetupBindingInDto
     * @param session
     * @return
     */
    @PostMapping("/change")
    @Transactional
    public ResponseEntity<ApiResponse<Void>> createMberNtcnIemSetup(@RequestBody MberNtcnSetupBindingInDto mberNtcnSetupBindingInDto, HttpSession session) {
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

        MberNtcnIemSetupInDto mberNtcnIemSetupInDto = new MberNtcnIemSetupInDto();

        mberNtcnIemSetupInDto.setMberNo(mberNtcnSetupBindingInDto.getMberNo());
        mberNtcnIemSetupInDto.setNtcnSetupCode(mberNtcnSetupBindingInDto.getCode());
        mberNtcnIemSetupInDto.setCreatMberNo(mberNo);
        mberNtcnIemSetupInDto.setChangeMberNo(mberNo);

        String value = mberNtcnSetupBindingInDto.getValue();
        String recptnTy = mberNtcnSetupBindingInDto.getRecptnTy();
        if(ObjectUtils.nullSafeEquals("APLCTN", recptnTy)) {
            mberNtcnIemSetupInDto.setAplctnRecptnAt(value);
        } else if(ObjectUtils.nullSafeEquals("EMAIL", recptnTy)) {
            mberNtcnIemSetupInDto.setEmailRecptnAt(value);
        } else if(ObjectUtils.nullSafeEquals("SMS", recptnTy)) {
            mberNtcnIemSetupInDto.setSmsRecptnAt(value);
        } else {
            throw new CustomException(ErrorCode.NTCN_SETUP_ERROR);
        }

        /******************************
         * 회원알림항목설정 변경 혹은 등록
         ******************************/
        mberNtcnIemSetupService.createMberNtcnIemSetup(mberNtcnIemSetupInDto);

        return ResponseEntity.ok(ApiResponse.success("회원알림항목설정 변경을 성공했습니다.", null));
    }
}

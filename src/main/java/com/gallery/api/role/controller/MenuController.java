package com.gallery.api.role.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gallery.api.codes.controller.CmmnCdController;
import com.gallery.api.common.ArtistService;
import com.gallery.api.member.dto.out.MberInfoOutDto;
import com.gallery.api.role.dto.out.MenuOutDto;
import com.gallery.api.role.service.MenuService;
import com.gallery.commn.ApiResponse;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.StcCommVari;
import com.gallery.commn.exception.CustomException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    private final ArtistService artistService;

    private static Logger logger = LoggerFactory.getLogger(CmmnCdController.class);

    /**
     * 메뉴 조회
     * @param mberInfoInDto
     * @return
     */
    @GetMapping("/routes")
    public ResponseEntity<ApiResponse<List<MenuOutDto>>> getLstMenuByMNoAndRCode(HttpSession session) {
        /******************************
         * 1. 세션 정보 취득
         * 2. 회원번호 및 가입유형코드 조회
         ******************************/
        String roleId = "";
        String mberNo = "";
        String mberSbscrbTyCode = "";
        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto) session.getAttribute("loginMember");
        if(mberInfoOutDto == null) {
            roleId = StcCommVari.ROLE_ID_R0006;
            mberSbscrbTyCode = StcCommVari.MBER_SBSCRB_TY_GUST;
        } else {
            mberNo = mberInfoOutDto.getMberNo();
            mberSbscrbTyCode = mberInfoOutDto.getMberSbscrbTyCode();
        }

        /******************************
         * 작가회원인 경우 작가회원번호 리턴, 그렇지 않으면 일반회원번호 리턴
         ******************************/    
        mberNo = artistService.getMberNo(mberNo, mberSbscrbTyCode);

        // if(ObjectUtils.nullSafeEquals(StcCommVari.MBER_SBSCRB_TY_ARTS, mberSbscrbTyCode)) {
        //     /******************************
        //      * 회원번호로 작가 정보가 있는지 확인
        //      ******************************/    
        //     mberNo = Optional.ofNullable(writrInfoService.getWritrInfoByGnrlMberNo(mberNo)).orElseGet(WritrInfoOutDto::new).getMberNo();
        //     if(mberNo == null) {
        //         throw new CustomException(ErrorCode.WRITR_NOT_FOUND);
        //     }
        // }

        /******************************
         * 메뉴 조회
         ******************************/
        //logger.info(mberNo + ":" + mberSbscrbTyCode);

        List<MenuOutDto> menuOutDtoLst = menuService.getLstMenuByMNoAndRCode(roleId, mberNo, mberSbscrbTyCode);
        if(menuOutDtoLst.size() == 0) {
            throw new CustomException(ErrorCode.MENU_ERROR);
        }

        return ResponseEntity.ok(ApiResponse.success("정상 조회 되었습니다.", menuOutDtoLst));
    }
}

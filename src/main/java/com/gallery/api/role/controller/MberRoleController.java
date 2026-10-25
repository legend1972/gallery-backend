package com.gallery.api.role.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gallery.api.artist.dto.out.WritrInfoOutDto;
import com.gallery.api.artist.service.WritrInfoService;
import com.gallery.api.common.ArtistService;
import com.gallery.api.member.dto.out.MberInfoOutDto;
import com.gallery.api.role.dto.MberRoleBindingDto;
import com.gallery.api.role.dto.MberRoleBindingOutDto;
import com.gallery.api.role.dto.MenuAndRoleBindingDto;
import com.gallery.api.role.dto.out.MberRoleOutDto;
import com.gallery.api.role.service.MberRoleService;
import com.gallery.commn.ApiResponse;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.StcCommVari;
import com.gallery.commn.exception.CustomException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mber/role")
@RequiredArgsConstructor
public class MberRoleController {
    private final MberRoleService mberRoleService;
    
    private final WritrInfoService writrInfoService;

    private final ArtistService artistService;

    /**
     * 전체 메뉴 조회
     * @param session
     * @param roleId
     * @return
     */
    @GetMapping("/menu/all")
    public ResponseEntity<ApiResponse<List<MberRoleBindingOutDto>>> getListAllMenuByMberNoAndRole(HttpSession session, @RequestParam String nm) {
        String mberNo = ""; //작가회원번호
        String roleId = "";
        
        /******************************
         * 1. 세션 정보 취득
         * 2. 회원번호 및 가입유형코드 조회
         ******************************/
        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto) session.getAttribute("loginMember");
        /**
         * 세션 정보가 없는 경우 롤 권한은 비회원 처리
         */
        if(mberInfoOutDto == null) {
            roleId = StcCommVari.ROLE_ID_R0006;
        } else {
            String gnrlMberNo = mberInfoOutDto.getMberNo(); //일반회원번호
            String mberSbscrbTyCode = mberInfoOutDto.getMberSbscrbTyCode(); //회원가입유형코드
            /**
             * 회원이 작가인 경우
             */
            /******************************
             * 작가회원인 경우 작가회원번호 리턴, 그렇지 않으면 일반회원번호 리턴
             ******************************/    
            mberNo = artistService.getMberNo(gnrlMberNo, mberSbscrbTyCode);

            // if(ObjectUtils.nullSafeEquals(StcCommVari.MBER_SBSCRB_TY_ARTS, mberSbscrbTyCode)) {
            //     WritrInfoOutDto writrInfoOutDto = writrInfoService.getWritrInfoByGnrlMberNo(gnrlMberNo);
            //     if(writrInfoOutDto == null) {
            //         throw new CustomException(ErrorCode.WRITR_NOT_FOUND);
            //     }
            //     /**
            //      * 작가회원번호
            //      */
            //     mberNo = writrInfoOutDto.getMberNo();
            // } else {
            //     /**
            //      * 일반회원번호
            //      */
            //     mberNo = gnrlMberNo;
            // }

            /******************************
             * 회원번호 조건으로 회원역할 조회
             ******************************/
            MberRoleOutDto mberRoleOutDto = mberRoleService.getMberRoleByMberNo(mberNo);
            roleId = mberRoleOutDto.getRoleId();
        }
        
        Map<String, MberRoleBindingOutDto> map = new LinkedHashMap<>();

        /******************************
         * 전체 메뉴 조회
         ******************************/
        List<MberRoleBindingDto> mberRoleBindingDtoList = mberRoleService.getListAllMenuByMberNoAndRole(roleId, mberNo, nm);
        for(MberRoleBindingDto mberRoleBindingDto: mberRoleBindingDtoList) {
            String groupId = mberRoleBindingDto.getGroupId();
            MberRoleBindingOutDto group = map.computeIfAbsent(groupId, id -> {
                MberRoleBindingOutDto mberRoleBindingOutDto = MberRoleBindingOutDto
                    .builder()
                        .groupId(mberRoleBindingDto.getGroupId())
                        .groupNm(mberRoleBindingDto.getGroupNm())
                        .groupDc(mberRoleBindingDto.getGroupDc())
                    .build();
                
                return mberRoleBindingOutDto;
            });

            group.getList().add(MenuAndRoleBindingDto
                .builder()
                    .menuId(mberRoleBindingDto.getMenuId())
                    .menuNm(mberRoleBindingDto.getMenuNm())
                    .menuDc(mberRoleBindingDto.getMenuDc())
                    .menuUrl(mberRoleBindingDto.getMenuUrl())
                    .iconNm(mberRoleBindingDto.getIconNm())
                    .iconColor(mberRoleBindingDto.getIconColor())
                    .inqirePosblAt(mberRoleBindingDto.getInqirePosblAt())
                    .registPosblAt(mberRoleBindingDto.getRegistPosblAt())
                    .changePosblAt(mberRoleBindingDto.getChangePosblAt())
                    .deletePosblAt(mberRoleBindingDto.getDeletePosblAt())
                .build());
        }

        /**
         * 그룹 정보 및 메뉴 정보 리스트
         */
        List<MberRoleBindingOutDto> list = new ArrayList<>(map.values());

        return ResponseEntity.ok(ApiResponse.success("전체 메뉴 조회에 성공했습니다.", list));
    }
}

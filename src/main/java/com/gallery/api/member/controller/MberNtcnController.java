package com.gallery.api.member.controller;

import com.gallery.api.file.dto.out.FileDtlsOutDto;
import com.gallery.api.file.service.FileDtlsService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gallery.api.artist.dto.out.WritrInfoOutDto;
import com.gallery.api.artist.service.WritrInfoService;
import com.gallery.api.member.dto.MberNtcnBindingDto;
import com.gallery.api.member.dto.MberNtcnBindingOutDto;
import com.gallery.api.member.dto.out.MberInfoOutDto;
import com.gallery.api.member.dto.out.MberNtcnCntDto;
import com.gallery.api.member.service.MberInfoService;
import com.gallery.api.member.service.MberNtcnService;
import com.gallery.commn.ApiResponse;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.exception.CustomException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ntcn")
@RequiredArgsConstructor
public class MberNtcnController {
    private final FileDtlsService fileDtlsService;

    private final MberNtcnService mberNtcnService;

    private final WritrInfoService writrInfoService;

    private final MberInfoService mberInfoService;

    /**
     * 읽지 않은 회원알림 건수 조회
     * @param session
     * @return
     */
    @GetMapping("/unread/cnt")
    public ResponseEntity<ApiResponse<Integer>> getCntMberNtcnByMberNo(HttpSession session) {
        int cnt = 0;
        /******************************
         * 세션 정보 취득
         ******************************/
        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto)session.getAttribute("loginMember");
        if(mberInfoOutDto == null) {
            ResponseEntity.ok(ApiResponse.success(cnt));
        } else {
            /******************************
             * 읽지 않은 회원알림 건수 조회
             ******************************/
            mberNtcnService.getCntMberNtcnByMberNo(mberInfoOutDto.getMberNo(), "N");
        }
        return ResponseEntity.ok(ApiResponse.success(cnt));
    }

    /**
     * 
     * @param page
     * @param size
     * @param ntcnCtgryCode
     * @param session
     * @return
     */
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<MberNtcnBindingOutDto>> getListMberNtcnByMberNo(
        @RequestParam Integer page,
        @RequestParam Integer size,
        @RequestParam String ntcnCtgryCode,
        HttpSession session
    ) {
        Integer offset = (page - 1) * size;

        /******************************
         * 세션 정보 취득
         ******************************/
        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto) session.getAttribute("loginMember");
        if(mberInfoOutDto == null) {
            throw new CustomException(ErrorCode.SESSION_ERROR);
        }
        
        String gnrlMberNo = mberInfoOutDto.getMberNo(); //일반회원번호
        // String mberSbscrbTyCode = mberInfoOutDto.getMberSbscrbTyCode(); //회원가입유형코드
        // String mberNo = ""; //작가회원번호
        // if(ObjectUtils.nullSafeEquals(StcCommVari.MBER_SBSCRB_TY_ARTS, mberSbscrbTyCode)) {
        //     /******************************
        //      * 회원번호로 작가 정보가 있는지 확인
        //      ******************************/
        //     WritrInfoOutDto writrInfoOutDto = writrInfoService.getWritrInfoByGnrlMberNo(gnrlMberNo);
        //     if(writrInfoOutDto == null) {
        //         throw new CustomException(ErrorCode.WRITR_NOT_FOUND);
        //     }
        //     mberNo = writrInfoOutDto.getMberNo();
        // } else {
        //     mberNo = gnrlMberNo;
        // }

        /******************************
         * 알림 리스트 정보 조회
         ******************************/
        List<MberNtcnBindingDto> mberNtcnBindingDtoList = mberNtcnService.getListMberNtcnByMberNo(gnrlMberNo, ntcnCtgryCode, size, offset);

        MberNtcnBindingOutDto mberNtcnBindingOutDto = new MberNtcnBindingOutDto();
        if(mberNtcnBindingDtoList.size() > 0) {
            /**
             * 각 알림 상태에 따라 건수가 있는 경우와 없는 경우가 있기 때문에
             * 건수가 있는 알림 상태인 경우에만 리스트가 화면에 전달되도록 함.
             */
            if(mberNtcnBindingDtoList.get(0).getMberNtcnNo() != null) {
                List<MberNtcnBindingDto> list = new ArrayList<>();
                for(MberNtcnBindingDto mberNtcnBindingDto : mberNtcnBindingDtoList) {
                    if(Strings.CS.contains(mberNtcnBindingDto.getTrgetTyCode(), "ARTIST")) {
                        String trgetNo = mberNtcnBindingDto.getTrgetNo();
    
                        WritrInfoOutDto writrInfoOutDto = writrInfoService.getWritrInfoByMberNo(trgetNo, null);
                        String email = mberInfoService.getMberInfoByMberNo(gnrlMberNo).getEmail();
                        
                        FileDtlsOutDto reprsntPrdctFileDtlsOutDto = fileDtlsService.getListFileDtlsByFileNo(writrInfoOutDto.getReprsntPrdctFileNo()).get(0);
                        StringBuilder sb = new StringBuilder();
                        sb.append("/api/file/");
                        sb.append(reprsntPrdctFileDtlsOutDto.getFileNo());
                        sb.append("/");
                        sb.append(reprsntPrdctFileDtlsOutDto.getSn());
    
                        mberNtcnBindingDto.setFileUrl(sb.toString());
                        mberNtcnBindingDto.setEmail(email);
                    }
    
                    list.add(mberNtcnBindingDto);
                }

                mberNtcnBindingOutDto.setMberNtcnList(list);
            }

            mberNtcnBindingOutDto.setMberNtcnCntDto(MberNtcnCntDto
                .builder()
                    .ALL(mberNtcnBindingDtoList.get(0).getAllCnt())
                    .NOREAD(mberNtcnBindingDtoList.get(0).getNoread())
                    .ARTWORK(mberNtcnBindingDtoList.get(0).getArtwork())
                    .INQUIRY(mberNtcnBindingDtoList.get(0).getInquiry())
                    .EXHIBITION(mberNtcnBindingDtoList.get(0).getExhibition())
                    .ETC(mberNtcnBindingDtoList.get(0).getEtc())
                .build());
        }

        return ResponseEntity.ok(ApiResponse.success(mberNtcnBindingOutDto));
    }

    /**
     * 회원알림 읽기 처리
     * @param mberNtcnNo
     * @param session
     * @return
     */
    @GetMapping("/chgredng/{mberNtcnNo}")
    @Transactional
    public ResponseEntity<ApiResponse<Void>> changeMberNtcnByMberNtcnNo(@PathVariable String mberNtcnNo, HttpSession session) {
        /******************************
         * 세션 정보 취득
         ******************************/
        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto) session.getAttribute("loginMember");
        if(mberInfoOutDto == null) {
            throw new CustomException(ErrorCode.SESSION_ERROR);
        }

        mberNtcnService.changeMberNtcnByMberNtcnNo(mberNtcnNo, mberInfoOutDto.getMberNo(), "Y");

        return ResponseEntity.ok(ApiResponse.success("회원알림 읽기 처리 완료했습니다.", null));
    }
}

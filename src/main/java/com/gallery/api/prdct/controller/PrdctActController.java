package com.gallery.api.prdct.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gallery.api.common.ArtistService;
import com.gallery.api.file.dto.in.FileDtlsInDto;
import com.gallery.api.file.service.FileDtlsService;
import com.gallery.api.member.dto.out.MberInfoOutDto;
import com.gallery.api.prdct.dto.PrdctActEtcBindingInDto;
import com.gallery.api.prdct.dto.in.InsttAuspcInDto;
import com.gallery.api.prdct.dto.in.PrdctActInDto;
import com.gallery.api.prdct.dto.out.PrdctActOutDto;
import com.gallery.api.prdct.service.InsttAuspcService;
import com.gallery.api.prdct.service.PrdctActService;
import com.gallery.commn.ApiResponse;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.FileUploadType;
import com.gallery.commn.exception.CustomException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * 작품활동
 * PrdctActController
 */
@RestController 
@RequestMapping("/api/prdct")
@RequiredArgsConstructor
public class PrdctActController {
    /**
     * 작품활동서비스
     */
    private final PrdctActService prdctActService;

    private final ArtistService artistService;

    private final FileDtlsService fileDtlsService;

    private final InsttAuspcService insttAuspcService;

    /**
     * 작품활동 등록 및 기관주최 등록 및 활동유형 이미지 등록
     * @param prdctActEtcBindingInDto
     * @param reprsntPrdctFile
     * @param insttAuspcFiles
     * @param fileDtlsFiles
     * @param session
     * @return
     */
    @PostMapping(value = "/act/regist", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<ApiResponse<Void>> changePrdctAct(
        @RequestPart("prdctActEtcBindingInDto") PrdctActEtcBindingInDto prdctActEtcBindingInDto,
        @RequestPart("reprsntPrdctFile") MultipartFile reprsntPrdctFile,
        @RequestPart("insttAuspcFiles") List<MultipartFile> insttAuspcFiles,
        @RequestPart("fileDtlsFiles") List<MultipartFile> fileDtlsFiles,
        HttpSession session
    ) {

        if(prdctActEtcBindingInDto == null || prdctActEtcBindingInDto.getPrdctActInDto() == null || prdctActEtcBindingInDto.getInsttAuspcInDtoList() == null || prdctActEtcBindingInDto.getFileDtlsInDtoList() == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        if(reprsntPrdctFile == null || reprsntPrdctFile.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_NULL_ISEMPTY);
        }

        if(insttAuspcFiles == null || insttAuspcFiles.size() == 0) {
            throw new CustomException(ErrorCode.FILE_NULL_ISEMPTY);
        }

        if(fileDtlsFiles == null || fileDtlsFiles.size() == 0) {
            throw new CustomException(ErrorCode.FILE_NULL_ISEMPTY);
        }

        /******************************
         * 세션 정보 취득
         ******************************/
        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto) session.getAttribute("loginMember");
        if(mberInfoOutDto == null) {
            throw new CustomException(ErrorCode.SESSION_ERROR);
        }
        
        PrdctActInDto prdctActInDto = prdctActEtcBindingInDto.getPrdctActInDto();

        /******************************
         * 작가회원인 경우 작가회원번호 리턴, 그렇지 않으면 일반회원번호 리턴
         ******************************/
        String mberNo = artistService.getMberNo(mberInfoOutDto.getMberNo(), mberInfoOutDto.getMberSbscrbTyCode());
        if(ObjectUtils.nullSafeEquals(mberInfoOutDto.getMberNo(), mberNo)) {
            //회원번호가 같은 경우 작가가 아니므로 예외 처리함
            throw new CustomException(ErrorCode.WRITR_NOT_FOUND);
        }

        /******************************
         * 대표 작품 파일 확장자 및 컨텐트 유형 검증, 업로드할 위치 설정 및 위치 생성, 파일 업로드 처리
         ******************************/
        List<FileDtlsInDto> reprsntDtoList = new ArrayList<>();
        FileDtlsInDto reprsntDto = fileDtlsService.getFileDtls(reprsntPrdctFile, mberNo, FileUploadType.ARTWORK.name(), 1, null, null);
        reprsntDtoList.add(reprsntDto);
        String reprsntPrdctFileNo = fileDtlsService.createListFileDtls(reprsntDtoList);

        /******************************
         * 다음작품순번 조회
         ******************************/
        String prdctActNo = "";
        Integer prdctSn = 1;
        PrdctActOutDto prdctActOutDto = prdctActService.getNextPrdctSnByMberNo(mberNo);
        /**
         * 작품활동 등록 없는 경우
         */
        if(prdctActOutDto == null) {
            /******************************
             * 작품활동등록 및 작품활동번호 조회
             ******************************/
            prdctActNo = prdctActService.createPrdctAct(PrdctActInDto
                .builder()
                    .prdctSn(prdctSn)
                    .mberNo(mberNo)
                    .actTyCode(prdctActInDto.getActTyCode())
                    .actNm(prdctActInDto.getActNm())
                    .actBgnde(prdctActInDto.getActBgnde())
                    .actEndde(prdctActInDto.getActEndde())
                    .actPlace(prdctActInDto.getActPlace())
                    .reprsntPrdctFileNo(reprsntPrdctFileNo)
                    .actIntrcn(prdctActInDto.getActIntrcn())
                    .othbcAt(prdctActInDto.getOthbcAt())
                    .useAt("Y")
                    .creatMberNo(mberInfoOutDto.getMberNo())
                    .changeMberNo(mberInfoOutDto.getMberNo())
                .build());

            /**
             * 기관주최 등록 및 파일내역 등록
             */
            int index = 0;
            String insttAuspcNo = "";
            String reprsntFileNo = "";
            for(MultipartFile file: insttAuspcFiles) {
                InsttAuspcInDto insttAuspcInDto = prdctActEtcBindingInDto.getInsttAuspcInDtoList().get(index);
                insttAuspcInDto.setSn(index + 1);

                insttAuspcInDto.setPrdctActNo(prdctActNo);
                insttAuspcInDto.setPrdctSn(1);
                insttAuspcInDto.setMberNo(mberNo);

                insttAuspcInDto.setCreatMberNo(mberNo);
                insttAuspcInDto.setChangeMberNo(mberNo);

                List<FileDtlsInDto> insttAuspcList = new ArrayList<>();
                /**
                 * 파일 확장자 및 컨텐트 유형 검증, 업로드할 위치 설정 및 위치 생성, 파일 업로드 처리
                 */
                FileDtlsInDto insttAuspcFileDtlsInDto = fileDtlsService.getFileDtls(file, mberNo, FileUploadType.ARTWORK.name(), index + 1, null, null);
                insttAuspcList.add(insttAuspcFileDtlsInDto);

                if(index == 0) {
                    /******************************
                     * 파일 내역 등록 및 파일번호 조회
                     ******************************/
                    reprsntFileNo = fileDtlsService.createListFileDtls(insttAuspcList);
                } else {
                    /******************************
                     * 파일 내역 등록
                     ******************************/
                    fileDtlsService.createListNextFileDtls(reprsntFileNo, reprsntDtoList);
                }
                insttAuspcInDto.setReprsntFileNo(reprsntFileNo);

                /**
                 * 기관주최등록 및 기관주최번호 조회
                 */
                if(index == 0) {
                    /******************************
                     * 기관주최등록 및 기관주최번호 조회
                     ******************************/
                    insttAuspcNo = insttAuspcService.createInsttAuspc(insttAuspcInDto); //첫번재 기관주최번호 생성
                } else {
                    insttAuspcInDto.setInsttAuspcNo(insttAuspcNo);
                    /******************************
                     * 기관주최등록
                     ******************************/
                    insttAuspcService.createNextInsttAuspc(insttAuspcInDto);
                }

                index++;
            }

            /**
             * 활동 유형에 따른 파일내역 등록
             */
            index = 0;
            reprsntFileNo = "";
            for(MultipartFile fileDtlsFile: fileDtlsFiles) {
                FileDtlsInDto fileDtlsInDto = prdctActEtcBindingInDto.getFileDtlsInDtoList().get(index);

                List<FileDtlsInDto> fileDtlsList = new ArrayList<>();
                FileDtlsInDto inDto = fileDtlsService.getFileDtls(fileDtlsFile, mberNo, FileUploadType.ARTWORK.name(), index + 1, fileDtlsInDto.getImageTyCode(), fileDtlsInDto.getDc());
                fileDtlsList.add(inDto);

                if(index == 0) {
                    /******************************
                     * 파일 내역 등록 및 파일번호 조회
                     ******************************/
                    reprsntFileNo = fileDtlsService.createListFileDtls(fileDtlsList);
                } else {
                    /******************************
                     * 파일 내역 등록
                     ******************************/
                    fileDtlsService.createListNextFileDtls(reprsntFileNo, reprsntDtoList);
                }

                index++;
            }
        /**
         * 등록된 작품활동이 있는 경우
         */
        } else {
            prdctActService.createNextPrdctAct(PrdctActInDto
                .builder()
                    .prdctActNo(prdctActOutDto.getPrdctActNo())
                    .prdctSn(prdctActOutDto.getPrdctSn())
                    .mberNo(mberNo)
                    .actTyCode(prdctActInDto.getActTyCode())
                    .actNm(prdctActInDto.getActNm())
                    .actBgnde(prdctActInDto.getActBgnde())
                    .actEndde(prdctActInDto.getActEndde())
                    .actPlace(prdctActInDto.getActPlace())
                    .reprsntPrdctFileNo(reprsntPrdctFileNo)
                    .actIntrcn(prdctActInDto.getActIntrcn())
                    .othbcAt(prdctActInDto.getOthbcAt())
                    .useAt("Y")
                    .creatMberNo(mberInfoOutDto.getMberNo())
                    .changeMberNo(mberInfoOutDto.getMberNo())
                .build());
            
            prdctActNo = prdctActOutDto.getPrdctActNo();
            prdctSn = prdctActOutDto.getPrdctSn();

            /**
             * 기관주최 등록 및 파일내역 등록
             */
            int index = 0;
            String insttAuspcNo = "";
            String reprsntFileNo = "";
            for(MultipartFile file: insttAuspcFiles) {
                InsttAuspcInDto insttAuspcInDto = prdctActEtcBindingInDto.getInsttAuspcInDtoList().get(index);
                insttAuspcInDto.setSn(index + 1);

                insttAuspcInDto.setPrdctActNo(prdctActNo);
                insttAuspcInDto.setPrdctSn(prdctSn);
                insttAuspcInDto.setMberNo(mberNo);

                insttAuspcInDto.setCreatMberNo(mberNo);
                insttAuspcInDto.setChangeMberNo(mberNo);

                List<FileDtlsInDto> insttAuspcList = new ArrayList<>();
                /**
                 * 파일 확장자 및 컨텐트 유형 검증, 업로드할 위치 설정 및 위치 생성, 파일 업로드 처리
                 */
                FileDtlsInDto insttAuspcFileDtlsInDto = fileDtlsService.getFileDtls(file, mberNo, FileUploadType.ARTWORK.name(), index + 1, null, null);
                insttAuspcList.add(insttAuspcFileDtlsInDto);

                if(index == 0) {
                    /******************************
                     * 파일 내역 등록 및 파일번호 조회
                     ******************************/
                    reprsntFileNo = fileDtlsService.createListFileDtls(insttAuspcList);
                } else {
                    /******************************
                     * 파일 내역 등록
                     ******************************/
                    fileDtlsService.createListNextFileDtls(reprsntFileNo, reprsntDtoList);
                }
                insttAuspcInDto.setReprsntFileNo(reprsntFileNo);

                /**
                 * 기관주최등록 및 기관주최번호 조회
                 */
                if(index == 0) {
                    /******************************
                     * 기관주최등록 및 기관주최번호 조회
                     ******************************/
                    insttAuspcNo = insttAuspcService.createInsttAuspc(insttAuspcInDto); //첫번재 기관주최번호 생성
                } else {
                    insttAuspcInDto.setInsttAuspcNo(insttAuspcNo);
                    /******************************
                     * 기관주최등록
                     ******************************/
                    insttAuspcService.createNextInsttAuspc(insttAuspcInDto);
                }

                index++;
            }

            /******************************
             * 파일 내역 사용여부 'N' 변경
             ******************************/
            fileDtlsService.updateFileDtlsByMberNo(mberNo, mberNo);

            /**
             * 활동 유형에 따른 파일내역 등록
             */
            index = 0;
            reprsntFileNo = "";
            for(MultipartFile fileDtlsFile: fileDtlsFiles) {
                FileDtlsInDto fileDtlsInDto = prdctActEtcBindingInDto.getFileDtlsInDtoList().get(index);

                List<FileDtlsInDto> fileDtlsList = new ArrayList<>();
                FileDtlsInDto inDto = fileDtlsService.getFileDtls(fileDtlsFile, mberNo, FileUploadType.ARTWORK.name(), index + 1, fileDtlsInDto.getImageTyCode(), fileDtlsInDto.getDc());
                fileDtlsList.add(inDto);

                if(index == 0) {
                    /******************************
                     * 파일 내역 등록 및 파일번호 조회
                     ******************************/
                    reprsntFileNo = fileDtlsService.createListFileDtls(fileDtlsList);
                } else {
                    /******************************
                     * 파일 내역 등록
                     ******************************/
                    fileDtlsService.createListNextFileDtls(reprsntFileNo, reprsntDtoList);
                }

                index++;
            }
        }

        return ResponseEntity.ok(ApiResponse.success("작품활동 등록 정상 처리했습니다.", null));
    }
}

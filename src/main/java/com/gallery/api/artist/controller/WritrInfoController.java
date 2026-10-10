package com.gallery.api.artist.controller;

import com.gallery.api.member.service.MberInfoService;
import com.gallery.api.member.service.MberNtcnIemSetupService;
import com.gallery.api.member.service.MberNtcnSetupService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gallery.api.artist.dto.WritrInfoBindingDto;
import com.gallery.api.artist.dto.WritrInfoBindingOutDto;
import com.gallery.api.artist.dto.WritrJdgmnInfoBindingDto;
import com.gallery.api.artist.dto.WritrJdgmnInfoBindingOutDto;
import com.gallery.api.artist.dto.WritrJdgmnInfoCntDto;
import com.gallery.api.artist.dto.in.WritrCareerInDto;
import com.gallery.api.artist.dto.in.WritrInfoInDto;
import com.gallery.api.artist.dto.in.WritrJdgmnChangeHistInDto;
import com.gallery.api.artist.dto.in.WritrSttusChangeHistInDto;
import com.gallery.api.artist.dto.in.WritrUrlInDto;
import com.gallery.api.artist.dto.out.WritrCareerOutDto;
import com.gallery.api.artist.dto.out.WritrInfoOutDto;
import com.gallery.api.artist.dto.out.WritrUrlOutDto;
import com.gallery.api.artist.service.WritrCareerService;
import com.gallery.api.artist.service.WritrInfoService;
import com.gallery.api.artist.service.WritrJdgmnChangeHistService;
import com.gallery.api.artist.service.WritrSttusChangeHistService;
import com.gallery.api.artist.service.WritrUrlService;
import com.gallery.api.file.dto.in.FileDtlsInDto;
import com.gallery.api.file.dto.out.FileDtlsOutDto;
import com.gallery.api.file.service.FileDtlsService;
import com.gallery.api.jdgmn.dto.in.JdgmnCeckDetailInDto;
import com.gallery.api.jdgmn.dto.out.JdgmnCeckDetailOutDto;
import com.gallery.api.jdgmn.service.JdgmnCeckDetailService;
import com.gallery.api.member.dto.in.MberNtcnIemSetupInDto;
import com.gallery.api.member.dto.in.MberNtcnSetupInDto;
import com.gallery.api.member.dto.out.MberInfoOutDto;
import com.gallery.api.ntcn.dto.out.NtcnTyOutDto;
import com.gallery.api.ntcn.service.NtcnTyService;
import com.gallery.api.role.dto.in.MberRoleInDto;
import com.gallery.api.role.dto.in.RoleInfoInDto;
import com.gallery.api.role.dto.out.MberRoleOutDto;
import com.gallery.api.role.dto.out.RoleInfoOutDto;
import com.gallery.api.role.service.MberRoleService;
import com.gallery.api.role.service.RoleInfoService;
import com.gallery.commn.ApiResponse;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.FileUploadType;
import com.gallery.commn.StcCommVari;
import com.gallery.commn.exception.CustomException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
public class WritrInfoController {
    private final MberInfoService mberInfoService;

    private final WritrInfoService writrInfoService;

    private final WritrSttusChangeHistService writrSttusChangeHistService;

    private final WritrUrlService writrUrlService;

    private final WritrCareerService writrCareerService;

    private final FileDtlsService fileDtlsService;

    // private final MberInfoService mberInfoService;

    private final WritrJdgmnChangeHistService writrJdgmnChangeHistService;

    // private final MberNtcnService mberNtcnService;

    private final RoleInfoService roleInfoService;

    private final MberRoleService mberRoleService;

    private final NtcnTyService ntcnTyService;

    private final MberNtcnSetupService mberNtcnSetupService;

    private final MberNtcnIemSetupService mberNtcnIemSetupService;

    private final JdgmnCeckDetailService jdgmnCeckDetailService;

    private static Logger logger = LoggerFactory.getLogger(WritrInfoController.class);

    /**
     * 작가 등록 신청
     * 작가 등록 신청만 한 상태이기 때문에 승인이 나기 전까지는 작가가 아님.
     * @param writrInfoBindingDto
     * @param reprsntPrdctFile
     * @param writrFile
     * @param session
     * @return
     */
    @PostMapping(value = "/regist", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<ApiResponse<Void>> writrInfoRegist(
        @RequestPart("writrInfoBindingDto") WritrInfoBindingDto writrInfoBindingDto,
        @RequestPart("reprsntPrdctFile") MultipartFile reprsntPrdctFile,
        @RequestPart("writrFile") MultipartFile writrFile,
        HttpSession session) {
        
        if(reprsntPrdctFile == null || reprsntPrdctFile.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_NULL_ISEMPTY);
        }

        if(writrFile == null || writrFile.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_NULL_ISEMPTY);
        }

        if(writrInfoBindingDto == null || writrInfoBindingDto.getWritrCareerInDtoList() == null || writrInfoBindingDto.getWritrInfoInDto() == null ||  writrInfoBindingDto.getWritrUrlInDtoList() == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        WritrInfoInDto writrInfoInDto = writrInfoBindingDto.getWritrInfoInDto();
        List<WritrCareerInDto> writrCareerInDtoList = writrInfoBindingDto.getWritrCareerInDtoList();
        List<WritrUrlInDto> writrUrlInDtoList = writrInfoBindingDto.getWritrUrlInDtoList();

        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto)session.getAttribute("loginMember");
        String gnrlMberNo = mberInfoOutDto.getMberNo();

        /******************************
         * 회원번호로 작가 정보가 있는지 확인. 작가가 있으면 예외 처리
         ******************************/
        WritrInfoOutDto writrInfoOutDto = writrInfoService.getWritrInfoByGnrlMberNo(gnrlMberNo);

        if(writrInfoOutDto != null) {
            throw new CustomException(ErrorCode.WRITR_FOUND);
        }

        writrInfoInDto.setGnrlMberNo(gnrlMberNo);
        writrInfoInDto.setCreatMberNo(StcCommVari.MNGR_MBER_NO);
        writrInfoInDto.setChangeMberNo(StcCommVari.MNGR_MBER_NO);
        writrInfoInDto.setJdgmnSttusCode(StcCommVari.JDGMN_STTUS_PEND);
        writrInfoInDto.setMberSttusCode(StcCommVari.MBER_STTUS_ACTV);

        /******************************
         * 작가정보등록 및 작가회원번호 조회
         ******************************/
        String mberNo = writrInfoService.createWritrInfo(writrInfoInDto);

        /******************************
         * 작가상태변경이력 등록
         ******************************/
        writrSttusChangeHistService.createWritrSttusChangeHist(WritrSttusChangeHistInDto
            .builder()
                .mberNo(mberNo)
                .endDt(LocalDateTime.of(9999, 12, 31, 23, 59, 59))
                .writrSttusCode(StcCommVari.WRITR_STTUS_ACTV)
                .writrChangeResnCode(null)
                .resnDetail(null)
                .creatMberNo(StcCommVari.MNGR_MBER_NO)
                .changeMberNo(StcCommVari.MNGR_MBER_NO)
            .build());

        /******************************
         * 작가심사변경이력 등록
         ******************************/
        writrJdgmnChangeHistService.createWritrJdgmnChangeHist(WritrJdgmnChangeHistInDto
            .builder()
                .mberNo(mberNo)
                .endDt(LocalDateTime.of(9999, 12, 31, 23, 59, 59))
                .jdgmnSttusCode(StcCommVari.JDGMN_STTUS_PEND)
                .creatMberNo(StcCommVari.MNGR_MBER_NO)
                .changeMberNo(StcCommVari.MNGR_MBER_NO)
            .build()
        );
            
        /******************************
         * 작가 URL 등록 및 URL 번호 조회
        ******************************/
        writrUrlInDtoList.stream().forEach((dto) -> {
            dto.setMberNo(mberNo);
            dto.setChangeMberNo(StcCommVari.MNGR_MBER_NO);
            dto.setCreatMberNo(StcCommVari.MNGR_MBER_NO);
        });

        writrUrlService.creatListWritrUrl(writrUrlInDtoList);

        /******************************
         * 작가경력 등록 및 경력번호 조회
        ******************************/
        writrCareerInDtoList.stream().forEach((dto) -> {
            dto.setMberNo(mberNo);
            dto.setCreatMberNo(StcCommVari.MNGR_MBER_NO);
            dto.setChangeMberNo(StcCommVari.MNGR_MBER_NO);
        });

        writrCareerService.createWritrCarrer(writrCareerInDtoList);

        /******************************
         * 대표 작품 파일 확장자 및 컨텐트 유형 검증, 업로드할 위치 설정 및 위치 생성, 파일 업로드 처리
         ******************************/
        List<FileDtlsInDto> reprsntDtoList = new ArrayList<>();
        FileDtlsInDto reprsntDto = fileDtlsService.getFileDtls(reprsntPrdctFile, mberNo, FileUploadType.ARTWORK.name(), 1);
        reprsntDtoList.add(reprsntDto);
        String reprsntPrdctFileNo = fileDtlsService.createListFileDtls(reprsntDtoList);

        /******************************
         * 작가 프로필 파일 확장자 및 컨텐트 유형 검증, 업로드할 위치 설정 및 위치 생성, 파일 업로드 처리
         ******************************/
        List<FileDtlsInDto> writrDtoList = new ArrayList<>();
        FileDtlsInDto writrDto = fileDtlsService.getFileDtls(writrFile, mberNo, FileUploadType.ARTIST_PROFILE.name(), 1);
        writrDtoList.add(writrDto);
        String writrFileNo = fileDtlsService.createListFileDtls(writrDtoList);

        /******************************
         * 작가 파일 번호 및 대표 작품 파일 번호 변경
         ******************************/
        writrInfoService.chgWritrInfoByMberNo(WritrInfoInDto
            .builder()
                .mberNo(mberNo)
                .writrFileNo(writrFileNo)
                .reprsntPrdctFileNo(reprsntPrdctFileNo)
                .changeMberNo(StcCommVari.MNGR_MBER_NO)
            .build());

        /**
         * session 객체 작가로 교체
         */
        // mberInfoOutDto.setMberSbscrbTyCode(StcCommVari.MBER_SBSCRB_TY_ARTS);
        // session.setAttribute("loginMember", mberInfoOutDto);
        NtcnTyOutDto ntcnTyOutDto;
        String cn = "";
        String trgetNo = "";

        String[] list = {"APPLICANT", "ADMIN"};
        for(String str: list) {
            String ntcnTagetMberNo = "";
            if(ObjectUtils.nullSafeEquals(str, "APPLICANT")) {
                ntcnTagetMberNo = gnrlMberNo; //일반회원번호
                ntcnTyOutDto = ntcnTyService.getNtcnTyByNtcnTyCode(StcCommVari.NTCN_TY_WRITR_JDGMN_PEND, StcCommVari.NTCN_SETUP_WRITR_JDGMN); //알림유형 조회
                cn = ntcnTyOutDto.getCnTmpl().replace("{mberNm}", mberInfoService.getMberInfoByMberNo(gnrlMberNo).getNcnm()); //회원번호로 회원정보 조회 및 회원명 변경
                trgetNo = mberNo;
            } else {
                ntcnTagetMberNo = StcCommVari.JDGMN_ADMIN_MBER_NO; //심사관리자회원번호 (임시직 (SJH))
                ntcnTyOutDto = ntcnTyService.getNtcnTyByNtcnTyCode(StcCommVari.NTCN_TY_WRITR_JDGMN_ADMIN_REQ, StcCommVari.NTCN_SETUP_WRITR_JDGMN_ADMIN); //알림유형 조회
                cn = ntcnTyOutDto.getCnTmpl().replace("{mberNm}", mberInfoService.getMberInfoByMberNo(gnrlMberNo).getNcnm()); //회원번호로 회원정보 조회 및 회원명 변경
                trgetNo = mberNo;
            }
            writrInfoService.createMberNtcn(ntcnTagetMberNo, cn, trgetNo, mberNo, ntcnTyOutDto);
        }

        return ResponseEntity.ok(ApiResponse.success("작가 등록에 성공했습니다.", null));
    }

    /**
     * 작가정보, 경력정보, URL정보, 작가 프로필 파일 내역, 대표 작품 파일 내역 조회 및 리턴
     * @param session
     * @return
     */
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<WritrInfoBindingOutDto>> getWritrInfoByGnrlMberNo(HttpSession session) {
        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto) session.getAttribute("loginMember");
        String mberNo = mberInfoOutDto.getMberNo();
        
        /******************************
         * 회원번호로 작가 정보가 있는지 확인
         ******************************/
        WritrInfoOutDto writrInfoOutDto = writrInfoService.getWritrInfoByGnrlMberNo(mberNo);

        if(writrInfoOutDto == null) {
            return ResponseEntity.ok(ApiResponse.success(null));
        }

        String writrMberNo = writrInfoOutDto.getMberNo();
        String writrFileNo = writrInfoOutDto.getWritrFileNo();
        String reprsntPrdctFileNo = writrInfoOutDto.getReprsntPrdctFileNo();

        /******************************
         * 경력 정보 리스트 조회
         ******************************/
        List<WritrCareerOutDto> writrCareerOutDtoList = writrCareerService.getListWritrCareerByMberNo(writrMberNo);

        /******************************
         * 작가URL 정보 리스트 조회
         ******************************/
        List<WritrUrlOutDto> writrUrlOutDtoList = writrUrlService.getListWritrUrlByMberNo(writrMberNo);

        /******************************
         * 대표 작품 파일 내역 리스트 조회. 단, 대표 작품은 파일 한개만 반영하므로 단 건 조회함.
         ******************************/
        FileDtlsOutDto reprsntPrdctFileDtlsOutDto = fileDtlsService.getListFileDtlsByFileNo(reprsntPrdctFileNo).get(0);

        StringBuffer sb = new StringBuffer();
        sb.append("/api/file/");
        sb.append(reprsntPrdctFileDtlsOutDto.getFileNo());
        sb.append("/");
        sb.append(reprsntPrdctFileDtlsOutDto.getSn());

        reprsntPrdctFileDtlsOutDto.setFileUrl(sb.toString());

        /******************************
         * 작가 프로필 파일 내역 리스트 조회. 단, 작가 프로필 사진은 파일 한개만 반영하므로 단 건 조회함.
         ******************************/
        FileDtlsOutDto writrFileDtlsOutDto = fileDtlsService.getListFileDtlsByFileNo(writrFileNo).get(0);

        sb.setLength(0);
        sb.append("/api/file/");
        sb.append(writrFileDtlsOutDto.getFileNo());
        sb.append("/");
        sb.append(writrFileDtlsOutDto.getSn());

        writrFileDtlsOutDto.setFileUrl(sb.toString());

        return ResponseEntity.ok(ApiResponse.success(WritrInfoBindingOutDto
            .builder()
                .writrInfoOutDto(writrInfoOutDto)
                .writrCareerOutDtoList(writrCareerOutDtoList)
                .writrUrlOutDtoList(writrUrlOutDtoList)
                .writrFileDtlsOutDto(writrFileDtlsOutDto)
                .reprsntPrdctFileDtlsOutDto(reprsntPrdctFileDtlsOutDto)
            .build()));
    }

    /**
     * 작가 정보 변경
     * @param writrInfoBindingDto
     * @param reprsntPrdctFile
     * @param writrFile
     * @param session
     * @return
     */
    @PutMapping(value="/change", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<ApiResponse<Void>> writrInfoChange(
        @RequestPart("writrInfoBindingDto") WritrInfoBindingDto writrInfoBindingDto,
        @RequestPart(value = "reprsntPrdctFile", required = false) MultipartFile reprsntPrdctFile,
        @RequestPart(value = "writrFile", required = false) MultipartFile writrFile,
        HttpSession session) {

        if(writrInfoBindingDto == null || writrInfoBindingDto.getWritrCareerInDtoList() == null || writrInfoBindingDto.getWritrInfoInDto() == null ||  writrInfoBindingDto.getWritrUrlInDtoList() == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        WritrInfoInDto writrInfoInDto = writrInfoBindingDto.getWritrInfoInDto();
        List<WritrCareerInDto> writrCareerInDtoList = writrInfoBindingDto.getWritrCareerInDtoList();
        List<WritrUrlInDto> writrUrlInDtoList = writrInfoBindingDto.getWritrUrlInDtoList();
        String mberNo = writrInfoInDto.getMberNo();

        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto)session.getAttribute("loginMember");
        String gnrlMberNo = mberInfoOutDto.getMberNo();

        /**
         * 변경하고자 하는 작가의 일반회원번호와 로그인한 회원번호가 일치하는지 확인
         */
        if(!ObjectUtils.nullSafeEquals(writrInfoInDto.getGnrlMberNo(), gnrlMberNo)) {
            throw new CustomException(ErrorCode.MEMBER_WRITR_NO_CONFLICT);
        }

        writrInfoInDto.setChangeMberNo(StcCommVari.MNGR_MBER_NO);

        /******************************
         * 작가정보변경
         ******************************/
        writrInfoService.chgWritrBassInfoByMberNo(writrInfoInDto);

        /******************************
         * 작가 URL 삭제
         ******************************/
        writrUrlService.delWritrUrlByMberNo(mberNo);

        /******************************
         * 작가 URL 등록 및 URL 번호 조회
        ******************************/
        writrUrlInDtoList.stream().forEach((dto) -> {
            dto.setCreatMberNo(StcCommVari.MNGR_MBER_NO);
            dto.setChangeMberNo(StcCommVari.MNGR_MBER_NO);
        });

        writrUrlService.creatListWritrUrl(writrUrlInDtoList);

        /******************************
         * 작가경력 삭제
         ******************************/
        writrCareerService.delWritrCareerByMberNo(mberNo);

        /******************************
         * 작가경력 등록 및 경력번호 조회
        ******************************/
        writrCareerInDtoList.stream().forEach((dto) -> {
            dto.setCreatMberNo(StcCommVari.MNGR_MBER_NO);
            dto.setChangeMberNo(StcCommVari.MNGR_MBER_NO);
        });

        writrCareerService.createWritrCarrer(writrCareerInDtoList);

        String reprsntPrdctFileNo = "";
        if(reprsntPrdctFile != null) {
            /******************************
             * 대표 작품 파일 내역 리스트 조회. 단, 대표 작품은 파일 한개만 반영하므로 단 건 조회함.
             * 기존 파일 삭제 처리
             ******************************/
            FileDtlsOutDto reprsntPrdctFileDtlsOutDto = fileDtlsService.getListFileDtlsByFileNo(writrInfoInDto.getReprsntPrdctFileNo()).get(0);
            fileDtlsService.delFileDtls(reprsntPrdctFileDtlsOutDto);
    
            /******************************
             * 대표 작품 파일 확장자 및 컨텐트 유형 검증, 업로드할 위치 설정 및 위치 생성, 파일 업로드 처리
            ******************************/
            List<FileDtlsInDto> reprsntDtoList = new ArrayList<>();
            FileDtlsInDto reprsntDto = fileDtlsService.getFileDtls(reprsntPrdctFile, mberNo, FileUploadType.ARTWORK.name(), 1);
            reprsntDtoList.add(reprsntDto);
            reprsntPrdctFileNo = fileDtlsService.createListFileDtls(reprsntDtoList);
        }

        String writrFileNo = "";
        if(writrFile != null) {
            /******************************
            * 작가 프로필 파일 내역 리스트 조회. 단, 작가 프로필 사진은 파일 한개만 반영하므로 단 건 조회함.
            * 기존 파일 삭제 처리
            ******************************/
            FileDtlsOutDto writrFileDtlsOutDto = fileDtlsService.getListFileDtlsByFileNo(writrInfoInDto.getWritrFileNo()).get(0);
            fileDtlsService.delFileDtls(writrFileDtlsOutDto);
    
            /******************************
             * 작가 프로필 파일 확장자 및 컨텐트 유형 검증, 업로드할 위치 설정 및 위치 생성, 파일 업로드 처리
             ******************************/
            List<FileDtlsInDto> writrDtoList = new ArrayList<>();
            FileDtlsInDto writrDto = fileDtlsService.getFileDtls(writrFile, mberNo, FileUploadType.ARTIST_PROFILE.name(), 1);
            writrDtoList.add(writrDto);
            writrFileNo = fileDtlsService.createListFileDtls(writrDtoList);
        }
        
        /******************************
         * 작가 파일 번호 및 대표 작품 파일 번호 변경
         ******************************/
        writrInfoService.chgWritrInfoByMberNo(WritrInfoInDto
            .builder()
                .mberNo(mberNo)
                .writrFileNo(writrFileNo)
                .reprsntPrdctFileNo(reprsntPrdctFileNo)
                .changeMberNo(StcCommVari.MNGR_MBER_NO)
            .build());

        return ResponseEntity.ok(ApiResponse.success("작가 정보 변경에 성공했습니다.", null));
    }

    /**
     * 심사할 작가 정보 조회 및 심사상태별 건수 조회
     * @param nm
     * @param jdgmnSttusCode
     * @return
     */
    @GetMapping("/jdgmn")
    public ResponseEntity<ApiResponse<WritrJdgmnInfoBindingOutDto>> getListWritrJdgmnInfoByNm(
        @RequestParam Integer page,
        @RequestParam Integer size, 
        @RequestParam String nm,
        @RequestParam String jdgmnSttusCode) {

        Integer offset = (page - 1) * size;

        /******************************
         * 작가를 심사할 정보 조회 및 심사상태별 건수 조회
         ******************************/
        List<WritrJdgmnInfoBindingDto> writrJdgmnInfoBindingDtoList = writrInfoService.getListWritrJdgmnInfoByNm(size, offset, nm, StcCommVari.WRITR_STTUS_ACTV, jdgmnSttusCode);

        WritrJdgmnInfoBindingOutDto writrJdgmnInfoBindingOutDto = new WritrJdgmnInfoBindingOutDto();
        if(writrJdgmnInfoBindingDtoList.size() > 0) {
            /**
             * 각 심사 상태에 따라 건수가 있는 경우와 없는 경우가 있기 때문에
             * 건수가 있는 심사 상태인 경우에만 리스트가 화면에 전달되도록 함.
             */
            if(writrJdgmnInfoBindingDtoList.get(0).getMberNo() != null) {
                for(WritrJdgmnInfoBindingDto writrJdgmnInfoBindingDto: writrJdgmnInfoBindingDtoList) {
                    /**
                     * 작가 프로필 사진을 api를 통해 화면에 출력하기 위해 처리
                     */
                    StringBuffer sb = new StringBuffer();
                    sb.append("/api/file/");
                    sb.append(writrJdgmnInfoBindingDto.getWritrFileNo());
                    sb.append("/");
                    sb.append(writrJdgmnInfoBindingDto.getWritrSn());
                    writrJdgmnInfoBindingDto.setWritrFileUrl(sb.toString());

                    /**
                     * 대표 작품 사진을 api를 통해 화면에 출력하기 위해 처리
                     */
                    sb.setLength(0);
                    sb.append("/api/file/");
                    sb.append(writrJdgmnInfoBindingDto.getReprsntPrdctFileNo());
                    sb.append("/");
                    sb.append(writrJdgmnInfoBindingDto.getReprsntPrdctSn());
                    writrJdgmnInfoBindingDto.setReprsntPrdctFileUrl(sb.toString());

                }

                writrJdgmnInfoBindingOutDto.setWritrJdgmnInfoList(writrJdgmnInfoBindingDtoList);
            }
            writrJdgmnInfoBindingOutDto.setWritrJdgmnInfoCnt(WritrJdgmnInfoCntDto
                .builder()
                    .ALL(writrJdgmnInfoBindingDtoList.get(0).getAllCnt())
                    .PEND(writrJdgmnInfoBindingDtoList.get(0).getPend())
                    .REVW(writrJdgmnInfoBindingDtoList.get(0).getRevw())
                    .CMPL(writrJdgmnInfoBindingDtoList.get(0).getCmpl())
                    .REJT(writrJdgmnInfoBindingDtoList.get(0).getRejt())
                    .RCHG(writrJdgmnInfoBindingDtoList.get(0).getRchg())
                .build());
        }

        return ResponseEntity.ok(ApiResponse.success(writrJdgmnInfoBindingOutDto));
    }

    /**
     * 작가정보, 경력정보, URL정보, 작가 프로필 파일 내역, 대표 작품 파일 내역 조회 및 리턴
     * @param session
     * @return
     */
    @GetMapping("/jdgmn/detail")
    public ResponseEntity<ApiResponse<WritrInfoBindingOutDto>> getWritrInfoByGnrlMberNo(@RequestParam String mberNo) {
        /******************************
         * 작가 정보 조회
         ******************************/
        WritrInfoOutDto writrInfoOutDto = writrInfoService.getWritrInfoByMberNo(mberNo, StcCommVari.WRITR_STTUS_ACTV);

        String writrFileNo = writrInfoOutDto.getWritrFileNo();
        String reprsntPrdctFileNo = writrInfoOutDto.getReprsntPrdctFileNo();

        /******************************
         * 경력 정보 리스트 조회
         ******************************/
        List<WritrCareerOutDto> writrCareerOutDtoList = writrCareerService.getListWritrCareerByMberNo(mberNo);

        /******************************
         * 작가URL 정보 리스트 조회
         ******************************/
        List<WritrUrlOutDto> writrUrlOutDtoList = writrUrlService.getListWritrUrlByMberNo(mberNo);

        /******************************
         * 심사체크리스트 조회
         ******************************/
        List<JdgmnCeckDetailOutDto> jdgmnCeckDetailOutDtoList = jdgmnCeckDetailService.getListJdgmnCeckDetailByJdgmnNo(writrInfoOutDto.getJdgmnNo());

        /******************************
         * 대표 작품 파일 내역 리스트 조회. 단, 대표 작품은 파일 한개만 반영하므로 단 건 조회함.
         ******************************/
        FileDtlsOutDto reprsntPrdctFileDtlsOutDto = fileDtlsService.getListFileDtlsByFileNo(reprsntPrdctFileNo).get(0);

        StringBuffer sb = new StringBuffer();
        sb.append("/api/file/");
        sb.append(reprsntPrdctFileDtlsOutDto.getFileNo());
        sb.append("/");
        sb.append(reprsntPrdctFileDtlsOutDto.getSn());

        reprsntPrdctFileDtlsOutDto.setFileUrl(sb.toString());

        /******************************
         * 작가 프로필 파일 내역 리스트 조회. 단, 작가 프로필 사진은 파일 한개만 반영하므로 단 건 조회함.
         ******************************/
        FileDtlsOutDto writrFileDtlsOutDto = fileDtlsService.getListFileDtlsByFileNo(writrFileNo).get(0);

        sb.setLength(0);
        sb.append("/api/file/");
        sb.append(writrFileDtlsOutDto.getFileNo());
        sb.append("/");
        sb.append(writrFileDtlsOutDto.getSn());

        writrFileDtlsOutDto.setFileUrl(sb.toString());

        return ResponseEntity.ok(ApiResponse.success(WritrInfoBindingOutDto
            .builder()
                .writrInfoOutDto(writrInfoOutDto)
                .writrCareerOutDtoList(writrCareerOutDtoList)
                .writrUrlOutDtoList(writrUrlOutDtoList)
                .writrFileDtlsOutDto(writrFileDtlsOutDto)
                .reprsntPrdctFileDtlsOutDto(reprsntPrdctFileDtlsOutDto)
                .jdgmnCeckDetailOutDtoList(jdgmnCeckDetailOutDtoList)
            .build()));
    }

    /**
     * 작가 심사 변경 처리
     * @param changeJdgmnSttusCode
     * @param writrInfoInDto
     */
    @PostMapping("/jdgmn/change")
    @Transactional
    public ResponseEntity<ApiResponse<Void>> writrJdgmnChange(@RequestBody WritrInfoBindingDto writrInfoBindingDto, HttpSession session) {
        if(writrInfoBindingDto == null ||
           writrInfoBindingDto.getChangeJdgmnSttusCode() == null ||
           writrInfoBindingDto.getWritrInfoInDto() == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
        
        MberInfoOutDto mberInfoOutDto = (MberInfoOutDto)session.getAttribute("loginMember");
        String loginMberNo = mberInfoOutDto.getMberNo();

        String changeJdgmnSttusCode = writrInfoBindingDto.getChangeJdgmnSttusCode();
        WritrInfoInDto writrInfoInDto = writrInfoBindingDto.getWritrInfoInDto();
        writrInfoInDto.setChangeMberNo(loginMberNo);

        String jdgmnNo = writrInfoInDto.getJdgmnNo();

        if(ObjectUtils.nullSafeEquals(changeJdgmnSttusCode, StcCommVari.JDGMN_STTUS_CHNG)) {
            /******************************
             * 작가 심사 정보 변경
             * 임시 저장 일때에는 심사상태코드를 "PEND" 로 조회 및 반영하고, 작가심사변경이력은 반영하지 않음.
             ******************************/
            writrInfoInDto.setJdgmnSttusCode(StcCommVari.JDGMN_STTUS_PEND);
            writrInfoService.chgWritrJdgmnInfoByMberNo(StcCommVari.JDGMN_STTUS_PEND, writrInfoInDto);

            List<JdgmnCeckDetailInDto> jdgmnCeckDetailInDtoList = writrInfoBindingDto.getJdgmnCeckDetailInDtoList();
            for(JdgmnCeckDetailInDto jdgmnCeckDetailInDto: jdgmnCeckDetailInDtoList) {
                jdgmnCeckDetailInDto.setCreatMberNo(loginMberNo);
                jdgmnCeckDetailInDto.setChangeMberNo(loginMberNo);
            }

            writrInfoInDto.setWritrFileNo(null);
            writrInfoInDto.setReprsntPrdctFileNo(null);

            if(jdgmnNo != null && !ObjectUtils.nullSafeEquals("", jdgmnNo)) {
                /******************************
                * 심사체크상세 삭제
                ******************************/
                jdgmnCeckDetailService.delListJdgmnCeckDetailByJdgmnNo(jdgmnNo);
            }

            /******************************
            * 심사체크상세 등록 및 심사번호 조회
            ******************************/
           if(jdgmnCeckDetailInDtoList.size() > 0) {
               writrInfoInDto.setJdgmnNo(jdgmnCeckDetailService.createListJdgmnCeckDetail(jdgmnCeckDetailInDtoList));
           }

            /******************************
            * 심사번호 변경
            ******************************/
            writrInfoService.chgWritrInfoByMberNo(writrInfoInDto);

            return ResponseEntity.ok(ApiResponse.success("작가 심사 저장했습니다.", null));
        } else {
            /******************************
             * 작가 심사 정보 변경
             ******************************/
            writrInfoService.chgWritrJdgmnInfoByMberNo(changeJdgmnSttusCode, writrInfoInDto);

            /******************************
             * 작가심사변경이력 변경
             ******************************/
            writrJdgmnChangeHistService.chgWritrJdgmnChangeHist(WritrJdgmnChangeHistInDto
                .builder()
                    .mberNo(writrInfoInDto.getMberNo())
                    .jdgmnSttusCode(writrInfoInDto.getJdgmnSttusCode())
                    .changeMberNo(loginMberNo) //작사 심사한 회원번호
                .build()
            );
    
            /******************************
             * 작가심사변경이력 등록
             ******************************/
            writrJdgmnChangeHistService.createWritrJdgmnChangeHist(WritrJdgmnChangeHistInDto
                .builder()
                    .mberNo(writrInfoInDto.getMberNo()) //작가회원번호
                    .endDt(LocalDateTime.of(9999, 12, 31, 23, 59, 59))
                    .jdgmnSttusCode(changeJdgmnSttusCode)
                    .creatMberNo(loginMberNo)
                    .changeMberNo(loginMberNo)
                .build()
            );
        }

        /******************************
         * 알림유형 조회
         ******************************/
        NtcnTyOutDto ntcnTyOutDto;
        String ntcnTagetMberNo = "";
        String mberNo = writrInfoInDto.getGnrlMberNo(); //일반회원번호
        String cn = "";
        String trgetNo = writrInfoInDto.getMberNo();

        if(ObjectUtils.nullSafeEquals(changeJdgmnSttusCode, StcCommVari.JDGMN_STTUS_REVW)) /* 리뷰 */ {
            ntcnTagetMberNo = writrInfoInDto.getGnrlMberNo(); //일반회원번호
            ntcnTyOutDto = ntcnTyService.getNtcnTyByNtcnTyCode(StcCommVari.NTCN_TY_WRITR_JDGMN_REVW, StcCommVari.NTCN_SETUP_WRITR_JDGMN); //알림유형 조회
            cn = ntcnTyOutDto.getCnTmpl().replace("{mberNm}", mberInfoService.getMberInfoByMberNo(mberNo).getNcnm()); //회원번호로 회원정보 조회 및 회원명 변경
        } else if(ObjectUtils.nullSafeEquals(changeJdgmnSttusCode, StcCommVari.JDGMN_STTUS_CMPL)) /* 승인 완료 */ {
            ntcnTagetMberNo = writrInfoInDto.getGnrlMberNo(); //일반회원번호
            ntcnTyOutDto = ntcnTyService.getNtcnTyByNtcnTyCode(StcCommVari.NTCN_TY_WRITR_JDGMN_CMPL, StcCommVari.NTCN_SETUP_WRITR_JDGMN); //알림유형 조회
            cn = ntcnTyOutDto.getCnTmpl().replace("{mberNm}", mberInfoService.getMberInfoByMberNo(mberNo).getNcnm()); //회원번호로 회원정보 조회 및 회원명 변경

            List<JdgmnCeckDetailInDto> jdgmnCeckDetailInDtoList = writrInfoBindingDto.getJdgmnCeckDetailInDtoList();
            for(JdgmnCeckDetailInDto jdgmnCeckDetailInDto: jdgmnCeckDetailInDtoList) {
                jdgmnCeckDetailInDto.setCreatMberNo(loginMberNo);
                jdgmnCeckDetailInDto.setChangeMberNo(loginMberNo);
            }

            writrInfoInDto.setWritrFileNo(null);
            writrInfoInDto.setReprsntPrdctFileNo(null);

            if(jdgmnNo != null && !ObjectUtils.nullSafeEquals("", jdgmnNo)) {
                /******************************
                * 심사체크상세 삭제
                ******************************/
                jdgmnCeckDetailService.delListJdgmnCeckDetailByJdgmnNo(jdgmnNo);
            }

            /******************************
            * 심사체크상세 등록 및 심사번호 조회
            ******************************/
           if(jdgmnCeckDetailInDtoList.size() > 0) {
               writrInfoInDto.setJdgmnNo(jdgmnCeckDetailService.createListJdgmnCeckDetail(jdgmnCeckDetailInDtoList));
           }

            /******************************
            * 심사번호 변경
            ******************************/
            writrInfoService.chgWritrInfoByMberNo(writrInfoInDto);

            /******************************
            * 회원가입유형 변경 (작가 회원으로 승인이 난 경우 변경 처리함. )
            ******************************/
            mberInfoService.changeMberInfoSbscrbTyByMberNo(mberNo, loginMberNo, StcCommVari.MBER_SBSCRB_TY_ARTS);

            /******************************
             * 회원번호 조건으로 회원역할 조회
            ******************************/
            MberRoleOutDto mberRoleOutDto = mberRoleService.getMberRoleByMberNo(mberNo);

            /******************************
             * 회원역할 종료일자 만료 처리
            ******************************/
            mberRoleService.chgMberRoleByMberNoAndRoleId(MberRoleInDto
                .builder()
                    .changeMberNo(loginMberNo)
                    .mberNo(mberNo)
                    .roleId(mberRoleOutDto.getRoleId())
                .build());

            /******************************
             * 역할정보 조회
             ******************************/
            RoleInfoOutDto roleInfoOutDto = roleInfoService.getRoleInfo(RoleInfoInDto
                .builder()
                    .bassRoleCode(StcCommVari.MBER_SBSCRB_TY_ARTS)
                .build())
                ;

            if(roleInfoOutDto == null) {
                throw new CustomException(ErrorCode.ROLE_INFO_NOT_FOUND);
            }

            /******************************
            * 회원역할 등록 (작가 회원으로 승인이 난 경우 등록 처리함.)
            ******************************/
            mberRoleService.createMberRole(MberRoleInDto
                .builder()
                    .mberNo(trgetNo)
                    .roleId(roleInfoOutDto.getRoleId())
                    .alwncMberNo(loginMberNo)
                    .endDt(LocalDateTime.of(9999, 12, 31, 23, 59, 59))
                    .creatMberNo(loginMberNo)
                    .changeMberNo(loginMberNo)
                .build()
            );

            /******************************
             * 회원알림설정 등록
             ******************************/
            mberNtcnSetupService.createMberNtcnSetup(MberNtcnSetupInDto
                .builder()
                    .mberNo(trgetNo)
                    .allRecptnAt("Y")
                    .aplctnRecptnAt("Y")
                    .emailRecptnAt("Y")
                    .smsRecptnAt("N")
                    .ntcnTimeLmttAt("Y")
                    .ntcnBeginTime("090000")
                    .ntcnEndTime("220000")
                    .creatMberNo(loginMberNo)
                    .changeMberNo(loginMberNo)
                .build()
            );

            /******************************
             * 회원알림항목설정 등록
             ******************************/
            mberNtcnIemSetupService.createListMberNtcnIemSetup(MberNtcnIemSetupInDto
                .builder()
                    .mberNo(trgetNo)
                    .aplctnRecptnAt("Y")
                    .emailRecptnAt("Y")
                    .smsRecptnAt("N")
                    .creatMberNo(loginMberNo)
                    .changeMberNo(loginMberNo)
                .build()
                ,roleInfoOutDto.getRoleId()
            );
        } else if(ObjectUtils.nullSafeEquals(changeJdgmnSttusCode, StcCommVari.JDGMN_STTUS_REJT)) /* 승인 거절 */ {
            ntcnTagetMberNo = writrInfoInDto.getGnrlMberNo(); //일반회원번호
            ntcnTyOutDto = ntcnTyService.getNtcnTyByNtcnTyCode(StcCommVari.NTCN_TY_WRITR_JDGMN_REJT, StcCommVari.NTCN_SETUP_WRITR_JDGMN); //알림유형 조회
            cn = ntcnTyOutDto.getCnTmpl();

            List<JdgmnCeckDetailInDto> jdgmnCeckDetailInDtoList = writrInfoBindingDto.getJdgmnCeckDetailInDtoList();
            for(JdgmnCeckDetailInDto jdgmnCeckDetailInDto: jdgmnCeckDetailInDtoList) {
                jdgmnCeckDetailInDto.setCreatMberNo(loginMberNo);
                jdgmnCeckDetailInDto.setChangeMberNo(loginMberNo);
            }

            writrInfoInDto.setWritrFileNo(null);
            writrInfoInDto.setReprsntPrdctFileNo(null);

            if(jdgmnNo != null && !ObjectUtils.nullSafeEquals("", jdgmnNo)) {
                /******************************
                * 심사체크상세 삭제
                ******************************/
                jdgmnCeckDetailService.delListJdgmnCeckDetailByJdgmnNo(jdgmnNo);
            }

            /******************************
            * 심사체크상세 등록 및 심사번호 조회
            ******************************/
           if(jdgmnCeckDetailInDtoList.size() > 0) {
               writrInfoInDto.setJdgmnNo(jdgmnCeckDetailService.createListJdgmnCeckDetail(jdgmnCeckDetailInDtoList));
           }

            /******************************
            * 심사번호 변경
            ******************************/
            writrInfoService.chgWritrInfoByMberNo(writrInfoInDto);

            /******************************
             * 작가상태변경이력 등록
             ******************************/
            writrSttusChangeHistService.changeWritrSttusChangeHistByMberNo(trgetNo, loginMberNo);

            /******************************
             * 작가상태변경이력 등록
             ******************************/
            writrSttusChangeHistService.createWritrSttusChangeHist(WritrSttusChangeHistInDto
                .builder()
                    .mberNo(trgetNo)
                    .writrSttusCode(StcCommVari.WRITR_STTUS_REJT)
                    .writrChangeResnCode(null)
                    .resnDetail(null)
                    .creatMberNo(loginMberNo)
                    .changeMberNo(loginMberNo)
                .build());

        } else if(ObjectUtils.nullSafeEquals(changeJdgmnSttusCode, StcCommVari.JDGMN_STTUS_RCHG)) /* 수정 요청 */ {
            ntcnTagetMberNo = writrInfoInDto.getGnrlMberNo(); //일반회원번호
            ntcnTyOutDto = ntcnTyService.getNtcnTyByNtcnTyCode(StcCommVari.NTCN_TY_WRITR_JDGMN_RCHG, StcCommVari.NTCN_SETUP_WRITR_JDGMN); //알림유형 조회
            cn = ntcnTyOutDto.getCnTmpl();

            List<JdgmnCeckDetailInDto> jdgmnCeckDetailInDtoList = writrInfoBindingDto.getJdgmnCeckDetailInDtoList();
            for(JdgmnCeckDetailInDto jdgmnCeckDetailInDto: jdgmnCeckDetailInDtoList) {
                jdgmnCeckDetailInDto.setCreatMberNo(loginMberNo);
                jdgmnCeckDetailInDto.setChangeMberNo(loginMberNo);
            }

            writrInfoInDto.setWritrFileNo(null);
            writrInfoInDto.setReprsntPrdctFileNo(null);

            if(jdgmnNo != null && !ObjectUtils.nullSafeEquals("", jdgmnNo)) {
                /******************************
                * 심사체크상세 삭제
                ******************************/
                jdgmnCeckDetailService.delListJdgmnCeckDetailByJdgmnNo(jdgmnNo);
            }

            /******************************
            * 심사체크상세 등록 및 심사번호 조회
            ******************************/
           if(jdgmnCeckDetailInDtoList.size() > 0) {
               writrInfoInDto.setJdgmnNo(jdgmnCeckDetailService.createListJdgmnCeckDetail(jdgmnCeckDetailInDtoList));
           }

            /******************************
            * 심사번호 변경
            ******************************/
            writrInfoService.chgWritrInfoByMberNo(writrInfoInDto);

        } else if (ObjectUtils.nullSafeEquals(changeJdgmnSttusCode, StcCommVari.JDGMN_STTUS_RE_PEND)) /* 재접수 */ {
            String[] list = {"APPLICANT", "ADMIN"};
            for(String str: list) {
                if(ObjectUtils.nullSafeEquals(str, "APPLICANT")) {
                    ntcnTagetMberNo = writrInfoInDto.getGnrlMberNo(); //일반회원번호
                    ntcnTyOutDto = ntcnTyService.getNtcnTyByNtcnTyCode(StcCommVari.NTCN_TY_WRITR_JDGMN_RE_PEND, StcCommVari.NTCN_SETUP_WRITR_JDGMN); //알림유형 조회
                    cn = ntcnTyOutDto.getCnTmpl();
                } else {
                    ntcnTagetMberNo = loginMberNo; //심사관리자회원번호
                    ntcnTyOutDto = ntcnTyService.getNtcnTyByNtcnTyCode(StcCommVari.NTCN_TY_WRITR_JDGMN_ADMIN_RE_REQ, StcCommVari.NTCN_SETUP_WRITR_JDGMN_ADMIN); //알림유형 조회
                    cn = ntcnTyOutDto.getCnTmpl().replace("{mberNm}", mberInfoService.getMberInfoByMberNo(mberNo).getNcnm()); //회원번호로 회원정보 조회 및 회원명 변경
                }
                writrInfoService.createMberNtcn(ntcnTagetMberNo, cn, trgetNo, loginMberNo, ntcnTyOutDto);
            }

            return ResponseEntity.ok(ApiResponse.success("작가 심사 변경 완료했습니다.", null));
        } else {
            throw new CustomException(ErrorCode.WRITR_JDGMN_STTUS_NO);
        }

        /******************************
         * 회원알림 등록
         ******************************/
        writrInfoService.createMberNtcn(ntcnTagetMberNo, cn, trgetNo, loginMberNo, ntcnTyOutDto);

        return ResponseEntity.ok(ApiResponse.success("작가 심사 변경 완료했습니다.", null));
    }
}

package com.gallery.api.file.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gallery.api.file.dto.FileDtlsBindingDto;
import com.gallery.api.file.dto.in.FileDtlsInDto;
import com.gallery.api.file.dto.out.FileDtlsOutDto;
import com.gallery.api.file.entity.FileDtlsEntity;
import com.gallery.api.file.mapper.FileDtlsMapper;
import com.gallery.commn.ErrorCode;
import com.gallery.commn.StcCommVari;
import com.gallery.commn.config.FileUploadProperties;
import com.gallery.commn.exception.CustomException;
import com.gallery.commn.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileDtlsService {
    private final FileDtlsMapper fileDtlsMapper;

    private final FileService fileService;

    private final FileUploadProperties fileUploadProperties;

    private static Logger logger = LoggerFactory.getLogger(FileDtlsService.class);

    /**
     * 파일 내역 등록 및 파일번호 조회
     * @param FileDtlsInDtoList
     * @return
     */
    public String createListFileDtls(List<FileDtlsInDto> FileDtlsInDtoList) {
        FileDtlsBindingDto fileDtlsBindingDto = FileDtlsBindingDto
            .builder()
                .fileDtlsList(FileDtlsInDtoList)
            .build();

        fileDtlsMapper.insertListFileDtls(fileDtlsBindingDto);

        return fileDtlsBindingDto.getFileNo();
    }

    /**
     * 파일 내역 등록
     * @param fileNo
     * @param FileDtlsInDtoList
     */
    public void createListNextFileDtls(String fileNo, List<FileDtlsInDto> FileDtlsInDtoList) {
        fileDtlsMapper.insertListNextFileDtls(FileDtlsBindingDto
            .builder()
                .fileNo(fileNo)
                .fileDtlsList(FileDtlsInDtoList)
            .build());
    }

    /**
     * 파일 확장자 및 컨텐트 유형 검증, 업로드할 위치 설정 및 위치 생성, 파일 업로드 처리
     * @param multipartFile
     * @param mberNo
     * @param fileUpLoadPath
     * @return
     */
    public FileDtlsInDto getFileDtls(MultipartFile multipartFile, String mberNo, String fileUpLoadPath, Integer sn, String imageTyCode, String dc) {
        /******************************
         * 파일 확장자 및 컨텐트 유형 검증
         ******************************/
        fileService.vrifyCntntsTyExtsn(multipartFile, StcCommVari.GROUP_CD_IMAGE_EXT);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StringBuffer sb = new StringBuffer();
        sb.append(fileUpLoadPath);
        sb.append(File.separator);
        sb.append(today);

        String fileLc = sb.toString();

        /******************************
         * 업로드할 위치 설정 및 위치 생성
         ******************************/
        Path path = Path.of(fileUploadProperties.getRootPath()
                    , fileUpLoadPath
                    , today);

        if(!Files.exists(path)) {
            try {
                /******************************
                 * 디렉토리 생성
                 ******************************/
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new CustomException(ErrorCode.DIRECTORY_CREAT_ERROR);
            }
        }

        /**
         * 컨텐트 유형
         */
        String fileContentType = multipartFile.getContentType();

        /**
         * 원본 파일명
         */
        String fileName = multipartFile.getOriginalFilename();

        /**
         * 원본 파일 확장자
         */
        String fileExtsn = fileName.substring(fileName.lastIndexOf(".") + 1);

        /**
         * UUID
         */
        sb = new StringBuffer();
        sb.append(UUID.randomUUID().toString());
        sb.append(".");
        sb.append(fileExtsn);

        /**
         * UUID 파일명
         */
        String uUIDFileName = sb.toString();

        Path filePath = path.resolve(uUIDFileName);
        try {
            /******************************
             * 파일 복사
             ******************************/
            multipartFile.transferTo(filePath);
        } catch (IOException e) {
            //이전에 업로드한 파일 삭제 처리 추가 필요(SJH)
            //Files.deleteIfExists(filePath);
            throw new CustomException(ErrorCode.FILE_COPY_ERROR);
        }

        return FileDtlsInDto
                .builder()
                    .sn(sn)
                    .mberNo(mberNo)
                    .fileLc(fileLc) //파일 위치
                    .orginlFileNm(fileName.substring(0, fileName.lastIndexOf("."))) //원본 파일명
                    .streFileNm(uUIDFileName.substring(0, uUIDFileName.lastIndexOf("."))) //저장 파일명
                    .fileExtsn(fileExtsn) //확장자
                    .cntntsTyCode(fileContentType) //컨텐츠유형코드
                    .imageTyCode(imageTyCode)
                    .dc(dc)
                    .useAt("Y")
                    .creatMberNo(StcCommVari.MNGR_MBER_NO)
                    .changeMberNo(StcCommVari.MNGR_MBER_NO)
                .build();
    }

    /**
     * 파일 내역 리스트 조회
     * @param fileNo
     * @return
     */
    public List<FileDtlsOutDto> getListFileDtlsByFileNo(String fileNo) {
        // logger.info("getListFileDtlsByFileNo: " + fileNo);
        List<FileDtlsEntity> fileDtlsEntityList = fileDtlsMapper.selectListFileDtlsByFileNo(fileNo);
        if(fileDtlsEntityList.size() == 0) {
            throw new CustomException(ErrorCode.FILE_DTLS_NOT_FOUND);
        }

        List<FileDtlsOutDto> list = new ArrayList<>();
        for(FileDtlsEntity fileDtlsEntity: fileDtlsEntityList) {
            list.add(FileDtlsOutDto
                .builder()
                    .fileNo(fileDtlsEntity.getFileNo())
                    .sn(fileDtlsEntity.getSn())
                    .mberNo(fileDtlsEntity.getMberNo())
                    .fileLc(fileDtlsEntity.getFileLc())
                    .orginlFileNm(fileDtlsEntity.getOrginlFileNm())
                    .streFileNm(fileDtlsEntity.getStreFileNm())
                    .fileExtsn(fileDtlsEntity.getFileExtsn())
                    .cntntsTyCode(fileDtlsEntity.getCntntsTyCode())
                    .imageTyCode(fileDtlsEntity.getImageTyCode())
                    .dc(fileDtlsEntity.getDc())
                    .useAt(fileDtlsEntity.getUseAt())
                    .creatMberNo(fileDtlsEntity.getCreatMberNo())
                    .creatDt(fileDtlsEntity.getCreatDt())
                    .changeMberNo(fileDtlsEntity.getChangeMberNo())
                    .changeDt(fileDtlsEntity.getChangeDt())
                .build());
        }

        return list;
    }

    /**
     * 파일번호와 순번으로 파일 내역 조회
     * @param fileNo
     * @param sn
     * @return
     */
    public FileDtlsOutDto getFileDtlsByFileNoAndSn(String fileNo, Integer sn) {
        // logger.info("getFileDtlsByFileNoAndSn: " + fileNo + ", " + sn );
        FileDtlsEntity fileDtlsEntity = fileDtlsMapper.selectFileDtlsByFileNoAndSn(fileNo, sn);
        if(fileDtlsEntity == null) {
            throw new CustomException(ErrorCode.FILE_DTLS_NOT_FOUND);
        }

        return FileDtlsOutDto
                .builder()
                    .fileNo(fileDtlsEntity.getFileNo())
                    .sn(fileDtlsEntity.getSn())
                    .mberNo(fileDtlsEntity.getMberNo())
                    .fileLc(fileDtlsEntity.getFileLc())
                    .orginlFileNm(fileDtlsEntity.getOrginlFileNm())
                    .streFileNm(fileDtlsEntity.getStreFileNm())
                    .fileExtsn(fileDtlsEntity.getFileExtsn())
                    .cntntsTyCode(fileDtlsEntity.getCntntsTyCode())
                    .imageTyCode(fileDtlsEntity.getImageTyCode())
                    .dc(fileDtlsEntity.getDc())
                    .useAt(fileDtlsEntity.getUseAt())
                    .creatMberNo(fileDtlsEntity.getCreatMberNo())
                    .creatDt(fileDtlsEntity.getCreatDt())
                    .changeMberNo(fileDtlsEntity.getChangeMberNo())
                    .changeDt(fileDtlsEntity.getChangeDt())
                .build();
    }

    /**
     * 기존 파일 삭제 처리
     * @param fileDtlsOutDto
     */
    public void delFileDtls(FileDtlsOutDto fileDtlsOutDto) {
        StringBuffer sb = new StringBuffer();
        sb.append(fileDtlsOutDto.getFileLc());
        sb.append(File.separator);
        sb.append(fileDtlsOutDto.getStreFileNm());
        sb.append(".");
        sb.append(fileDtlsOutDto.getFileExtsn());

        /******************************
         * 업로드할 위치 설정 및 위치 생성
         ******************************/
        Path path = Path.of(fileUploadProperties.getRootPath()
                    , sb.toString());
        try {
            /******************************
             * 파일 삭제
             ******************************/
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_COPY_ERROR);
        }
    }

    /**
     * 사용여부 'N' 변경
     * @param changeMberNo
     * @param mberNo
     */
    public void updateFileDtlsByMberNo(String changeMberNo, String mberNo) {
        fileDtlsMapper.updateFileDtlsByMberNo(changeMberNo, mberNo);
    }
}


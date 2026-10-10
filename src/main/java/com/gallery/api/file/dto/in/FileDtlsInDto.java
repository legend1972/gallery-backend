package com.gallery.api.file.dto.in;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDtlsInDto {
    private String fileNo;
    private Integer sn;
    private String mberNo;
    private String fileLc;
    private String orginlFileNm;
    private String streFileNm;
    private String fileExtsn;
    private String cntntsTyCode;
    private String imageTyCode;
    private String dc;
    private String useAt;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

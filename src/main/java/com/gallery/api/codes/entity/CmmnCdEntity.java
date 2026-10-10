package com.gallery.api.codes.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmmnCdEntity {
    private String groupCode;
    private String code;
    private String codeNm;
    private String sn;
    private String rm;
    private String refer1;
    private String refer2;
    private String useAt;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

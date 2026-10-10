package com.gallery.api.jdgmn.dto.out;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JdgmnCeckDetailOutDto {
    private String jdgmnNo;
    private String jdgmnCeckCode;
    private String mberNo;
    private String ceckAt;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

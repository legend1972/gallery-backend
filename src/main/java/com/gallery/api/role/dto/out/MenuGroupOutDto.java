package com.gallery.api.role.dto.out;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuGroupOutDto {
    private String groupId;
    private String groupNm;
    private String groupDc;
    private Integer sn;
    private String useAt;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

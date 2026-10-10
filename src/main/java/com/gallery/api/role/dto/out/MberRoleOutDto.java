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
public class MberRoleOutDto {
    private String mberNo;
    private String roleId;
    private LocalDateTime alwncDt;
    private String alwncMberNo;
    private LocalDateTime endDt;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

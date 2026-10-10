package com.gallery.api.member.dto.out;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MberSttusChangeHistOutDto {
    private String mberNo;
    private LocalDateTime beginDt;
    private LocalDateTime endDt;
    private String mberSttusCode;
    private String mberChangeResnCode;
    private String resnDetail;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

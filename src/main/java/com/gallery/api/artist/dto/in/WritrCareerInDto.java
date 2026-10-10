package com.gallery.api.artist.dto.in;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritrCareerInDto {
    private String careerNo;
    private Integer sn;
    private String mberNo;
    private String careerYear;
    private String careerCn;
    private String careerSeCode;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

package com.gallery.api.artist.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritrCareerEntity {
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

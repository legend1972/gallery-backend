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
public class WritrSttusChangeHistInDto {
    private String mberNo;
    private LocalDateTime beginDt;
    private LocalDateTime endDt;
    private String writrSttusCode;
    private String writrChangeResnCode;
    private String resnDetail;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

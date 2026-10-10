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
public class WritrJdgmnChangeHistEntity {
    private String mberNo;
    private LocalDateTime beginDt;
    private LocalDateTime endDt;
    private String jdgmnSttusCode;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

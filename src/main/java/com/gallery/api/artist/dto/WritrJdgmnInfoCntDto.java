package com.gallery.api.artist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritrJdgmnInfoCntDto {
    private Integer ALL;
    private Integer PEND;
    private Integer REVW;
    private Integer CMPL;
    private Integer REJT;
    private Integer RCHG;
}

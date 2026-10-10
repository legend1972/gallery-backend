package com.gallery.api.artist.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritrJdgmnInfoBindingOutDto {
    private List<WritrJdgmnInfoBindingDto> writrJdgmnInfoList;
    private WritrJdgmnInfoCntDto writrJdgmnInfoCnt;
}

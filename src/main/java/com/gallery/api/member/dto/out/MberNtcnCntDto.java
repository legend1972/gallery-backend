package com.gallery.api.member.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MberNtcnCntDto {
    private Integer ALL;
    private Integer NOREAD;
    private Integer ARTWORK;
    private Integer INQUIRY;
    private Integer EXHIBITION;
    private Integer ETC;
}

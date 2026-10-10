package com.gallery.api.codes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CmmnGroupCdDto {
    private String groupCode;
    private String groupNm;
    private String columnId;
    private String rm;
}

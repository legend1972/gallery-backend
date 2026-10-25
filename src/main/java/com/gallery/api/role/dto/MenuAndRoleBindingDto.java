package com.gallery.api.role.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuAndRoleBindingDto {
    private String menuId;
    private String menuNm;
    private String menuDc;
    private String menuUrl;
    private String iconNm;
    private String iconColor;
    private String inqirePosblAt;
    private String registPosblAt;
    private String changePosblAt;
    private String deletePosblAt;
}

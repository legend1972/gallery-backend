package com.gallery.api.role.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MberRoleBindingOutDto {
    private String groupId;
    private String groupNm;
    private String groupDc;

    @Builder.Default
    private List<MenuAndRoleBindingDto> list = new ArrayList<>();
}

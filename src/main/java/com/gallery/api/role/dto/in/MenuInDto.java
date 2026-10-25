package com.gallery.api.role.dto.in;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuInDto {
    private String menuId;
    private Integer menuOrdr;
    private String parntsMenuId;
    private String menuNm;
    private String menuUrl;
    private String upendUseAt;
    private String lptUseAt;
    private String lptMenuAt;
    private Integer lptMenuSn; 
    private String lptIconNm;
    private String iconNm;
    private String iconColor;
    private String loginNeedAt;
    private String useAt;
    private String creatMberNo;
    private LocalDateTime creatDt;
    private String changeMberNo;
    private LocalDateTime changeDt;
}

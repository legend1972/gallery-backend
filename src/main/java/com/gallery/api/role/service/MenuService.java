package com.gallery.api.role.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gallery.api.role.dto.out.MenuOutDto;
import com.gallery.api.role.entity.MenuEntity;
import com.gallery.api.role.mapper.MenuMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuMapper menuMapper;

    /**
     * 메뉴 조회
     * @param mberNo
     * @param bassRoleCode
     * @return
     */
    public List<MenuOutDto> getLstMenuByMNoAndRCode(String mberNo, String bassRoleCode) {
        List<MenuOutDto> menuOutDtoLst = new ArrayList<>();
        
        List<MenuEntity> menuEntityLst = menuMapper.selectLstMenuByMNoAndRCode(mberNo, bassRoleCode);
        for(MenuEntity menuEntity : menuEntityLst) {
            menuOutDtoLst.add(MenuOutDto
                .builder()
                    .menuId(menuEntity.getMenuId())
                    .menuOrdr(menuEntity.getMenuOrdr())
                    .parntsMenuId(menuEntity.getParntsMenuId())
                    .menuNm(menuEntity.getMenuNm())
                    .menuUrl(menuEntity.getMenuUrl())
                    .upendUseAt(menuEntity.getUpendUseAt())
                    .lptUseAt(menuEntity.getLptUseAt())
                    .lptMenuAt(menuEntity.getLptMenuAt())
                    .lptMenuSn(menuEntity.getLptMenuSn())
                    .lptIcon(menuEntity.getLptIcon())
                    .useAt(menuEntity.getUseAt())
                    .creatMberNo(menuEntity.getCreatMberNo())
                    .creatDt(menuEntity.getCreatDt())
                    .changeMberNo(menuEntity.getChangeMberNo())
                    .changeDt(menuEntity.getChangeDt())
                .build());
        }

        return menuOutDtoLst;
    }
}

package com.gallery.api.prdct.dto;

import java.io.Serializable;
import java.util.List;

import com.gallery.api.file.dto.in.FileDtlsInDto;
import com.gallery.api.prdct.dto.in.InsttAuspcInDto;
import com.gallery.api.prdct.dto.in.PrdctActInDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrdctActEtcBindingInDto implements Serializable {
    private PrdctActInDto prdctActInDto;
    private List<InsttAuspcInDto> insttAuspcInDtoList;
    private List<FileDtlsInDto> fileDtlsInDtoList;

}

package com.gallery.api.file.dto;

import java.util.List;

import com.gallery.api.file.dto.in.FileDtlsInDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDtlsBindingDto {
    private String fileNo;
    private List<FileDtlsInDto> fileDtlsList;
}

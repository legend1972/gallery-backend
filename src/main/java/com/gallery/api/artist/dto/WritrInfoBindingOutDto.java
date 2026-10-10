package com.gallery.api.artist.dto;

import java.io.Serializable;
import java.util.List;

import com.gallery.api.artist.dto.out.WritrCareerOutDto;
import com.gallery.api.artist.dto.out.WritrInfoOutDto;
import com.gallery.api.artist.dto.out.WritrUrlOutDto;
import com.gallery.api.file.dto.out.FileDtlsOutDto;
import com.gallery.api.jdgmn.dto.out.JdgmnCeckDetailOutDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritrInfoBindingOutDto implements Serializable {
    private WritrInfoOutDto writrInfoOutDto;
    private List<WritrCareerOutDto> writrCareerOutDtoList;
    private List<WritrUrlOutDto> writrUrlOutDtoList;
    private FileDtlsOutDto writrFileDtlsOutDto;
    private FileDtlsOutDto reprsntPrdctFileDtlsOutDto;
    private List<JdgmnCeckDetailOutDto> jdgmnCeckDetailOutDtoList;
}

package com.gallery.api.artist.dto;

import java.io.Serializable;
import java.util.List;

import com.gallery.api.artist.dto.in.WritrCareerInDto;
import com.gallery.api.artist.dto.in.WritrInfoInDto;
import com.gallery.api.artist.dto.in.WritrUrlInDto;
import com.gallery.api.jdgmn.dto.in.JdgmnCeckDetailInDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritrInfoBindingDto implements Serializable{
    private String changeJdgmnSttusCode;
    private WritrInfoInDto writrInfoInDto;
    private List<WritrCareerInDto> writrCareerInDtoList;
    private List<WritrUrlInDto> writrUrlInDtoList;
    private List<JdgmnCeckDetailInDto> jdgmnCeckDetailInDtoList;
}

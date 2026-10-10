package com.gallery.api.artist.dto;

import java.util.List;

import com.gallery.api.artist.dto.in.WritrUrlInDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritrUrlBindingDto {
    private String urlNo;
    private List<WritrUrlInDto> urlList;
}

package com.gallery.api.artist.dto;

import java.util.List;

import com.gallery.api.artist.dto.in.WritrCareerInDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WritrCareerBindingDto {
    private String careerNo;
    private List<WritrCareerInDto> careerList;
}

package com.xfrenzy47x.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRecipeRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    @NotEmpty
    @Size(min = 1)
    private String[] ingredients;

    @NotEmpty
    @Size(min = 1)
    private String[] directions;

}


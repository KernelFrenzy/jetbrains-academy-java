package com.xfrenzy47x.mapper;

import com.xfrenzy47x.dto.AddRecipeRequestDto;
import com.xfrenzy47x.model.Recipe;

import java.time.LocalDateTime;

public class RecipeMapper {

    private RecipeMapper() { throw new IllegalStateException("Utility class");}
    public static Recipe convert(AddRecipeRequestDto addRecipeRequestDto, Recipe recipe) {
        if (recipe == null) {
            recipe = new Recipe();
        }

        recipe.setName(addRecipeRequestDto.getName());
        recipe.setDescription(addRecipeRequestDto.getDescription());
        recipe.setIngredients(addRecipeRequestDto.getIngredients());
        recipe.setDirections(addRecipeRequestDto.getDirections());
        recipe.setCategory(addRecipeRequestDto.getCategory());
        recipe.setDate(LocalDateTime.now());

        return recipe;
    }
}

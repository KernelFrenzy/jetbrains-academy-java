package com.xfrenzy47x.service;

import com.xfrenzy47x.dto.AddRecipeRequestDto;
import com.xfrenzy47x.exception.ForbiddenException;
import com.xfrenzy47x.exception.NotFoundException;
import com.xfrenzy47x.mapper.RecipeMapper;
import com.xfrenzy47x.model.Recipe;
import com.xfrenzy47x.model.User;
import com.xfrenzy47x.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserService userService;

    public Long addRecipe(AddRecipeRequestDto addRecipeRequestDto, String username) {
        Recipe recipe = RecipeMapper.convert(addRecipeRequestDto, null);
        recipe.setUser(getUser(username));
        recipe = recipeRepository.save(recipe);
        return recipe.getId();
    }

    public Recipe getRecipe(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public void deleteRecipe(Long id, String username) throws ForbiddenException, NotFoundException {
        Recipe recipe = getRecipe(id);
        if (recipe == null) throw new NotFoundException();
        if (!recipe.getUser().getUsername().equals(username)) throw new ForbiddenException();
        recipeRepository.delete(recipe);
    }

    public void updateRecipe(Long id, AddRecipeRequestDto addRecipeRequestDto, String username) throws ForbiddenException, NotFoundException {
        Recipe recipe = getRecipe(id);
        if (recipe == null) throw new NotFoundException();
        if (!recipe.getUser().getUsername().equals(username)) throw new ForbiddenException();

        recipe = RecipeMapper.convert(addRecipeRequestDto, recipe);
        recipe.setId(id);

        recipeRepository.save(recipe);
    }

    public List<Recipe> searchRecipes(String category, String name) {
        if (!category.isEmpty()) {
            return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
        } else {
            return recipeRepository.findByNameIgnoreCaseContainingOrderByDateDesc(name);
        }
    }

    private User getUser(String username) {
        return userService.findByUsername(username);
    }

}


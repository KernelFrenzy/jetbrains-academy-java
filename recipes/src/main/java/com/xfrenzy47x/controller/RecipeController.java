package com.xfrenzy47x.controller;

import com.xfrenzy47x.dto.AddRecipeRequestDto;
import com.xfrenzy47x.dto.AddRecipeResponseDto;
import com.xfrenzy47x.exception.ForbiddenException;
import com.xfrenzy47x.exception.NotFoundException;
import com.xfrenzy47x.model.Recipe;
import com.xfrenzy47x.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @PostMapping("/api/recipe/new")
    public ResponseEntity<Object> saveRecipe(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody AddRecipeRequestDto recipeRequestDto) {
        if (details == null) {
            return ResponseEntity.status(401).build();
        }
        Long id = recipeService.addRecipe(recipeRequestDto, details.getUsername());
        return ResponseEntity.ok(new AddRecipeResponseDto(id));
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<Object> getRecipe(@AuthenticationPrincipal UserDetails details, @PathVariable(required = false) Long id) {
        if (details == null) {
            return ResponseEntity.status(401).build();
        }
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(recipe);
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<Object> deleteRecipe(@AuthenticationPrincipal UserDetails details, @PathVariable(required = false) Long id) {
        if (details == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            recipeService.deleteRecipe(id, details.getUsername());
            return ResponseEntity.status(204).build();
        } catch (ForbiddenException forbiddenException) {
            return ResponseEntity.status(403).build();
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<Object> updateRecipe(@AuthenticationPrincipal UserDetails details, @PathVariable(required = false) Long id, @Valid @RequestBody AddRecipeRequestDto addRecipeRequestDto) {
        if (details == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            recipeService.updateRecipe(id, addRecipeRequestDto, details.getUsername());
            return ResponseEntity.status(204).build();
        } catch (ForbiddenException forbiddenException) {
            return ResponseEntity.status(403).build();
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("api/recipe/search")
    public ResponseEntity<Object> searchRecipes(@AuthenticationPrincipal UserDetails details, @RequestParam(required = false, defaultValue = "") String category, @RequestParam(required = false, defaultValue = "") String name) {
        if (details == null) {
            return ResponseEntity.status(401).build();
        }
        if ((category.isEmpty() && name.isEmpty()) || (!category.isEmpty() && !name.isEmpty())) {
            return ResponseEntity.status(400).build();
        }

        List<Recipe> recipeList = recipeService.searchRecipes(category, name);
        return ResponseEntity.ok(recipeList);
    }
}

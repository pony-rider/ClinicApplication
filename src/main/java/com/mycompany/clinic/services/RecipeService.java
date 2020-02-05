/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.clinic.services;

import com.mycompany.clinic.entity.Recipe;
import com.mycompany.clinic.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class RecipeService implements EntityService<Recipe>{
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    
    @Override
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }
    
    @Override
    public Recipe create(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
    
    @Override
    public Recipe update(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public void delete(Recipe e) {
        recipeRepository.delete(e);
    }

    @Override
    public Recipe get(Long id) {
        return recipeRepository.findById(id).get();
    }
    
}

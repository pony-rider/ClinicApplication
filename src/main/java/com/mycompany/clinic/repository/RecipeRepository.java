/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.clinic.repository;

import com.mycompany.clinic.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long>{

}

package com.example.Gustomate.fridge.repository;

import com.example.Gustomate.fridge.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}

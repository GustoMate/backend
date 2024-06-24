package com.example.Gustomate.fridge.controller;

import com.example.Gustomate.fridge.model.Ingredient;
import com.example.Gustomate.fridge.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fridge/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<Ingredient> addIngredient(@ModelAttribute Ingredient ingredient, @RequestParam String memberEmail) {
        Ingredient savedIngredient = ingredientService.addIngredient(memberEmail, ingredient);
        return ResponseEntity.ok(savedIngredient);
    }

    @PostMapping("/update")
    public ResponseEntity<Ingredient> updateIngredient(@RequestParam Long id, @ModelAttribute Ingredient ingredientDetails, @RequestParam String memberEmail) {
        Ingredient updatedIngredient = ingredientService.updateIngredient(id, memberEmail, ingredientDetails);
        return updatedIngredient != null ?
                ResponseEntity.ok(updatedIngredient) :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteIngredient(@RequestParam Long id, @RequestParam String memberEmail) {
        ingredientService.deleteIngredient(id, memberEmail);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients(@RequestParam String memberEmail) {
        return ResponseEntity.ok(ingredientService.getAllIngredients(memberEmail));
    }

    @GetMapping("/getById")
    public ResponseEntity<Ingredient> getIngredientById(@RequestParam Long id, @RequestParam String memberEmail) {
        return ingredientService.getIngredientById(id, memberEmail)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

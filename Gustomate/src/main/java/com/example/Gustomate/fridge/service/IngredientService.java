package com.example.Gustomate.fridge.service;

import com.example.Gustomate.fridge.model.Ingredient;
import com.example.Gustomate.fridge.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    //재료 추가
    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    //재료 삭제
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

    //전체 재료 얻기
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
    
    //특정 재료 얻기
    public Optional<Ingredient> getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }

    //재료 수정하기
    public Ingredient updateIngredient(Long id, Ingredient ingredientDetails) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);
        if (ingredientOptional.isPresent()) {
            Ingredient existingIngredient = ingredientOptional.get();
            existingIngredient.setName(ingredientDetails.getName());
            existingIngredient.setQuantity(ingredientDetails.getQuantity());
            existingIngredient.setExpiryDate(ingredientDetails.getExpiryDate());
            return ingredientRepository.save(existingIngredient);
        }
        return null;
    }

}

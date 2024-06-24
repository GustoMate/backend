package com.example.Gustomate.fridge.service;

import com.example.Gustomate.fridge.model.Ingredient;
import com.example.Gustomate.fridge.repository.IngredientRepository;
import com.example.Gustomate.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final MemberRepository memberRepository;

    public IngredientService(IngredientRepository ingredientRepository, MemberRepository memberRepository) {
        this.ingredientRepository = ingredientRepository;
        this.memberRepository = memberRepository;
    }

    public Ingredient addIngredient(String memberEmail, Ingredient ingredient) {
        Long userId = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
        ingredient.setUserId(userId);
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(Long id, String memberEmail, Ingredient ingredientDetails) {
        Long userId = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
        return ingredientRepository.findByIdAndUserId(id, userId)
                .map(ingredient -> {
                    ingredient.setName(ingredientDetails.getName());
                    ingredient.setQuantity(ingredientDetails.getQuantity());
                    ingredient.setUserId(userId);
                    ingredient.setExpiryDate(ingredientDetails.getExpiryDate());
                    ingredient.setPurchaseDate(ingredientDetails.getPurchaseDate());
                    return ingredientRepository.save(ingredient);
                })
                .orElse(null);
    }

    public void deleteIngredient(Long id, String memberEmail) {
        Long userId = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
        ingredientRepository.deleteByIdAndUserId(id, userId);
    }

    public List<Ingredient> getAllIngredients(String memberEmail) {
        Long userId = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
        return ingredientRepository.findAllByUserId(userId);
    }

    public Optional<Ingredient> getIngredientById(Long id, String memberEmail) {
        Long userId = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
        return ingredientRepository.findByIdAndUserId(id, userId);
    }
}

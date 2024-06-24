package com.example.Gustomate;

import com.example.Gustomate.fridge.controller.IngredientController;
import com.example.Gustomate.fridge.model.Ingredient;
import com.example.Gustomate.fridge.service.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient(1L, "Milk", 2, 1L, LocalDate.of(2023, 12, 25), LocalDate.of(2023, 6, 1));
    }

    @Test
    void shouldAddIngredient() throws Exception {
        String memberEmail = "jihyeon";

        // 서비스가 해당 재료를 정상적으로 추가하고 반환하도록 설정
        given(ingredientService.addIngredient(eq(memberEmail), any(Ingredient.class))).willReturn(ingredient);

        // MockMvc를 사용하여 POST 요청을 보내고 JSON 응답 검증
        mockMvc.perform(post("/fridge/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("memberEmail", memberEmail)
                        .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Milk")));
    }

    @Test
    void shouldGetAllIngredients() throws Exception {
        String memberEmail = "jihyeon@example.com";

        given(ingredientService.getAllIngredients(eq(memberEmail))).willReturn(Collections.singletonList(ingredient));

        mockMvc.perform(get("/fridge/ingredients")
                        .param("memberEmail", memberEmail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Milk")));
    }
}

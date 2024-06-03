package com.example.Gustomate.fridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/fridge/view/ingredients")
    public String ingredientsPage() {
        return "ingredient";  // static 폴더 내의 ingredients.html 파일을 가리키는 것
    }
}

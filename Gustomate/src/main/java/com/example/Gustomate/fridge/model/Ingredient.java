package com.example.Gustomate.fridge.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

// 재료 모델 생성
@Entity
@Getter @Setter // 모든 필드에 대한 Getter와 Setter를 자동으로 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 매개변수를 갖는 생성자 자동 생성
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    private LocalDate expiryDate;
    private LocalDate purchaseDate;
}

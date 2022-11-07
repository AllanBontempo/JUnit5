package com.healthycoderapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DietPlannerTest {

    DietPlanner dietPlanner;

    @BeforeEach
    void setUp() {
        dietPlanner = new DietPlanner(20, 30, 50);
    }

    @Test
    void should_ReturnCorrectDietPlan_When_CorrectCoder() {
        Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
        DietPlan expected = new DietPlan(2202, 110, 73, 275);

        DietPlan actual = dietPlanner.calculateDiet(coder);

        assertAll(
                () -> assertEquals(expected.getCalories(), actual.getCalories()),
                () -> assertEquals(expected.getProtein(), actual.getProtein()),
                () -> assertEquals(expected.getFat(), actual.getFat()),
                () -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
        );

    }

}
package com.healthycoderapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DietPlannerTest {

    private DietPlanner dietPlanner;

    // run before every test case
    @BeforeEach
    void setup(){
        this.dietPlanner = new DietPlanner(20,30,50);
    }

    @AfterEach
    void afterEach(){
        System.out.println(" A unit test was finished.");
    }

//    @Test
//    void should_ReturnCorrectDietPlan_When_CorrectCoder(){
//        //given
//        Coder coder = new Coder(1.82,75.0,26,Gender.MALE);
//        DietPlan expected = new DietPlan(2202,110,73,275);
//
//        //when
//        DietPlan actual = dietPlanner.calculateDiet(coder);
//        //then
//        //assertEquals(expected, actual); --> this will not work as it compares the reference
//        assertAll(
//                ()-> assertEquals(expected.getCalories(),actual.getCalories()),
//                ()-> assertEquals(expected.getProtein(), actual.getProtein()),
//                ()-> assertEquals(expected.getFat(), actual.getFat()),
//                ()-> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
//        );
//
//    }

    @RepeatedTest(value=10, name=RepeatedTest.LONG_DISPLAY_NAME)
        /* execute 10 times
        *  each repetition is treated as seperate unit test - That means @AfterEach and @BeforeEach will be executed 10 times*/
    void should_ReturnCorrectDietPlan_When_CorrectCoder(){
        //given
        Coder coder = new Coder(1.82,75.0,26,Gender.MALE);
        DietPlan expected = new DietPlan(2202,110,73,275);

        //when
        DietPlan actual = dietPlanner.calculateDiet(coder);
        //then
        //assertEquals(expected, actual); --> this will not work as it compares the reference
        assertAll(
                ()-> assertEquals(expected.getCalories(),actual.getCalories()),
                ()-> assertEquals(expected.getProtein(), actual.getProtein()),
                ()-> assertEquals(expected.getFat(), actual.getFat()),
                ()-> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
        );

    }
}
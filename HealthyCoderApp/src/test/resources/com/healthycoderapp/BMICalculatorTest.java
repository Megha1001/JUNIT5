package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BMICalculatorTest {

    private String environment = "dev";
    @BeforeAll
    static void beforeAll(){
        System.out.println("Before all unit tests.");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After all unit tests.");
    }
    @Nested
    class IsDietRecommendedTests{
        //to get the coder friendly way use name in @ParameterizedTest
        @ParameterizedTest(name = "weight={0} height={1}") // 0 --> coderWeight, 1--> coderHeight
        //for multiple value not for csv file, and variable name should be value
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1) //look into /resources by default
    /*
    input file contains headers --> weight, height we should not use that since it will endup in error
    so use numLinesToSkip=1
     */
        void should_ReturnTrue_When_RecommendedDiet(Double coderWeight,Double coderHeight){
            //given
            double weight = coderWeight;
            double height = coderHeight;
            //when
            boolean recommended = BMICalculator.isDietRecommended(weight,height);
            //then
            assertTrue(recommended);
        }

        @Test
        void should_ReturnFalse_When_RecommendedNotDiet(){
            //given
            double weight = 50.0;
            double height = 1.92;
            //when
            boolean recommended = BMICalculator.isDietRecommended(weight,height);
            //then
            assertFalse(recommended);
        }

        //Test Exceptions
        @Test
        void should_ThrowArithmeticException_When_HeightZero(){
            //given
            double weight = 50.0;
            double height = 0;
            //when
            Executable executable = ()->BMICalculator.isDietRecommended(weight,height);
            //then
            assertThrows(ArithmeticException.class,executable);
        }
    }

//    @Test
//    void should_ReturnTrue_When_RecommendedDiet(){
//        //given
//        double weight = 89.0;
//        double height = 1.72;
//        //when
//        boolean recommended = BMICalculator.isDietRecommended(weight,height);
//        //then
//        assertTrue(recommended);
//    }

//    @ParameterizedTest
//    @ValueSource(doubles = {70.0,89.0,95.0,110.0})
//    void should_ReturnTrue_When_RecommendedDiet(Double coderWeight){
//        //given
//        double weight = coderWeight;
//        double height = 1.72;
//        //when
//        boolean recommended = BMICalculator.isDietRecommended(weight,height);
//        //then
//        assertTrue(recommended);
//    }

//    //to get the coder friendly way use name in @ParameterizedTest
//    @ParameterizedTest(name = "weight={0} height={1}") // 0 --> coderWeight, 1--> coderHeight
//    //for multiple value not for csv file, and variable name should be value
//    @CsvSource(value={"89.0,1.72","95.0,1.75","110.0,1.78"})
//    void should_ReturnTrue_When_RecommendedDiet(Double coderWeight,Double coderHeight){
//        //given
//        double weight = coderWeight;
//        double height = coderHeight;
//        //when
//        boolean recommended = BMICalculator.isDietRecommended(weight,height);
//        //then
//        assertTrue(recommended);
//    }

    @Nested
    @DisplayName("{{}} sample inner class display name")
    class FindBMIWithWorstCoderTest{
        //Test Multiple assertions
        @Test
        @DisplayName(">>>> sample method display name")
//        @Disabled
        @DisabledOnOs(OS.MAC)
        void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty(){
            //given
            List<Coder> coderList = new ArrayList<>();
            coderList.add(new Coder(1.80, 60.0));
            coderList.add(new Coder(1.85, 98.5));
            coderList.add(new Coder(1.82, 64.7));

            //when
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coderList);
            //then
            assertAll(
                    ()->assertEquals(1.85, coderWorstBMI.getHeight()),
                    ()->assertEquals(98.5, coderWorstBMI.getWeight())
            );
        }

        @Test
        void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements(){
            //given
            assumeTrue(BMICalculatorTest.this.environment.equals("dev"));
            List<Coder> coderList = new ArrayList<>();
            for(int i=0; i<10000; i++){
                coderList.add(new Coder(1.0+i, 10.0+i));
            }

            //when
            Executable executable = ()-> BMICalculator.findCoderWithWorstBMI(coderList);
            //then
        /*
         It is typically used to ensure that a particular operation
         completes within a specified time limit.
         */
            assertTimeout(Duration.ofMillis(10),executable); // check how long executable took
        }

        //Test Null values
        @Test
        void should_ReturnNUllWorstBMI_When_CoderListEmpty(){
            //given
            List<Coder> coderList = new ArrayList<>();
            //when
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coderList);
            //then
            assertNull(coderWorstBMI);
        }
    }


    @Nested
    class GetBMIScoresTest{
        //Test array equality
        @Test
        void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty(){
            //given
            List<Coder> coderList = new ArrayList<>();
            coderList.add(new Coder(1.80, 60.0));
            coderList.add(new Coder(1.82, 98.0));
            coderList.add(new Coder(1.82, 64.7));
            double[] expected = {18.52,29.59,19.53};

            //when
            double[] bmiScores = BMICalculator.getBMIScores(coderList);

            //then
//        assertEquals(expected,bmiScores);// compare references

            assertArrayEquals(expected, bmiScores);
        }
    }

}
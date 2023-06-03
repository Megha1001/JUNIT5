# JUNIT5

- HOW TO WRITE TEST CASES
    1. First mark the test case packages as Test Sources Root
    
    Marking test classes as test sources root in IntelliJ IDEA helps with project organization, enables test-specific features, ensures proper classpath configuration, and integrates smoothly with testing frameworks.
    2. Go to class and press Cmd+Shift+T

### Conventions

- Method name
    - Should when
        - Ex : shoule_ReturnTrue_When_DietRecommended
- Break your method in three parts
    - given
    - when
    - then

@Test
    void should_ReturnTrue_When_DietRecommended(){
        //given
        double weight = 89.0;
        double height = 1.72;
        //when
        boolean recommended = BMICalculator.isDietRecommended(weight,height);
        //then
        assertTrue(recommended);
    }
    
  dont affraid to write many test cases
    @Test
    void should_ReturnTrue_When_RecommendedDiet(){
        //given
        double weight = 89.0;
        double height = 1.72;
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
    
   look at the method if you have any exception like below write test cases for that as well
    
   public static boolean isDietRecommended(double weight, double height) {
		if (height == 0.0) throw new ArithmeticException();
		double bmi = weight / (height * height);
		if (bmi < BMI_THRESHOLD)
			return false;
		return true;
	}
   
  Point to remember

- when we run this —> this is not a normal execution since it will thrown an exception. so to make it Executable that needs a lamda

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
 -choose the one from jupiter.api

we have executable meaning, it will not be executed immediately. We pass this to assertThrows so that it can execute the lamdba for itself and check if it throws exception

-Multiple checks
@Test
    void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty(){
        //given
        List<Coder> coderList = new ArrayList<>();
        coderList.add(new Coder(1.80, 60.0));
        coderList.add(new Coder(1.82, 98.0));
        coderList.add(new Coder(1.82, 64.7));

        //when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coderList);
        //then
        assertEquals(1.82, coderWorstBMI.getHeight());
        assertEquals(98.0, coderWorstBMI.getWeight());
    }
    
If one assertion fails second one will never going to execute
    @Test
    void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty(){
        //given
        List<Coder> coderList = new ArrayList<>();
        coderList.add(new Coder(1.80, 60.0));
        coderList.add(new Coder(1.82, 98.0));
        coderList.add(new Coder(1.82, 64.7));

        //when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coderList);
        //then
        assertEquals(1.85, coderWorstBMI.getHeight());
        assertEquals(98.5, coderWorstBMI.getWeight());
    }
    
For above code first assertion Fails but we dont know about second assertion

SO THERE IS assertAll method
    @Test
    void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty(){
        //given
        List<Coder> coderList = new ArrayList<>();
        coderList.add(new Coder(1.80, 60.0));
        coderList.add(new Coder(1.82, 98.0));
        coderList.add(new Coder(1.82, 64.7));

        //when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coderList);
        //then
        assertAll(
                ()->assertEquals(1.85, coderWorstBMI.getHeight()),
                ()->assertEquals(98.5, coderWorstBMI.getWeight())
        );
    }
    
    
FOR ARRAYs
    
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
    
BeforeEach and AfterEach annotations

@BeforeEach : Annotation will be used before each test case

@AfterEach : Annotation will be used after each test case
    
    package com.healthycoderapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @Test
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
    
    
 @BeforeAll  : can have any name but must be static. It is used for operations that should be run exactly once before all unit tests are performed. such as setting up database connection or starting servers.

@AfterAll : can have any name but must be static. It is used for operation that could be run exactly once after all unit tests are performed. such as closing database connection or stopping servers
    
package com.healthycoderapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @BeforeAll
    static void beforeAll(){
        System.out.println("Before all unit tests.");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After all unit tests.");
    }

    @Test
    void should_ReturnTrue_When_RecommendedDiet(){
        //given
        double weight = 89.0;
        double height = 1.72;
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

    //Test Multiple assertions
    @Test
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
    
    
    
`@ParameterizedTest` : For testing same cases with different values

1. with @ValueSource
    
    @ParameterizedTest
    @ValueSource(doubles = {70.0,89.0,95.0,110.0})
    void should_ReturnTrue_When_RecommendedDiet(Double coderWeight){
        //given
        double weight = coderWeight;
        double height = 1.72;
        //when
        boolean recommended = BMICalculator.isDietRecommended(weight,height);
        //then
        assertTrue(recommended);
    }
    
2. with @CsvSource
    @ParameterizedTest
    //for multiple value not for csv file, and variable name should be value
    @CsvSource(value={"89.0,1.72","95.0,1.75","110.0,1.78"})
    void should_ReturnTrue_When_RecommendedDiet(Double coderWeight,Double coderHeight){
        //given
        double weight = coderWeight;
        double height = coderHeight;
        //when
        boolean recommended = BMICalculator.isDietRecommended(weight,height);
        //then
        assertTrue(recommended);
    }
    
    //to get the coder friendly way use name in @ParameterizedTest
    @ParameterizedTest(name = "weight={0} height={1}") // 0 --> coderWeight, 1--> coderHeight
    //for multiple value not for csv file, and variable name should be value
    @CsvSource(value={"89.0,1.72","95.0,1.75","110.0,1.78"})
    void should_ReturnTrue_When_RecommendedDiet(Double coderWeight,Double coderHeight){
        //given
        double weight = coderWeight;
        double height = coderHeight;
        //when
        boolean recommended = BMICalculator.isDietRecommended(weight,height);
        //then
        assertTrue(recommended);
    }
    
    3. From csv file
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
    
    - WHEN YOU WANT TO RUN SAME TEST CASE MULTIPLE TIMES  - this could happen if your test generated random values and you wanted to make sure all of them are legit.
    
    @RepeatedTest(10)
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
    A word of warning : Only use repeat test when they make sense
    
 - TEST PERFORMANCE : Sometimes , you may need to run a certain test within a given time limit.
    @Test
    void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements(){
        //given
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
    
    - We may need to write some unit tests that only make sense under certain conditions

A very typical use case is when you have more than one execution environment.For instance, when you run from your local machine while you code i.e. dev env. However, at some point you may want to more your project into production so that env will be prod.

some test cases doesn’t make sense in all the env

For instance , if you only want to run some performance units tests when you are in the prod environment because this is where you have powerful machines available where you run unit tests on your local machine in the DEV env you would like to skip such tests because you know your computer is too weak to pass some TC
 
    private String environment = "dev";
@Test
    void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements(){
        //given
        assumeTrue(this.environment.equals("prod"));
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
    
    
    assumeTrue and assertTrue are same but assumeTrue never makes a test fail

Hence , assumeTrue is more good since it doesnt make test fail or it simply skipped the test.

- Organize your code with @Nested
    - It is being used in the inner classes.
    
    Before change code
    
    package com.healthycoderapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

    //Test Multiple assertions
    @Test
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
        assumeTrue(this.environment.equals("prod"));
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
    
    
    After Change
    
    package com.healthycoderapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
    class FindBMIWithWorstCoderTest{
        //Test Multiple assertions
        @Test
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
            assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
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
    
You can have @BeforeAll etc in inner classes


- OTHER ANNOTATIONS
    1. @DisplayName —> own name for the test
    
    @Test
        @DisplayName(">>>> sample method display name")
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
   
    
    We can add it to inner class as well
    
    @Nested
    @DisplayName("{{}} sample inner class display name")
    class FindBMIWithWorstCoderTest{
        //Test Multiple assertions
        @Test
        @DisplayName(">>>> sample method display name")
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
            assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
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
    
    
    2. @Disabled - To ignore a test. such test case will never be executed. it will not fail or pass it will simply skipped
    
    3.@DisabledOnOS 
    
    
DIFFERENCE B/W Junit 4 and Junit 5
    
    - We can migrate to Junit 5 and still run your old Junit 4 tests using a component named Junit Vintage.
    
    Running older Junit4 tests on Junit 5 platform —> Possible
    
    RUnning newer Junit 5 tests on the Junit4 platfrom —> not possible
    
    —> Means not backward compatible.
    
    1. 
    
    | Junit4 | Junit5 |
    | --- | --- |
    | @Before | @BeforeEach |
    | @After | @AterEach |
    | @BeforeClass | @BeforeAll |
    | @AfterClass | @AfterAll |
    | @Ignore | @Disabled |
    1. Public methods
    
    @Test
    
    public void should_ThrowException() —> must be public
    
    Junit5
    
    void should_ThrowException() —> no need for public
    
    3.Testing exceptions
    
    Junit4
    
    @Test(expected=Exception.class)
    
    public void should_ThrowEx(){
    
    }
    
    Junit5 
    
    @Test
    
    void should_ThrowEx(){
    
    assertThrows(Exception.class, ()→{});
    
    }
    
    1. Testing performance
    
    Junit4
    
    @Test(timeout=1)
    
    public void should_Timeout()){
    
    }
    
    Junit5
    
    @Test
    
    void should_TimeOut(){
    
    assertTimeOut(Duration.OfMillis(1), ()→{});
    
    }
    
    1. New annotations
        
        Junit 4
        
        nope 😟
        
        Junit 5
        
        @Nested
        
        @RepeatedTest
        
- TEST DRIVEN DEVELOPMENT(TDD)
    - Creating software by starting with unit tests that fail and only then writing actual code.
    
    why Use TDD ?
    
    - Increase code quality
    - Unit test are always reads as soon as you finish your implementation
    - Modern and agile approach
    
    

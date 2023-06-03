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

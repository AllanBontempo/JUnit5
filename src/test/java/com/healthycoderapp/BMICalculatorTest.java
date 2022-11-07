package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    private String environment = "prod";

    @Nested
    class IsDietRecommendedTest {

        @ParameterizedTest(name = "weight = {0}, height = {1}")
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {
            // given
            double weight = coderWeight;
            double height = coderHeight;
            // when
            boolean recommended = BMICalculator.isDietRecommended(weight, height);
            // then
            assertTrue(recommended);
        }

        @Test
        void should_ReturnFalse_When_DietNotRecommended() {
            double weight = 50.3;
            double height = 1.65;

            boolean recommended = BMICalculator.isDietRecommended(weight, height);

            assertFalse(recommended);
        }

        @Test
        void should_ThrowArithmeticException_When_HeightZero() {
            double weight = 50.3;
            double height = 0.0;

            Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

            assertThrows(ArithmeticException.class, executable);
        }

    }

    @Nested
    class FindCoderWithWorstBMITest {
        @Test
        @DisplayName(">>>> sample method display name")
        void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {

            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));


            Coder corderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

            assertAll(
                    () -> assertEquals(1.82, corderWorstBMI.getHeight()),
                    () -> assertEquals(98.0, corderWorstBMI.getWeight())
            );
        }

        @Test
        void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements() {

            Assumptions.assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
            List<Coder> coders = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                coders.add(new Coder(1.0 + i, 10.0 + i));
            }

            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

            assertTimeout(Duration.ofMillis(50), executable);


        }


        @Test
        void should_ReturnNullWorstBMICoder_When_CoderListEmpty() {

            List<Coder> coders = new ArrayList<>();

            Coder corderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

            assertNull(corderWorstBMI);
        }
    }

    @Nested
    class GetBMIScoresTest {
        @Test
        void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {

            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));

            double[] expected = {18.52, 29.59, 19.53};

            double[] bmiScores = BMICalculator.getBMIScores(coders);

            assertArrayEquals(expected, bmiScores);

        }
    }



}
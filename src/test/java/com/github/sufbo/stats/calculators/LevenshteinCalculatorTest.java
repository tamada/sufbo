package com.github.sufbo.stats.calculators;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.stats.entities.Similarity;

public class LevenshteinCalculatorTest {
    private LevenshteinCalculator calculator = new LevenshteinCalculator();

    public Bytecode create(String code){
        return new Bytecode(ByteArray.parse(code));
    }

    @Test
    public void testLevenshtein(){
        Bytecode b1 = create("cafebabe");
        Bytecode b2 = create("fecababe");
        Bytecode b3 = create("00000000");
        Bytecode b4 = create("cafebabe");

        assertThat(calculator.algorithm(), is("levenshtein"));
        assertThat(calculator.calculate(b1, b2).isCloseTo(1 - 2.0 / 4, 1E-8), is(true));

        assertThat(calculator.calculate(b1, b3), is(new Similarity(0)));
        assertThat(calculator.calculate(b1, b4), is(new Similarity(1.0)));
    }
}

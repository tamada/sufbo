package com.github.sufbo.stats.calculators;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.stats.entities.Similarity;

public class LevenshteinCalculator extends AbstractSimilarityCalculator {
    public LevenshteinCalculator(){
        super("levenshtein");
    }

    @Override
    public Similarity calculate(ByteArray array1, ByteArray array2) {
        return calculate(array1.stream().toArray(), array2.stream().toArray());
    }

    private Similarity calculate(int[] array1, int[] array2){
        int[][] table = initializeTable(new int[array1.length + 1][array2.length + 1]);
        for(int i = 1; i < table.length; i++){
            for(int j = 1; j < table[i].length; j++){
                int cost = cost(array1[i - 1], array2[j - 1]);
                table[i][j] = minimumCost(table, i, j, cost);
            }
        }
        return createSimilarity(table, array1.length, array2.length);
    }
    
    private int cost(int value1, int value2){
        if(value1 == value2)
            return 0;
        return 1;
    }

    private int minimumCost(int[][] table, int i, int j, int cost){
        return minimum(table[i - 1][j    ] + 1,
                table[i    ][j - 1] + 1,
                table[i - 1][j - 1] + cost);
    }

    private int minimum(int d1, int d2, int d3){
        return Math.min(d1,  Math.min(d2,  d3));
    }

    private Similarity createSimilarity(int[][] table, int length1, int length2){
        int finalCost = table[length1][length2];
        return new Similarity(1 - 1.0 * finalCost / Math.max(length1, length2));
    }

    private int[][] initializeTable(int[][] table){
        for(int i = 0; i < table.length; i++)    table[i][0] = i;
        for(int i = 0; i < table[0].length; i++) table[0][i] = i;
        return table;
    }
}

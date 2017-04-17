package com.github.sufbo.stats.entities;

public class Similarity {
    public static final Similarity ONE = new Similarity(1.0);

    private double similarity;

    public Similarity(double numerator, double denominator){
        this(numerator / denominator);
        if(Math.abs(denominator) < 1E-8 && Math.abs(numerator) < 1E-8)
            similarity = 1d;
    }

    public Similarity(double value){
        this.similarity = value;
    }

    public double value(){
        return similarity;
    }

    public boolean isCloseTo(Similarity similarity, double delta){
        return isCloseTo(similarity.similarity, delta);
    }

    public boolean isCloseTo(double similarity, double delta){
        return this.similarity - similarity < delta;
    }

    @Override
    public String toString(){
        return Double.toString(similarity);
    }

    @Override
    public int hashCode(){
        return Double.hashCode(similarity);
    }

    @Override
    public boolean equals(Object object){
        return object instanceof Similarity
                && similarity == ((Similarity)object).similarity;
    }
}

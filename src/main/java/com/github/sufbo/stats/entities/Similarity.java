package com.github.sufbo.stats.entities;

public class Similarity {
    private double similarity;

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

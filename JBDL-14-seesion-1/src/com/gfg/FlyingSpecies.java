package com.gfg;

public abstract class FlyingSpecies {
    protected Integer numberOfWings;
    protected Integer weight;

    public FlyingSpecies(Integer numberOfWings, Integer weight) {
        this.numberOfWings = numberOfWings;
        this.weight = weight;
    }

    public Integer getNumberOfWings() {
        return numberOfWings;
    }

    public void setNumberOfWings(Integer numberOfWings) {
        this.numberOfWings = numberOfWings;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void walk(){
    };
}

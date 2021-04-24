package com.gfg;

public class Bird extends FlyingSpecies implements IFly {

    public Bird(Integer numberOfWings, Integer weight) {
        super(numberOfWings, weight);
    }

    @Override
    public void walk() {
        System.out.println("Bird walks slowly");
    }


    @Override
    public void fly() {
        System.out.println("Bird flies 20 km per hour");
    }
}

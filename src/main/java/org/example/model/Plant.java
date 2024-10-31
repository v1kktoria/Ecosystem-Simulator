package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plant extends Organism{
    private double growthRate;

    @Override
    public String toString() {
        return "Растение {" +
                "Имя: '" + getName() + '\'' +
                ", Популяция: " + String.format("%.2f", getPopulation()) +
                ", Потребность в воде: " + String.format("%.2f", growthRate) +
                '}';
    }
    public Plant(String name, double population, double growthRate) {
        super(name, population);
        this.growthRate = growthRate;
    }

}

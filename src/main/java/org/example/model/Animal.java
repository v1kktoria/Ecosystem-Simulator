package org.example.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Animal extends Organism{

    private double foodConsumptionRate;
    private AnimalType animalType;

    @Override
    public String toString() {
        return "Животное {" +
                "Имя: '" + getName() + '\'' +
                ", Популяция: " + String.format("%.2f", getPopulation()) +
                ", Потребность в пище: " + String.format("%.2f", foodConsumptionRate) +
                ", Тип животного: " + animalType.toString() +
                '}';
    }

    public Animal(String name, double population, double foodConsumptionRate, AnimalType animalType) {
        super(name, population);
        this.foodConsumptionRate = foodConsumptionRate;
        this.animalType = animalType;
    }
}

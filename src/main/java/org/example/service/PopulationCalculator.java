package org.example.service;


import org.example.utils.Logger;
import org.example.model.*;

public class PopulationCalculator {

    private final Logger logger = new Logger();
    private final boolean isLoggingEnabled;

    public PopulationCalculator(boolean isLoggingEnabled) {
        this.isLoggingEnabled = isLoggingEnabled;
    }

    private void logInteraction(String message, Ecosystem ecosystem) {
        if (isLoggingEnabled) {
            logger.logInteraction(message, ecosystem);
        }
    }

    public void calculatePopulationChange(Organism organism, Ecosystem ecosystem) {
        if (organism instanceof Plant) {
            organism.setPopulation(organism.getPopulation() + calculatePlantChange((Plant) organism, ecosystem));
        } else if (organism instanceof Animal) {
            organism.setPopulation(organism.getPopulation() + calculateAnimalChange((Animal) organism, ecosystem));
        }
    }

    private double calculatePlantChange(Plant plant, Ecosystem ecosystem) {
        double waterUsed = plant.getPopulation() * plant.getGrowthRate() * 0.01;

        double availableWater = ecosystem.getWaterAvailability();
        ecosystem.setWaterAvailability(Math.max(0, availableWater - waterUsed));
        double change = 0;
        // Проверяем, достаточно ли воды для роста растений
        if (availableWater < waterUsed) {
            // Уменьшение популяции растений из-за нехватки воды
            change -= plant.getPopulation() * 0.05;
        } else change += plant.getPopulation() * 0.05;

        // Влияние влажности
        if (ecosystem.getHumidity() >= 60) {
            change += plant.getPopulation() * 0.05;
        } else if (ecosystem.getHumidity() < 40) {
            change -= plant.getPopulation() * 0.05;
        }

        // Влияние температуры
        if (ecosystem.getTemperature() > 15 && ecosystem.getTemperature() < 30) {
            change += plant.getPopulation() * 0.05;
        } else {
            change -= plant.getPopulation() * 0.05;
        }

        return change;
    }

    private double calculateAnimalChange(Animal animal, Ecosystem ecosystem) {
        double change = 0.0;
        double requiredFood = animal.getFoodConsumptionRate() * animal.getPopulation() * 0.05;
        double requiredWater = requiredFood * 0.3;

        // Проверяем, достаточно ли воды для животных
        if (ecosystem.getWaterAvailability() >= requiredWater) {
            ecosystem.setWaterAvailability(ecosystem.getWaterAvailability() - requiredWater);
        } else {
            ecosystem.setWaterAvailability(0);
            change -= animal.getPopulation() * 0.1;
        }

        switch (animal.getAnimalType()) {
            case HERBIVORE -> {
                double consumedPlants = Math.min(requiredFood, getTotalPlantPopulation(ecosystem));
                // Проверяем, достаточно ли пищи для животных
                if (consumedPlants > 0) {
                    change += consumedPlants * 0.5;
                    decreasePlantPopulation(ecosystem, consumedPlants);
                    logInteraction(String.format("%s съел(а) %.2f растений.", animal.getName(), consumedPlants), ecosystem);
                } else {
                    // Уменьшение популяции из-за нехватки пищи
                    change -= animal.getPopulation() * 0.1;
                }
            }
            case CARNIVORE -> {
                double consumedHerbivores = Math.min(requiredFood, getTotalAnimalPopulation(ecosystem, AnimalType.HERBIVORE));
                if (consumedHerbivores > 0) {
                    change += (consumedHerbivores) * 0.5;
                    decreaseAnimalPopulation(ecosystem, AnimalType.HERBIVORE, consumedHerbivores);
                    logInteraction(String.format("%s съел(а) %.2f особей травоядных",animal.getName(), consumedHerbivores), ecosystem);
                } else {
                    change -= animal.getPopulation() * 0.1;
                }
            }
            case OMNIVORE -> {
                double consumedHerbivores1 = Math.min(requiredFood * 0.33, getTotalAnimalPopulation(ecosystem, AnimalType.HERBIVORE));
                double consumedCarnivores = Math.min(requiredFood * 0.33, getTotalAnimalPopulation(ecosystem, AnimalType.CARNIVORE));
                double consumedPlants1 = Math.min(requiredFood * 0.33, getTotalPlantPopulation(ecosystem));
                if (consumedHerbivores1 > 0 || consumedCarnivores > 0 || consumedPlants1 > 0) {
                    change += (consumedHerbivores1 + consumedCarnivores + consumedPlants1) * 0.5;
                    decreaseAnimalPopulation(ecosystem, AnimalType.HERBIVORE, consumedHerbivores1);
                    decreaseAnimalPopulation(ecosystem, AnimalType.CARNIVORE, consumedCarnivores);
                    decreasePlantPopulation(ecosystem, consumedPlants1);
                    logInteraction(String.format("%s съел(а) %.2f особей травоядных, %.2f особей плотоядных, %.2f растений", animal.getName(), consumedHerbivores1, consumedCarnivores, consumedPlants1), ecosystem);
                } else {
                    change -= animal.getPopulation() * 0.1;
                }
            }
        }

        // Учитываем влияние влажности и температуры на популяцию животных
        change += adjustForTemperatureAndHumidity(animal, ecosystem);
        return change;
    }

    private double adjustForTemperatureAndHumidity(Animal animal, Ecosystem ecosystem) {
        double change = 0.0;
        if (ecosystem.getTemperature() < 10 || ecosystem.getTemperature() > 35) {
            change -= animal.getPopulation() * 0.05;
        } else {
            change += animal.getPopulation() * 0.02;
        }

        if (ecosystem.getHumidity() < 30) {
            change -= animal.getPopulation() * 0.05;
        }
        return change;
    }

    private double getTotalAnimalPopulation(Ecosystem ecosystem, AnimalType targetType) {
        return ecosystem.getOrganisms().stream()
                .filter(o -> o instanceof Animal && ((Animal) o).getAnimalType() == targetType)
                .mapToDouble(Organism::getPopulation)
                .sum();
    }

    private double getTotalPlantPopulation(Ecosystem ecosystem) {
        return ecosystem.getOrganisms().stream()
                .filter(o -> o instanceof Plant)
                .mapToDouble(Organism::getPopulation)
                .sum();
    }

    private void decreaseAnimalPopulation(Ecosystem ecosystem, AnimalType type, double consumed) {
        ecosystem.getOrganisms().stream()
                .filter(o -> o instanceof Animal && ((Animal) o).getAnimalType() == type)
                .forEach(o -> o.setPopulation(Math.max(0, o.getPopulation() - consumed)));
    }

    private void decreasePlantPopulation(Ecosystem ecosystem, double consumed) {
        ecosystem.getOrganisms().stream()
                .filter(o -> o instanceof Plant)
                .forEach(o -> o.setPopulation(Math.max(0, o.getPopulation() - consumed)));
    }
}
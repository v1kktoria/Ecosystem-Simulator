package org.example.service;

import org.example.model.*;

import java.util.HashMap;
import java.util.Map;

public class PredictionService {
    private final PopulationCalculator populationCalculator;

    public PredictionService(PopulationCalculator populationCalculator) {
        this.populationCalculator = populationCalculator;
    }

    public String predictPopulationChanges(Ecosystem ecosystem) {
        StringBuilder report = new StringBuilder("Прогноз изменения популяций:\n");
        Map<String, Double> initialPopulations = new HashMap<>();

        for (Organism organism : ecosystem.getOrganisms()) {
            initialPopulations.put(organism.getName(), organism.getPopulation());
        }

        for (Organism organism : ecosystem.getOrganisms()) {
            populationCalculator.calculatePopulationChange(organism, ecosystem);
        }

        for (Organism organism : ecosystem.getOrganisms()) {
            double initialPopulation = initialPopulations.get(organism.getName());
            double finalPopulation = organism.getPopulation();
            double change = finalPopulation - initialPopulation;

            if (change > 0) {
                report.append(String.format("%s: Увеличится на %.2f особей\n", organism.getName(), change));
            } else if (change < 0) {
                report.append(String.format("%s: Уменьшится на %.2f особей\n", organism.getName(), Math.abs(change)));
            } else {
                report.append(String.format("%s: Останется стабильным\n", organism.getName()));
            }
        }

        return report.toString();
    }
}


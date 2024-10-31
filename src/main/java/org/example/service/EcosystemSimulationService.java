package org.example.service;

import org.example.utils.Logger;
import org.example.model.Ecosystem;
import org.example.model.Organism;

import java.util.HashMap;
import java.util.Map;

public class EcosystemSimulationService {

    private final Logger logger = new Logger();
    private final PopulationCalculator populationCalculator;
    private final EcosystemService ecosystemService;

    public EcosystemSimulationService(PopulationCalculator populationCalculator, EcosystemService ecosystemService) {
        this.populationCalculator = populationCalculator;
        this.ecosystemService = ecosystemService;
    }

    public void simulate(Ecosystem ecosystem) {
        double initialTemperature = ecosystem.getTemperature();
        double initialHumidity = ecosystem.getHumidity();
        double initialWaterAvailability = ecosystem.getWaterAvailability();
        Map<String, Double> initialPopulations = new HashMap<>();

        logger.logStart(ecosystem);
        for (Organism organism : ecosystem.getOrganisms()) {
            initialPopulations.put(organism.getName(), organism.getPopulation());
        }

        updateClimateConditions(ecosystem);
        updateWaterAvailability(ecosystem);

        for (Organism organism : ecosystem.getOrganisms()) {
            populationCalculator.calculatePopulationChange(organism, ecosystem);
        }

        applyPopulationChanges(ecosystem);
        logger.logSimulationStep(initialPopulations, ecosystem, initialTemperature, initialHumidity, initialWaterAvailability);
    }

    private void applyPopulationChanges(Ecosystem ecosystem) {
        ecosystemService.updateEcosystem(ecosystem);
    }

    private void updateWaterAvailability(Ecosystem ecosystem) {
        double newWaterAmount = ecosystem.getWaterAvailability();
        // Влияние влажности и температуры
        if (ecosystem.getHumidity() > 50) {
            // Увеличение воды из за высокой влажности
            newWaterAmount += ecosystem.getHumidity() * 0.3;
        }
        if (ecosystem.getTemperature() > 30) {
            // Уменьшение воды из за высокой температуры
            newWaterAmount -= ecosystem.getTemperature() * 0.3;
        }
        if (newWaterAmount > 100) newWaterAmount = 100;
        else if (newWaterAmount < 0) newWaterAmount = 0;
        ecosystem.setWaterAvailability(newWaterAmount);
    }

    private void updateClimateConditions(Ecosystem ecosystem) {
        ecosystem.setTemperature(ecosystem.getTemperature() + Math.random() * 6 - 3);
        ecosystem.setHumidity(Math.max(0, Math.min(100, ecosystem.getHumidity() + Math.random() * 10 - 5)));
    }

}
package org.example.utils;

import org.example.model.Ecosystem;
import org.example.model.Organism;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Logger {
    private static final String directory = "ecosystems";

    public void logStart(Ecosystem ecosystem)
    {
        String filename = directory + "/" + ecosystem.getName() + "_log.txt";
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("=== Новый шаг симуляции " + getCurrentTime() + " ===\n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог-файл: " + e.getMessage());
        }
    }
    public void logInteraction(String message, Ecosystem ecosystem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "/" + ecosystem.getName() + "_log.txt", true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог-файл: " + e.getMessage());
        }
    }

    public void logSimulationStep(Map<String, Double> initialPopulation, Ecosystem ecosystem,
                                  double initialTemperature, double initialHumidity, double initialWaterAvailability)
    {
        String filename = directory + "/" + ecosystem.getName() + "_log.txt";
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("Изменения популяций:\n");
            for(Organism organism : ecosystem.getOrganisms())
            {
                double initialPopulations = initialPopulation.get(organism.getName());
                double finalPopulation = organism.getPopulation();
                double change = finalPopulation - initialPopulations;
                if (finalPopulation == 0) {
                    writer.write(String.format("%s: популяция полностью вымерла", organism.getName()));
                    ecosystem.removeOrganismByName(organism.getName());
                }

                else writer.write(String.format("%s: %+.2f единиц\n", organism.getName(), change));
            }
            writer.write(String.format("Климатические условия:\n - Изменение температуры: %+.2f\n", ecosystem.getTemperature() - initialTemperature));
            writer.write(String.format(" - Изменение влажности: %+.2f%%\n", ecosystem.getHumidity() - initialHumidity));
            writer.write(String.format(" - Изменение доступности воды: %+.2f\n", ecosystem.getWaterAvailability() - initialWaterAvailability));

            writer.write("=== Конец шага симуляции ===\n\n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог-файл: " + e.getMessage());
        }
        System.out.println("Изменения, происходящие в экосистеме записаны в лог-файл: " + filename);
    }

    private String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}

package org.example;

import org.example.model.Ecosystem;
import org.example.repository.EcosystemRepository;
import org.example.repository.FileEcosystemRepository;
import org.example.service.EcosystemService;
import org.example.service.EcosystemSimulationService;
import org.example.service.PopulationCalculator;
import org.example.service.PredictionService;
import org.example.utils.InputValidator;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputValidator inputValidator = new InputValidator(scanner);
        EcosystemRepository ecosystemRepository = new FileEcosystemRepository();
        EcosystemService ecosystemService = new EcosystemService(ecosystemRepository);
        PredictionService predictionService = new PredictionService(new PopulationCalculator(false));
        EcosystemSimulationService ecosystemSimulationService = new EcosystemSimulationService(new PopulationCalculator(true), ecosystemService);
        boolean running = true;

        while (running) {
            System.out.println("Выберите действие:");
            System.out.println("1. Создать новую экосистему");
            System.out.println("2. Обновить экосистему");
            System.out.println("3. Получить информацию об экосистеме по имени");
            System.out.println("4. Получить все экосистемы");
            System.out.println("5. Удалить экосистему");
            System.out.println("6. Сделать прогноз динамики популяций");
            System.out.println("7. Автоматическая симуляции экосистемы");
            System.out.println("0. Выход");

            switch (inputValidator.getValidIntegerInput()) {
                case 1 -> {
                    ecosystemService.createAndSaveEcosystem();
                }
                case 2 -> {
                    System.out.print("Введите название экосистемы для обновления: ");
                    String ecosystemNameToUpdate = scanner.nextLine();
                    Ecosystem ecosystemToUpdate = ecosystemService.getEcosystemByName(ecosystemNameToUpdate);
                    if (ecosystemToUpdate != null) ecosystemService.handleUpdateEcosystem(ecosystemToUpdate);
                }
                case 3 -> {
                    System.out.print("Введите название экосистемы: ");
                    String ecosystemName = scanner.nextLine();
                    Ecosystem ecosystem = ecosystemService.getEcosystemByName(ecosystemName);
                    if (ecosystem != null) {
                        System.out.println("Экосистема: " + ecosystem.getName());
                        System.out.println("Организмы: " + ecosystem.getOrganisms());
                        System.out.printf("Температура: %.2f\n", ecosystem.getTemperature());
                        System.out.printf("Влажность: %.2f%%\n", ecosystem.getHumidity());
                        System.out.printf("Доступность воды: %.2f\n", ecosystem.getWaterAvailability());
                    }
                }
                case 4 -> {
                    System.out.println("Список всех экосистем:");
                    List<Ecosystem> ecosystems = ecosystemService.getAllEcosystems();
                    if (ecosystems.isEmpty()) {
                        System.out.println("Экосистемы отсутствуют.");
                    } else {
                        ecosystems.forEach(ec -> System.out.println(ec.getName()));
                    }
                }
                case 5 -> {
                    System.out.print("Введите название экосистемы для удаления: ");
                    String ecosystemNameToDelete = scanner.nextLine();
                    Ecosystem ecosystemToDelete = ecosystemService.getEcosystemByName(ecosystemNameToDelete);
                    if (ecosystemToDelete != null) ecosystemService.deleteEcosystem(ecosystemToDelete);
                }
                case 6 -> {
                    System.out.print("Введите название экосистемы для прогноза популяции: ");
                    String ecosystemNameToPrediction = scanner.nextLine();
                    Ecosystem ecosystemToPrediction = ecosystemService.getEcosystemByName(ecosystemNameToPrediction);
                    if (ecosystemToPrediction != null) System.out.println(predictionService.predictPopulationChanges(ecosystemToPrediction));
                }
                case 7 -> {
                    System.out.print("Введите название экосистемы для автоматической симуляции: ");
                    String ecosystemNameToSimulate = scanner.nextLine();
                    Ecosystem ecosystemToSimulate = ecosystemService.getEcosystemByName(ecosystemNameToSimulate);
                    if (ecosystemToSimulate != null) ecosystemSimulationService.simulate(ecosystemToSimulate);
                }
                case 0 -> {
                    running = false;
                    System.out.println("Выход из программы.");
                }
                default -> System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
        scanner.close();
    }
}

package org.example.service;

import org.example.utils.InputValidator;
import org.example.utils.OrganismFactory;
import org.example.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputService {
    private final Scanner scanner = new Scanner(System.in);
    private final OrganismFactory organismFactory;
    private final InputValidator inputValidator = new InputValidator(scanner);

    public InputService() {
        this.organismFactory = new OrganismFactory();
    }

    public Ecosystem getEcosystemInput() {
        System.out.print("Введите название экосистемы: ");
        String name = scanner.nextLine();

        List<Organism> organisms = new ArrayList<>();
        String addMore;

        do {
            System.out.println("Добавление организма:");
            organisms.add(organismFactory.createOrganism());
            System.out.print("Хотите добавить еще один организм? (да/нет): ");
            addMore = scanner.nextLine();
        } while (addMore.equalsIgnoreCase("да"));

        System.out.print("Введите температуру экосистемы (в градусах Цельсия): ");
        double temperature = inputValidator.getValidDoubleInput(-50, 50);

        System.out.print("Введите влажность экосистемы (в процентах): ");
        double humidity = inputValidator.getValidDoubleInput(0, 100);

        System.out.print("Введите доступность воды (в процентах): ");
        double waterAvailability = inputValidator.getValidDoubleInput(0, 100);

        return new Ecosystem(name, organisms, temperature, humidity, waterAvailability);
    }

    public void updateEcosystemInput(Ecosystem ecosystem) {
        System.out.println("Обновление экосистемы: " + ecosystem.getName());

        System.out.print("Введите новую температуру (текущая: " + ecosystem.getTemperature() + " °C, оставьте пустым для отмены): ");
        String newTemperature = scanner.nextLine();
        if (!newTemperature.trim().isEmpty()) {
            ecosystem.setTemperature(Double.parseDouble(newTemperature));
        }

        System.out.print("Введите новую влажность (текущая: " + ecosystem.getHumidity() + " %, оставьте пустым для отмены): ");
        String newHumidity = scanner.nextLine();
        if (!newHumidity.trim().isEmpty()) {
            ecosystem.setHumidity(Double.parseDouble(newHumidity));
        }

        System.out.print("Введите новую доступность воды (текущая: " + ecosystem.getWaterAvailability() + " %, оставьте пустым для отмены): ");
        String newWaterAvailability = scanner.nextLine();
        if (!newWaterAvailability.trim().isEmpty()) {
            ecosystem.setWaterAvailability(Double.parseDouble(newWaterAvailability));
        }

        updateOrganisms(ecosystem.getOrganisms());

        System.out.print("Хотите добавить организмы? (да/нет): ");
        if (scanner.nextLine().equalsIgnoreCase("да")) {
            System.out.print("Введите количество организмов для добавления: ");
            int numberOfOrganisms = inputValidator.getValidIntegerInput();
            for (int i = 0; i < numberOfOrganisms; i++) {
                ecosystem.getOrganisms().add(organismFactory.createOrganism());
            }
        }

        System.out.print("Хотите удалить организмы? (да/нет): ");
        if (scanner.nextLine().equalsIgnoreCase("да")) {
            System.out.print("Введите количество организмов для удаления: ");
            int numberOfOrganismsToRemove = inputValidator.getValidIntegerInput();
            for (int i = 0; i < numberOfOrganismsToRemove; i++) {
                System.out.print("Введите имя организма для удаления: ");
                String nameToRemove = scanner.nextLine();
                ecosystem.removeOrganismByName(nameToRemove);
            }
        }
        System.out.println("Экосистема успешно обновлена!");
    }

    private void updateOrganisms(List<Organism> organisms)
    {
        System.out.print("Хотите изменить параметры организмов? (да/нет): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("да")) {
            for (Organism organism : organisms) {
                System.out.println("Изменение параметров организма: " + organism.getName());

                System.out.print("Введите новую популяцию (текущая: " + organism.getPopulation() + ", оставьте пустым для отмены): ");
                String newPopulation = scanner.nextLine().trim();
                if (!newPopulation.isEmpty()) {
                    organism.setPopulation(Double.parseDouble(newPopulation));
                }

                if (organism instanceof Animal animal) {
                    System.out.print("Введите новую скорость потребления пищи (текущая: " + animal.getFoodConsumptionRate() + ", оставьте пустым для отмены): ");
                    String newFoodConsumptionRate = scanner.nextLine().trim();
                    if (!newFoodConsumptionRate.isEmpty()) {
                        animal.setFoodConsumptionRate(Double.parseDouble(newFoodConsumptionRate));
                    }

                    System.out.print("Введите новый тип животного (текущий: " + animal.getAnimalType() + ", оставьте пустым для отмены): \n");
                    System.out.println("1. Травоядное\n2. Плотоядное\n3. Всеядное\nВведите номер типа животного:");
                    String newAnimalType = scanner.nextLine().trim();

                    if (!newAnimalType.isEmpty()) {
                        switch (newAnimalType) {
                            case "1" -> animal.setAnimalType(AnimalType.HERBIVORE);
                            case "2" -> animal.setAnimalType(AnimalType.CARNIVORE);
                            case "3" -> animal.setAnimalType(AnimalType.OMNIVORE);
                            default -> System.out.println("Некорректный номер типа животного. Тип не изменен.");
                        }
                    }
                } else if (organism instanceof Plant plant) {
                    System.out.print("Введите новую скорость роста (текущая: " + plant.getGrowthRate() + ", оставьте пустым для отмены): ");
                    String newGrowthRate = scanner.nextLine().trim();
                    if (!newGrowthRate.isEmpty()) {
                        plant.setGrowthRate(Double.parseDouble(newGrowthRate));
                    }
                }
            }
        }
    }
}
